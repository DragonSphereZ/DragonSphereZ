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
	
	public Ellipse setBase(Base3d base) {
		this.base = base;
		return this;
	}
	
	public Ellipse setRadiusU(double radiusU) {
		this.radiusU = radiusU;
		return this;
	}
	
	public Ellipse setRadiusV(double radiusV) {
		this.radiusV = radiusV;
		return this;
	}
	
	public Vector3d getPoint(double angle) {
		return CylindricalCoordinates.getPoint(base, radiusU, radiusV, angle, 0);
	}
}
