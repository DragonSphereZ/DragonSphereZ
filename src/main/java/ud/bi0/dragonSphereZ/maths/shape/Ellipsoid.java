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
		return Math.max(base.getU().lengthSquared(), Math.max(base.getV().lengthSquared(), base.getW().lengthSquared()));
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
		int pointAmount = (int) density;
		double stepRadiusU = endRadiusU - startRadiusU;
		double stepRadiusV = endRadiusV - startRadiusV;
		double stepRadiusW = endRadiusW - startRadiusW;
		double stepAngleThetha = endAngleThetha - startAngleThetha;
		double stepAnglePhi = endAnglePhi - startAnglePhi;
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
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 	THIS PART IS NOT YET UPDATED!
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
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
				points[i][j] = getPoint(pointU, pointV);
				pointV += stepV;
			}
			pointV = startV;
			pointU += stepU;
		}
		return points;
	}
}
