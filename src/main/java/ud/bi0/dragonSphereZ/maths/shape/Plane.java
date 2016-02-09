package ud.bi0.dragonSphereZ.maths.shape;

import java.util.ArrayList;
import java.util.List;

import ud.bi0.dragonSphereZ.maths.base.Base2;
import ud.bi0.dragonSphereZ.maths.vector.Vector2;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Plane extends Shape {
	
	/**
	 * A plane is defined by an origin and two directions
	 * (u,v). The normal vector is orthogonal to u and v
	 * and has a length of 1.
	 * 
	 */
	protected Vector3 origin;
	protected Base2 base;
	
	/**
	 * Creates a new plane that goes through 
	 * the point at origin and has the x- and
	 * y-axis as a base.
	 * 
	 */
	public Plane(Vector3 origin) {
		this.origin = origin;
		this.base = new Base2();
	}
	
	public Plane(Vector3 u, Vector3 v) {
		this.origin = new Vector3(0,0,0);
		this.base = new Base2(u,v);
	}
	/**
	 * Creates a new plane that goes through the point at 
	 * origin and has u and v as a basis.
	 * 
	 */
	public Plane(Vector3 origin, Vector3 u, Vector3 v) {
		this.origin = origin;
		this.base = new Base2(u,v);
	}
	
	@Override
	public ShapeType getShapeType() {
		return ShapeType.PLANE;
	}
	
	public Vector3 getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
	
	public Base2 getBase() {
		return base;
	}
	
	public void setBase(Base2 base) {
		this.base = base;
	}
	
	/**
	 * Get a point on the plane.
	 * If u = v = 0 it returns the origin.
	 * If u = 1 and v = 0 it returns origin + base vector u.
	 * If u = 0 and v = 1 it returns origin + base vector v.
	 * 
	 */
	public Vector3 getPoint(double u, double v) {
		return origin.clone().add(u,base.getU()).add(v,base.getV());
	}
	
	/**
	 * Get a point on the plane.
	 * The vector holds the factors for u and v:
	 * point = (u,v).
	 * 
	 */
	public Vector3 getPoint(Vector2 point) {
		return getPoint(point.getX(),point.getY());
	}

	/**
	 * Get a line on the plane.
	 * Similar to getPoint().
	 * 
	 */
	public Line getLine(double startU, double endU, double startV, double endV) {
		return new Line(getPoint(startU,startV), getPoint(endU,endV));
	}
	
	/**
	 * Get a line on the plane.
	 * Similar to getPoint().
	 * 
	 */
	public Line getLine(Vector2 start, Vector2 end) {
		return new Line(getPoint(start), getPoint(end));
	}
	
	/**
	 * Get a set of points on the plane
	 * from origin to origin + u + v with
	 * density 1
	 * 
	 */
	public List<Vector3> render() {
		return render(0,1,0,1,1);
	}
	
	/**
	 * Get a set of points on the plane
	 * from origin to origin + u + v with
	 * density (density).
	 * 
	 */
	public List<Vector3> render(double density) {
		return render(0,1,0,1,density);
	}
	
	/**
	 * Get a set of points on the plane.
	 * Returns points between:
	 * 		origin + startU * u + startV * u 
	 * 	and origin + endU * u + endV
	 */
	public List<Vector3> render(double startU, double endU, double startV, double endV, double density) {
		int pointAmountU = (int) (Math.abs(endU - startU) * base.getU().length() * density);
		int pointAmountV = (int) (Math.abs(endV - startV) * base.getV().length() * density);
		double stepU = (endU - startU) / pointAmountU;
		double stepV = (endV - startV) / pointAmountV;
		List<Vector3> points = new ArrayList<Vector3>((pointAmountU+1)*(pointAmountV+1));
		double pointU = startU;
		double pointV = startV;
		for (int i = 0; i < pointAmountU+1; i++) {
			for (int j = 0; j < pointAmountV+1; j++) {
				points.add(getPoint(pointU, pointV));
				pointV += stepV;
			}
			pointV = startV;
			pointU += stepU;
		}
		return points;
	}
	
	/**
	 * Get a set of points on the plane.
	 * The vectors hold the factors for u and v:
	 * start = (startU, startV)
	 * end = (endU, endV)
	 */
	public List<Vector3> render(Vector2 start, Vector2 end, double density) {
		return render(start.getX(), end.getX(), start.getY(), end.getY(), density);
	}
	
}
