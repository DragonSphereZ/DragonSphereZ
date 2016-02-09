package ud.bi0.dragonSphereZ.maths.shape;

import ud.bi0.dragonSphereZ.maths.base.Base2;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Ellipse extends Shape {
	
	/**
	 * An ellipse is defined by an origin two
	 * base vectors (u,v).
	 * The normal vector is orthogonal to u and v
	 * and has a length of 1.
	 * 
	 */
	protected Vector3 origin;
	protected Base2 base;
	
	/**
	 * Creates an ellipse at a point (origin) with the 
	 * two bases u and v.
	 * 
	 */
	public Ellipse(Vector3 origin, Vector3 u, Vector3 v) {
		this.origin = origin;
		this.base = new Base2(u,v);
	}
	
	/**
	 * Creates an ellipse at a point (origin) with the
	 * two radii x and y on the respective axis.
	 * 
	 */
	public Ellipse(Vector3 origin, double x, double y) {
		this.origin = origin;
		this.base = new Base2(x,y);
	}
	
	@Override
	public ShapeType getShapeType() {
		return ShapeType.ELLIPSE;
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
	
	public Vector3 getPoint(double angle) {
		return origin.clone().add(Math.cos(angle),base.getU()).add(Math.sin(angle),base.getV());
	}
	
	/**
	 * Get a set of points on the ellipse 
	 * with density 1.
	 * 
	 */
	public Vector3[] render() {
		return render(0, 2*Math.PI,1);
	}
	
	/**
	 * Get a set of points on the ellipse
	 * with density (density).
	 * 
	 */
	public Vector3[] render(double density) {
		return render(0, 2*Math.PI, density);
	}

	/**
	 * Get a set of points on the ellipse.
	 * start represents the angle at the start.
	 * end represents the angle at the end.
	 * If start = 0 then the first point will be at origin + u.
	 * If start = pi / 2 then the first point will be at origin + v;
	 * 
	 */
	public Vector3[] render(double start, double end, double density) {
		int pointAmount = (int) (Math.abs(end - start) * Math.sqrt(base.getU().lengthSquared() + base.getV().lengthSquared()) * density);
		double step = (end - start) / pointAmount;
		double angle = start;
		Vector3[] points = new Vector3[pointAmount];
		for (int i = 0; i < pointAmount; i++) {
			points[i] = this.getPoint(angle);
			angle += step;
		}
		return points;
	}
}
