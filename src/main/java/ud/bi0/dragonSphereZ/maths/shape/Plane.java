package ud.bi0.dragonSphereZ.maths.shape;

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
	protected Vector3 normal;
	protected Vector3 u;
	protected Vector3 v;
	
	/**
	 * Creates a new plane that goes through 
	 * the point at origin and has the x- and
	 * y-axis as a base.
	 * 
	 */
	public Plane(Vector3 origin) {
		this.origin = origin;
		this.u = new Vector3(1,0,0);
		this.v = new Vector3(0,1,0);
		this.normal = new Vector3(0,0,1);
	}
	
	/**
	 * Creates a new plane that goes through the point at 
	 * origin and has u and v normalized as a basis.
	 * 
	 */
	public Plane(Vector3 origin, Vector3 u, Vector3 v) {
		this.origin = origin;
		this.u = u;
		this.v = v;
		this.normal = u.clone().crossProduct(v).normalize();
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
	
	public Vector3 getNormal() {
		return normal;
	}
	
	/**
	 * Sets the normal vector of the plane and 
	 * changes the base accordingly.
	 * 
	 */
	public void setNormal(Vector3 direction) {
		Vector3 normal = direction.clone().normalize();
		this.normal = normal;
		this.u.adjust(normal);
		this.v.adjust(normal);
	}
	
	public Vector3 getU() {
		return u;
	}
	
	/** 
	 * Sets the base vector u. If adjust
	 * is true it will change the other base
	 * vector accordingly.
	 * 
	 */
	public void setU(Vector3 u, boolean adjust) {
		if (adjust) this.v.adjust(this.u, u);
		this.u = u;
		this.normal = u.clone().crossProduct(v).normalize();
	}
	
	public Vector3 getV() {
		return v;
	}
	
	/**
	 * Sets the base vector v. If adjust 
	 * is true it will change the other base 
	 * vector accordingly.
	 * 
	 */
	public void setV(Vector3 v, boolean adjust) {
		if (adjust) this.u.adjust(this.v, v);
		this.v = v;
		this.normal = u.clone().crossProduct(v).normalize();
	}
	
	/**
	 * Get a point on the plane.
	 * If u = v = 0 it returns the origin.
	 * If u = 1 and v = 0 it returns origin + base vector u.
	 * If u = 0 and v = 1 it returns origin + base vector v.
	 * 
	 */
	public Vector3 getPoint(double u, double v) {
		return origin.clone().add(u,this.u).add(v,this.v);
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
	public Vector3[][] render() {
		return render(0,1,0,1,1);
	}
	
	/**
	 * Get a set of points on the plane
	 * from origin to origin + u + v with
	 * density (density).
	 * 
	 */
	public Vector3[][] render(double density) {
		return render(0,1,0,1,density);
	}
	
	/**
	 * Get a set of points on the plane.
	 * Returns points between:
	 * 		origin + startU * u + startV * u 
	 * 	and origin + endU * u + endV
	 */
	public Vector3[][] render(double startU, double endU, double startV, double endV, double density) {
		int pointAmountU = (int) (Math.abs(endU - startU) * density);
		int pointAmountV = (int) (Math.abs(endV - startV) * density);
		double stepU = (endU - startU) / pointAmountU;
		double stepV = (endV - startV) / pointAmountV;
		Vector3[][] points = new Vector3[pointAmountU][pointAmountV];
		double pointU = startU;
		double pointV = startV;
		for (int i = 0; i < pointAmountU; i++) {
			for (int j = 0; j < pointAmountV; j++) {
				points[i][j] = getPoint(pointU, pointV);
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
	public Vector3[][] render(Vector2 start, Vector2 end, double density) {
		return render(start.getX(), end.getX(), start.getY(), end.getY(), density);
	}
	
}
