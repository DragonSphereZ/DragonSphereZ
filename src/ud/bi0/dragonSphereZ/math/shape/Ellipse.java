package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;

import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;
import ud.bi0.dragonSphereZ.math.DoubleFunction;

public class Ellipse extends Shape {
	
	private final Base3d base;
	private final Vector2d radius;
	
	public Ellipse() {
		super();
		this.base = new Base3d();
		this.radius = new Vector2d(1,1);
	}
	
	public Ellipse(Ellipse ellipse) {
		this(ellipse.base, ellipse.radius);
	}
	
	public Ellipse(Base3d base, Vector2d radius) {
		this(base, radius.getX(), radius.getY());
	}
	
	public Ellipse(Base3d base, double radiusU, double radiusV) {
		super();
		this.base = new Base3d(base);
		this.radius = new Vector2d(radiusU, radiusV);
	}
	
	public Base3d getBase() {
		return new Base3d(base);
	}
	
	public Vector2d getRadius() {
		return new Vector2d(radius);
	}
	
	public double getRadiusU() {
		return radius.getX();
	}
	
	public double getRadiusV() {
		return radius.getY();
	}
	
	public Vector3d getAxis() {
		return new Vector3d(base.getW());
	}
	
	public Ellipse setBase(Base3d base) {
		return new Ellipse(base, this.radius);
	}
	
	public Ellipse setRadius(double radius) {
		return setRadius(radius, radius);
	}
	
	public Ellipse setRadius(Vector2d radius) {
		return setRadius(radius.getX(), radius.getY());
	}
	
	public Ellipse setRadius(double radiusU, double radiusV) {
		return new Ellipse(this.base, radiusU, radiusV);
	}
	
	public Ellipse transform(Matrix3d matrix) {
		return new Ellipse(base.transform(matrix), radius);
	}
	
	public Cylinder getCylinder(double height) {
		return new Cylinder(base, radius, height);
	}

	public Vector3d getPoint(double angle) {
		return getPoint(getRadiusU(), getRadiusV(), angle);
	}
	
	public Vector3d getPoint(double radiusU, double radiusV, double angle) {
		return Coordinate.Cylindrical3d.getPoint(this.base, radiusU, radiusV, angle, 0).add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction angle) {
		double radiusU = getRadiusU();
		double radiusV = getRadiusV();
		DoubleFunction u = (t) -> radiusU;
		DoubleFunction v = (t) -> radiusV;
		return getPointN(n, u, v, angle);
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction radiusU, DoubleFunction radiusV, DoubleFunction angle) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.apply(i), radiusV.apply(i), angle.apply(i)));
		}
		return points;
	}
}
