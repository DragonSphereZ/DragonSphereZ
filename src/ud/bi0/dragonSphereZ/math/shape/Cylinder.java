package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;

import com.flowpowered.math.matrix.Matrix2d;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;
import ud.bi0.dragonSphereZ.math.DoubleFunction;

public class Cylinder extends Shape {
	
	private final Base3d base;
	private final Vector2d radius;
	private final double height;
	
	public Cylinder() {
		super();
		this.base = new Base3d();
		this.radius = new Vector2d(1,1);
		this.height = 1;
	}
	
	public Cylinder(Cylinder cylinder) {
		this(cylinder.base, cylinder.radius, cylinder.height);
	}
	
	public Cylinder(Base3d base, Vector2d radius, double height) {
		this(base, radius.getX(), radius.getY(), height);
	}
	
	public Cylinder(Base3d base, double radiusU, double radiusV, double height) {
		super();
		this.base = new Base3d(base);
		this.radius = new Vector2d(radiusU, radiusV);
		this.height = height;
	}
	
	public Base3d getBase() {
		return new Base3d(base);
	}
	
	public Vector2d getRadius() {
		return new Vector2d(radius);
	}
	
	public double getHeight() {
		return height;
	}
	
	public Cylinder setBase(Base3d base) {
		return new Cylinder(base, this.radius, this.height);
	}
	
	public Cylinder setRadius(Vector2d radius) {
		return new Cylinder(this.base, radius, this.height);
	}
	
	public Cylinder setRadius(double radiusU, double radiusV) {
		return setRadius(new Vector2d(radiusU, radiusV));
	}
	
	public Cylinder setRadius(double radius) {
		return setRadius(new Vector2d(radius, radius));
	}
	
	public Cylinder setHeight(double height) {
		return new Cylinder(this.base, this.radius, height);
	}
	
	public Cylinder transform(Matrix3d matrix) {
		return new Cylinder(base.transform(matrix), radius, height);
	}
	
	public Cylinder transformRadius(Matrix2d matrix) {
		return new Cylinder(base, matrix.transform(radius), height);
	}
	
	public Vector3d getPoint(double angle, double height) {
		return getPoint(this.radius, angle, height);
	}
	
	public Vector3d getPoint(Vector2d radius, double angle, double height) {
		return getPoint(radius.getX(), radius.getY(), angle, height);
	}
	
	public Vector3d getPoint(double radiusU, double radiusV, double angle, double height) {
		return Coordinate.Cylindrical3d.getPoint(base, radiusU, radiusV, angle, height).add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction angle, DoubleFunction height) {
		double u = this.radius.getX();
		double v = this.radius.getY();
		DoubleFunction radiusU = (i) -> u;
		DoubleFunction radiusV = (i) -> v;
		return getPointN(n, radiusU, radiusV, angle, height);
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction radiusU, DoubleFunction radiusV, DoubleFunction angle, DoubleFunction height) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.apply(i), radiusV.apply(i), angle.apply(i), height.apply(i)));
		}
		return points;
	}
}
