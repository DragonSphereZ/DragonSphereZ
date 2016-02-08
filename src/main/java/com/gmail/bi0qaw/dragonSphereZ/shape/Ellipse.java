package com.gmail.bi0qaw.dragonSphereZ.shape;

import com.gmail.bi0qaw.dragonSphereZ.vector.Vector3;

public class Ellipse extends Shape {
	
	/**
	 * An ellipse is defined by an origin two
	 * base vectors (u,v).
	 * The normal vector is orthogonal to u and v
	 * and has a length of 1.
	 * 
	 */
	protected Vector3 origin;
	protected Vector3 normal;
	protected Vector3 u;
	protected Vector3 v;
	
	/**
	 * Creates an ellipse at a point (origin) with the 
	 * two bases u and v.
	 * 
	 */
	public Ellipse(Vector3 origin, Vector3 u, Vector3 v) {
		this.origin = origin;
		this.u = u;
		this.v = v;
		this.normal = u.clone().crossProduct(v).normalize();
	}
	
	/**
	 * Creates an ellipse at a point (origin) with the
	 * two radii x and y on the respective axis.
	 * 
	 */
	public Ellipse(Vector3 origin, double x, double y) {
		this.origin = origin;
		this.u = new Vector3(x,0,0);
		this.v = new Vector3(0,y,0);
		this.normal = u.clone().crossProduct(v).normalize();
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
	
	public Vector3 getNormal() {
		return normal;
	}
	
	/**
	 * Changes the direction of the normal vector and 
	 * adjusts the base accordingly.
	 * 
	 */
	public void setNormal(Vector3 direction) {
		Vector3 normal = direction.clone().normalize();
		this.u.adjust(this.normal, normal);
		this.v.adjust(this.normal, normal);
		this.normal = normal;
	}
	
	public Vector3 getU() {
		return u;
	}
	
	/**
	 * Changes the base vector u. If adjust is 
	 * true it will change the other base vector
	 * accordingly.
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
	 * Changes the base vector v. If adjust is
	 * true it will change the other base vector
	 * accordingly.
	 * 
	 */
	public void setV(Vector3 v, boolean adjust) {
		if (adjust) this.u.adjust(this.v, v);
		this.v = v;
		this.normal = u.clone().crossProduct(v).normalize();
	}
	
	/**
	 * Get a point on the ellipse.
	 * If angle = 0 then it will return origin + u.
	 * If angle = pi / 2 then it will return origin + v.
	 * 
	 */
	public Vector3 getPoint(double angle) {
		return origin.clone().add(Math.cos(angle),u).add(Math.sin(angle),v);
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
		int pointAmount = (int) (Math.abs(end - start) * Math.sqrt(getU().lengthSquared() + getV().lengthSquared()) * density);
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
