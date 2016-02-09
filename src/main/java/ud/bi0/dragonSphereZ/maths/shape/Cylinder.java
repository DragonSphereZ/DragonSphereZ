package ud.bi0.dragonSphereZ.maths.shape;

import java.util.ArrayList;
import java.util.List;

import ud.bi0.dragonSphereZ.maths.base.Base2;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Cylinder extends Shape {
	
	/**
	 * An cylinder is defined by an origin two
	 * base vectors (u,v).
	 * The normal vector is orthogonal to u and v
	 * and has a length of 1.
	 * 
	 */
	protected Vector3 origin;
	protected Base2 base;
	
	/**
	 * Creates an cylinder at a point (origin) with the 
	 * two bases u and v.
	 * 
	 */
	public Cylinder(Vector3 origin, Vector3 u, Vector3 v) {
		this.origin = origin;
		this.base = new Base2(u,v);
	}
	
	/**
	 * Creates an cylinder at a point (origin) with the
	 * two radii x and y on the respective axis.
	 * 
	 */
	public Cylinder(Vector3 origin, double x, double y) {
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
		return getPoint(angle, 1, 0);
	}
	
	public Vector3 getPoint(double radius, double angle) {
		return getPoint(angle, radius, 0);
	}
	
	public Vector3 getPoint(double radius, double angle, double height) {
		return getPoint(radius, radius, angle, height);
	}
	
	public Vector3 getPoint(double radiusU, double radiusV, double angle, double height) {
		return origin.getCylinderCoordinate(base.getNormal(), base.getU(), base.getV(), radiusU, radiusV, angle, height);
	}
	
	public List<Vector3> renderEllipse() {
		return renderEllipse(1);
	}
	
	public List<Vector3> renderEllipse(double density) {
		return renderEllipse(0, 2*Math.PI, 0, density);
	}
	
	public List<Vector3> renderEllipse(double startAngle, double endAngle, double height, double density) {
		return renderSpiral(1, 1, 1, 1, startAngle, endAngle, height, height, density);
	}
	
	public List<Vector3> renderSpiral(double startRadiusU, double endRadiusU, double startRadiusV, double endRadiusV, double startAngle, double endAngle, double startHeight, double endHeight, double density) {
		
		double totalAngle = Math.abs(endAngle - startAngle);
		double maxRadius = Math.max(
							Math.max(Math.abs(startRadiusU),Math.abs(endRadiusU))*base.getU().length(), 
							Math.max(Math.abs(startRadiusV),Math.abs(endRadiusV))*base.getV().length());
		
		int pointAmount = (int) (totalAngle * maxRadius * density);
		double stepRadiusU = (endRadiusU - startRadiusU) / pointAmount;
		double stepRadiusV = (endRadiusV - startRadiusV) / pointAmount;
		double stepAngle = (endAngle - startAngle) / pointAmount;
		double stepHeight = (endHeight - startHeight) / pointAmount;
		double radiusU = startRadiusU;
		double radiusV = startRadiusV;
		double angle = startAngle;
		double height = startHeight;
		List<Vector3> points = new ArrayList<Vector3>(pointAmount);
		for (int i = 0; i < pointAmount; i++) {
			points.add(getPoint(radiusU, radiusV, angle, height));
			radiusU += stepRadiusU;
			radiusV += stepRadiusV;
			angle += stepAngle;
			height += stepHeight;
		}
		return points;
	}
}
