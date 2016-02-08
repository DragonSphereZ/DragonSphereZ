package com.gmail.bi0qaw.dragonSphereZ.shape;

import java.util.Random;

import com.gmail.bi0qaw.dragonSphereZ.vector.Vector3;

public class Ellipsoid extends Shape {
	
	/**
	 * An ellipsoid is defined by a center point (origin)
	 * and three base vectors (u,v,w).
	 * 
	 */
	protected Vector3 origin;
	protected Vector3 u;
	protected Vector3 v;
	protected Vector3 w;
	
	/**
	 * Creates an ellipsoid at origin with the
	 * vectors u, v and w as base.
	 * 
	 */
	public Ellipsoid(Vector3 origin, Vector3 u, Vector3 v, Vector3 w) {
		this.origin = origin;
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	/**
	 * Creates an ellipsoid at origin with the 
	 * three radii x, y and z on their respective axis.
	 * 
	 */
	public Ellipsoid(Vector3 origin, double x, double y, double z) {
		this.origin = origin;
		this.u = new Vector3(x,0,0);
		this.v = new Vector3(0,y,0);
		this.w = new Vector3(0,0,z);
	}
	
	
	/**
	 * Creates a sphere at origin with a certain radius.
	 */
	public Ellipsoid(Vector3 origin, double radius) {
		this.origin = origin;
		this.u = new Vector3(radius,0,0);
		this.v = new Vector3(0,radius,0);
		this.w = new Vector3(0,0,radius);
	}
	
	/**
	 * Creates a unit sphere at origin.
	 * 
	 */
	public Ellipsoid(Vector3 origin) {
		this.origin = origin;
		this.u = new Vector3(1,0,0);
		this.v = new Vector3(0,1,0);
		this.w = new Vector3(0,0,1);
	}
	
	@Override
	public ShapeType getShapeType() {
		return ShapeType.ELLIPSOID;
	}
	
	public Vector3 getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
	
	public Vector3 getU() {
		return u;
	}
	
	/**
	 * Set the base vector u. If adjust is true
	 * the other base vectors will be changed
	 * accordingly.
	 * 
	 */
	public void setU(Vector3 u, boolean adjust) {
		if (adjust) {
			this.v.adjust(this.u, u);
			this.w.adjust(this.u, u);
		}
		this.u = u;
	}
	
	public Vector3 getV() {
		return v;
	}
	
	/**
	 * Set the base vector v. If adjust is true
	 * the other base vectors will be changed
	 * accordingly.
	 * 
	 */
	public void setV(Vector3 v, boolean adjust) {
		if (adjust) {
			this.u.adjust(this.v, v);
			this.w.adjust(this.v, v);
		}
		this.v = v;
	}
	

	public Vector3 getW() {
		return w;
	}
	
	/**
	 * Set the base vector w. If adjust is true
	 * the other base vectors will be changed
	 * accordingly.
	 * 
	 */	
	public void setW(Vector3 w, boolean adjust) {
		if (adjust) {
			this.u.adjust(this.w, w);
			this.v.adjust(this.w, w);
		}
		this.w = w;
	}
	
	/**
	 * Returns the length of the longest
	 * base vector.
	 * 
	 */
	public double getMaxRadius() {
		return Math.sqrt(getMaxRadiusSquared());
	}
	
	/**
	 * Returns the squared length of the 
	 * longest base vector.
	 * 
	 */
	public double getMaxRadiusSquared() {
		return Math.max(u.lengthSquared(), Math.max(v.lengthSquared(), w.lengthSquared()));
	}
	
	/**
	 * Returns the two points at (u,v) of the 
	 * parameterized ellipsoid. 
	 * If u = v = 0 it returns:
	 * 		origin + u + w
	 * and	origin + u - w
	 * 
	 * If u = pi / 2 & v = 0 it returns:
	 * 		origin + v + w
	 * and 	origin + v - w
	 * 
	 */
	public Vector3[] getPoint(double u, double v) {
		double cosu = Math.cos(u);
		double sinu = Math.sin(u);
		double sinv = Math.sin(v);
		double cosv = Math.cos(v);
		Vector3 dUV = this.u.clone().multiply(cosu*sinv).add(sinu*sinv,this.v);
		Vector3 dW = this.w.clone().multiply(cosv);
		Vector3[] points = new Vector3[2];
		points[0] = origin.clone().add(dUV).add(dW);
		points[1] = origin.clone().add(dUV).subtract(dW);
		return points;
	}
	
	
	/**
	 * Get a set of points on the ellipsoid
	 * with density 1.
	 * 
	 */
	
	public Vector3[][] render() {
		return render(0, 2*Math.PI, 0, Math.PI, 1);
	}
	
	/**
	 * Get a set of points on the ellipsoid
	 * with density (density).
	 * 
	 */
	public Vector3[][] render(double density) {
		return render(0, 2*Math.PI, 0, Math.PI, density);
	}
	
	/**
	 * Get a set of points on the surface of the 
	 * ellipsoid (Vector3[u][v]). All points are located 
	 * between (startU, startV) and (endU, endV).
	 * 
	 * Note that startU should be >= 0, endU <= 2*pi
	 * startV >= 0, endV <= pi.
	 * 
	 */
	public Vector3[][] render(double startU, double endU, double startV, double endV, double density) {
		double maxRadius = getMaxRadius();
		int pointAmountU = (int) (Math.abs(endU - startU) * maxRadius * density);
		int pointAmountV = (int) (Math.abs(endV - startV) * maxRadius * density);
		double stepU = (endU - startU) / pointAmountU;
		double stepV = (endV - startV) / pointAmountV;
		double pointU = startU;
		double pointV = startV;
		Vector3[][] points = new Vector3[pointAmountU][pointAmountV];
		for (int i = 0; i < pointAmountU; i++) {
			for (int j = 0; j < pointAmountV; j++) {
				points[i][j] = getPoint(pointU, pointV)[0];
				pointV += stepV;
			}
			pointV = startV;
			pointU += stepU;
		}
		return points;
	}
	
	/**
	 * Get a set of points randomly distributed
	 * on the surface of the ellipsoid with
	 * density 1.
	 * 
	 */
	public Vector3[] renderRandom() {
		return renderRandom(0, 2*Math.PI, 0, Math.PI, 1);
	}
	
	/**
	 * Get a set of points randomly distributed
	 * on the surface of the ellipsoid with
	 * density (density).
	 * 
	 */
	public Vector3[] renderRandom(double density) {
		return renderRandom(0, 2*Math.PI, 0, Math.PI, density);
	}
	
	/**
	 * Get a set of points randomly distributed
	 * on the surface of the ellipsoid. All points
	 * are located between (startU, startV) and 
	 * (endU, endV).
	 * 
	 * Note that startU should be >= 0, endU <= 2*pi
	 * startV >= 0, endV <= pi.
	 * 
	 */
	public Vector3[] renderRandom(double startU, double endU, double startV, double endV, double density) {
		Random random = new Random();
		double maxRadius = getMaxRadius();
		int pointAmount = (int) (4 / 3 * Math.PI * maxRadius * maxRadius * density * density);
		double dU = endU - startU;
		double dV = endV - startV;
		Vector3[] points = new Vector3[pointAmount];
		for (int i = 0; i < pointAmount; i++) {
			points[i] = getPoint(startU + random.nextDouble()*dU, startV + random.nextDouble()*dV)[0];
		}
		return points;
		
	}
}
