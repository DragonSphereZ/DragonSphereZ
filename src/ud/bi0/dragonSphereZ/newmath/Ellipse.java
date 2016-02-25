package ud.bi0.dragonSphereZ.newmath;

import ud.bi0.dragonSphereZ.newmath.CylindricalCoordinates;
import com.flowpowered.math.vector.Vector3d;

public class Ellipse extends Shape {
	
	private Base3d base;
	private double radiusU;
	private double radiusV;
	
	public Ellipse() {
		super();
		this.base = new Base3d();
		this.radiusU = 1;
		this.radiusV = 1;
	}
	
	public Base3d getBase() {
		return base;
	}
	
	public double getRadiusU() {
		return radiusU;
	}
	
	public double getRadiusV() {
		return radiusV;
	}
	
	public void setBase(Base3d base) {
		this.base = base;
	}
	
	public void setRadiusU(double radiusU) {
		this.radiusU = radiusU;
	}
	
	public void setRadiusV(double radiusV) {
		this.radiusV = radiusV;
	}
	
	public Vector3d getPoint(double angle) {
		return CylindricalCoordinates.getPoint(base, radiusU, radiusV, angle, 0);
	}
}
