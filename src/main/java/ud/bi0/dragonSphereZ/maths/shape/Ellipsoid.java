package ud.bi0.dragonSphereZ.maths.shape;

import java.util.ArrayList;
import java.util.List;

import ud.bi0.dragonSphereZ.maths.base.Base3;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Ellipsoid extends Shape {
	
	/**
	 * An ellipsoid is defined by a center point (origin)
	 * and three base vectors (u,v,w).
	 * 
	 */
	protected Vector3 origin;
	protected Base3 base;
	
	
	public Ellipsoid(Vector3 origin, Base3 base) {
		this.origin = origin;
		this.base = base;
	}
	
	/**
	 * Creates an ellipsoid at origin with the
	 * vectors u, v and w as base.
	 * 
	 */
	public Ellipsoid(Vector3 origin, Vector3 u, Vector3 v, Vector3 w) {
		this.origin = origin;
		this.base = new Base3(u,v,w);
	}
	
	/**
	 * Creates an ellipsoid at origin with the 
	 * three radii x, y and z on their respective axis.
	 * 
	 */
	public Ellipsoid(Vector3 origin, double x, double y, double z) {
		this.origin = origin;
		this.base = new Base3(x,y,z);
	}
	
	/**
	 * Creates a sphere at origin with a certain radius.
	 */
	public Ellipsoid(Vector3 origin, double radius) {
		this.origin = origin;
		this.base = new Base3(radius, radius, radius);
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
	
	public Base3 getBase() {
		return base;
	}
	
	public Vector3 getPoint(double angleThetha, double anglePhi) {
		return getPoint(1, 1, 1, angleThetha, anglePhi);
	}
	
	public Vector3 getPoint(double radius, double angleThetha, double anglePhi) {
		return getPoint(radius, radius, radius, angleThetha, anglePhi);
	}
	
	/**
	 * Returns a point on the ellipsoid.
	 * 
	 */
	public Vector3 getPoint(double radiusU, double radiusV, double radiusW, double angleThetha, double anglePhi) {
		return origin.getSphereCoordinate(base.getU(), base.getV(), base.getW(), radiusU, radiusV, radiusW, angleThetha, anglePhi);
	}
	
	public List<Vector3> renderSpiral(
			double startRadiusU,
			double endRadiusU,
			double startRadiusV,
			double endRadiusV,
			double startRadiusW,
			double endRadiusW,
			double startAngleThetha,
			double endAngleThetha,
			double startAnglePhi,
			double endAnglePhi,
			double density)
	{
		double maxRadius = Math.max(
				Math.max(Math.abs(startRadiusU),Math.abs(endRadiusU))*base.getU().length(), 
				Math.max(Math.max(Math.abs(startRadiusV),Math.abs(endRadiusV))*base.getV().length(),
						 Math.max(Math.abs(startRadiusW),Math.abs(endRadiusW))*base.getW().length()));
		double maxAngle = Math.max(Math.abs(startAngleThetha-endAngleThetha), Math.abs(startAnglePhi-endAnglePhi));
		int pointAmount = (int) (maxRadius * maxAngle * density);
		
		double stepRadiusU = (endRadiusU - startRadiusU) / pointAmount;
		double stepRadiusV = (endRadiusV - startRadiusV) / pointAmount;
		double stepRadiusW = (endRadiusW - startRadiusW) / pointAmount;
		double stepAngleThetha = (endAngleThetha - startAngleThetha) / pointAmount;
		double stepAnglePhi = (endAnglePhi - startAnglePhi) / pointAmount;
		double radiusU = startRadiusU;
		double radiusV = startRadiusV;
		double radiusW = startRadiusW;
		double angleThetha = startAngleThetha;
		double anglePhi = startAnglePhi;
		List<Vector3> points = new ArrayList<Vector3>(pointAmount);
		for (int i = 0; i < pointAmount; i++) {
			points.add(getPoint(radiusU, radiusV, radiusW, angleThetha, anglePhi));
			radiusU += stepRadiusU;
			radiusV += stepRadiusV;
			radiusW += stepRadiusW;
			angleThetha += stepAngleThetha;
			anglePhi += stepAnglePhi;
		}
		return points;
	}
	
}
