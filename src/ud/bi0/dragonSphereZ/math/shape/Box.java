package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Box 
	extends BaseShape
	implements Cloneable {
	
	public static final Vector3d DEFAULT_RADIUS = Vector3d.ONE;
	
	private Vector3d radius;
	
	public Box() {
		this(DEFAULT_RADIUS);
	}
	
	public Box(Box box) {
		this(box.getOrigin(), box.getBase(), box.getRadius());
	}
	
	public Box(Vector3d radius) {
		setRadius(radius);
	}
	
	public Box(Vector3d origin, Base3d base, Vector3d radius) {
		super(origin, base);
		setRadius(radius);
	}
	
	public Vector3d getRadius() {
		return radius.clone();
	}
	
	public void setRadius(double radius) {
		setRadius(radius, radius, radius);
	}
	
	public void setRadius(double radiusU, double radiusV, double radiusW) {
		setRadius(new Vector3d(radiusU, radiusV, radiusW));
	}
	
	public void setRadius(Vector3d radius) {
		this.radius = radius.clone();
	}
	
	public void transformRadius(Matrix3d matrix) {
		Vector3d radius = getRadius();
		radius = matrix.transform(radius);
		setRadius(radius);
	}
	
	public ArrayList<Vector3d> getVertices() {
		return getVertices(radius.getX(), radius.getY(), radius.getZ());
	}
	
	public ArrayList<Vector3d> getVertices(double radiusU, double radiusV, double radiusW) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(8);
		points.add(getPoint(radiusU, radiusV, radiusW, 0, 0, 0));
		points.add(getPoint(radiusU, radiusV, radiusW, 1, 0, 0));
		points.add(getPoint(radiusU, radiusV, radiusW, 0, 1, 0));
		points.add(getPoint(radiusU, radiusV, radiusW, 0, 0, 1));
		points.add(getPoint(radiusU, radiusV, radiusW, 1, 1, 0));
		points.add(getPoint(radiusU, radiusV, radiusW, 1, 0, 1));
		points.add(getPoint(radiusU, radiusV, radiusW, 0, 1, 1));
		points.add(getPoint(radiusU, radiusV, radiusW, 1, 1, 1));
		return points;
	}
	
	public ArrayList<Line> getEdges(double radiusU, double radiusV, double radiusW) {
		ArrayList<Line> lines = new ArrayList<Line>(12);
		ArrayList<Vector3d> vertices = getVertices(radiusU, radiusV, radiusW);
		lines.add(new Line(vertices.get(0), vertices.get(1)));
		lines.add(new Line(vertices.get(0), vertices.get(2)));
		lines.add(new Line(vertices.get(0), vertices.get(3)));
		lines.add(new Line(vertices.get(1), vertices.get(4)));
		lines.add(new Line(vertices.get(1), vertices.get(5)));
		lines.add(new Line(vertices.get(2), vertices.get(4)));
		lines.add(new Line(vertices.get(2), vertices.get(6)));
		lines.add(new Line(vertices.get(3), vertices.get(5)));
		lines.add(new Line(vertices.get(3), vertices.get(6)));
		lines.add(new Line(vertices.get(5), vertices.get(7)));
		lines.add(new Line(vertices.get(6), vertices.get(7)));
		lines.add(new Line(vertices.get(7), vertices.get(7)));
		return lines;
	}
	
	public Vector3d getPoint(double percentU, double percentV, double percentW) {
		return getPoint(this.getRadius(), new Vector3d(percentU, percentV, percentW));
	}
	
	public Vector3d getPoint(double radiusU, double radiusV, double radiusW, double percentU, double percentV, double percentW) {
		return getPoint(new Vector3d(radiusU, radiusV, radiusW), new Vector3d(percentU, percentV, percentW));
	}
	
	public Vector3d getPoint(Vector3d radius, Vector3d percent) {
		return Coordinate.Cartesian3d.getPoint(getBase(), radius.mul(-1).add(radius.mul(percent).mul(2))).add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPoint(int n, IntToDoubleFunction percentU, IntToDoubleFunction percentV, IntToDoubleFunction percentW) {
		double u = getRadius().getX();
		double v = getRadius().getY();
		double w = getRadius().getZ();
		IntToDoubleFunction radiusU = (i) -> u;
		IntToDoubleFunction radiusV = (i) -> v;
		IntToDoubleFunction radiusW = (i) -> w;
		return getPointN(n, radiusU, radiusV, radiusW, percentU, percentV, percentW);
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction radiusU, IntToDoubleFunction radiusV, IntToDoubleFunction radiusW, IntToDoubleFunction percentU, IntToDoubleFunction percentV, IntToDoubleFunction percentW) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.applyAsDouble(i), radiusV.applyAsDouble(i), radiusU.applyAsDouble(i), percentU.applyAsDouble(i), percentV.applyAsDouble(i), percentW.applyAsDouble(i)));
		}
		return points;
	}
	
	@Override
	public Box clone() {
		return new Box(this);
	}
	
}
