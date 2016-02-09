package ud.bi0.dragonSphereZ.maths.shape;

import java.util.ArrayList;
import java.util.List;

import ud.bi0.dragonSphereZ.maths.base.Base3;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Box extends Shape {
	
	protected Vector3 origin;
	protected Base3 base;
	
	public Box(Vector3 origin, Vector3 u, Vector3 v, Vector3 w) {
		this.origin = origin;
		this.base = new Base3(u,v,w);
	}
	
	public Box(Vector3 origin, double x, double y, double z) {
		this.origin = origin;
		this.base = new Base3(x,y,z);
	}
	
	public Vector3 getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
	
	public Base3 getBase() {
		return base;
	}
	
	public void setBase(Base3 base) {
		this.base = base;
	}
	
	public Vector3 getPoint(double u, double v, double w) {
		return origin.getCoordinate(base.getU(), base.getV(), base.getW(), u, v, w);
	}

	public List<Vector3> getVertices(double startU, double endU, double startV, double endV, double startW, double endW) {
		List<Vector3> vertices = new ArrayList<Vector3>(8);
		vertices.add(this.getPoint(startU, startV, startW));
		vertices.add(this.getPoint(endU, startV, startW));
		vertices.add(this.getPoint(startU, endV, startW));
		vertices.add(this.getPoint(startU, startV, endW));
		vertices.add(this.getPoint(endU, endV, startW));
		vertices.add(this.getPoint(endU, startV, endW));
		vertices.add(this.getPoint(startU, endV, endW));
		vertices.add(this.getPoint(endU, endV, endW));
		return vertices;
	}
	
	public List<Line> getEdges(double startU, double endU, double startV, double endV, double startW, double endW) {
		List<Line> edges = new ArrayList<Line>(12);
		List<Vector3> vertices = this.getVertices(startU, endU, startV, endV, startW, endW);
		edges.add(new Line(vertices.get(0),vertices.get(1)));
		edges.add(new Line(vertices.get(0),vertices.get(2)));
		edges.add(new Line(vertices.get(0),vertices.get(3)));
		edges.add(new Line(vertices.get(1),vertices.get(4)));
		edges.add(new Line(vertices.get(1),vertices.get(5)));
		edges.add(new Line(vertices.get(2),vertices.get(4)));
		edges.add(new Line(vertices.get(2),vertices.get(6)));
		edges.add(new Line(vertices.get(3),vertices.get(5)));
		edges.add(new Line(vertices.get(3),vertices.get(6)));
		edges.add(new Line(vertices.get(4),vertices.get(7)));
		edges.add(new Line(vertices.get(5),vertices.get(7)));
		edges.add(new Line(vertices.get(6),vertices.get(7)));
		return edges;
	}
	
	public List<Plane> getFaces(double startU, double endU, double startV, double endV, double startW, double endW) {
		List<Vector3> vertices = this.getVertices(startU, endU, startV, endV, startW, endW);
		List<Plane> faces = new ArrayList<Plane>(6);
		Vector3 point0 = vertices.get(0);
		Vector3 point7 = vertices.get(7);
		faces.add(new Plane(point0.clone(), vertices.get(1).clone().subtract(point0), vertices.get(2).clone().subtract(point0)));
		faces.add(new Plane(point0.clone(), vertices.get(1).clone().subtract(point0), vertices.get(3).clone().subtract(point0)));
		faces.add(new Plane(point0.clone(), vertices.get(2).clone().subtract(point0), vertices.get(3).clone().subtract(point0)));
		faces.add(new Plane(point7.clone(), vertices.get(4).clone().subtract(point7), vertices.get(5).clone().subtract(point7)));
		faces.add(new Plane(point7.clone(), vertices.get(4).clone().subtract(point7), vertices.get(6).clone().subtract(point7)));
		faces.add(new Plane(point7.clone(), vertices.get(5).clone().subtract(point7), vertices.get(6).clone().subtract(point7)));
		return faces;
	}
	
	public List<Vector3> renderOutline(double density) {
		return renderOutline(-0.5,0.5,-0.5,0.5,-0.5,0.5,density);
	}
	
	public List<Vector3> renderOutline(double startU, double endU, double startV, double endV, double startW, double endW, double density) {
		List<Line> edges = this.getEdges(startU, endU, startV, endV, startW, endW);
		List<Vector3> points = new ArrayList<Vector3>();
		for (Line edge : edges) {
			points.addAll(edge.render(density));
		}
		return points;
	}
	
	public List<Vector3> renderFaces(double density) {
		return renderFaces(-0.5,0.5,-0.5,0.5,-0.5,0.5,density);
	}
	
	public List<Vector3> renderFaces(double startU, double endU, double startV, double endV, double startW, double endW, double density) {
		List<Plane> faces = this.getFaces(startU, endU, startV, endV, startW, endW);
		List<Vector3> points = new ArrayList<Vector3>();
		for (Plane face : faces) {
			points.addAll(face.render(density));
		}
		return points;
	}
}
