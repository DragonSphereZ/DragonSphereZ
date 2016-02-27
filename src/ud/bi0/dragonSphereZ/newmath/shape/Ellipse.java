package ud.bi0.dragonSphereZ.newmath.shape;

import ud.bi0.dragonSphereZ.newmath.Base3d;
import ud.bi0.dragonSphereZ.newmath.CylindricalCoordinate3d;
import ud.bi0.dragonSphereZ.newmath.DoubleFunction;
import ud.bi0.dragonSphereZ.newmath.Shape;

import java.util.ArrayList;
import java.util.Iterator;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

public class Ellipse extends Shape {
	
	private Base3d base;
	private Vector2d radius;
	
	public Ellipse() {
		super();
		this.setBase(new Base3d());
		this.setRadius(new Vector2d(1,1));
	}
	
	public Base3d getBase() {
		return base;
	}
	
	public Vector2d getRadius() {
		return radius;
	}
	
	public double getRadiusU() {
		return radius.getX();
	}
	
	public double getRadiusV() {
		return radius.getY();
	}
	
	public Ellipse setBase(Base3d base) {
		this.base = base;
		return this;
	}
	
	public Ellipse setRadiusU(double radiusU) {
		setRadius(radiusU, getRadiusV());
		return this;
	}
	
	public Ellipse setRadiusV(double radiusV) {
		setRadius(getRadiusU(), radiusV);
		return this;
	}
	
	public Ellipse setRadius(double radiusU, double radiusV) {
		this.setRadius(new Vector2d(radiusU, radiusV));
		return this;
	}
	
	public Ellipse setRadius(Vector2d radius) {
		this.radius = radius;
		return this;
	}

	public Vector3d getPoint(double angle) {
		return getPoint(getRadiusU(), getRadiusV(), angle);
	}
	
	public Vector3d getPoint(double radiusU, double radiusV, double angle) {
		return CylindricalCoordinate3d.getPoint(base, radiusU, radiusV, angle, 0);
	}
	
	public ArrayList<Vector3d> getPointN( int n, double radiusU, double radiusV, double angle, DoubleFunction funRadiusU, DoubleFunction funRadiusV, DoubleFunction funAngle) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(funRadiusU.apply(i, x)))
		}
		return points;
	}
}
