package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.DoubleFunction;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

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
	
	public Ellipse adjust(Vector3d from, Vector3d to) {
		return setBase(base.adjust(from, to));
	}
	
	public Ellipse rotate(Quaterniond rotation) {
		return setBase(base.rotate(rotation));
	}
	
	public Ellipse transform(Matrix3d matrix) {
		return setBase(base.transform(matrix));
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
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction angle) {
		double u = getRadiusU();
		double v = getRadiusV();
		IntToDoubleFunction radiusU = (int i) -> u;
		IntToDoubleFunction radiusV = (int i) -> v;
		return getPointN(n, radiusU, radiusV, angle);
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction radiusU, IntToDoubleFunction radiusV, IntToDoubleFunction angle) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.applyAsDouble(i), radiusV.applyAsDouble(i), angle.applyAsDouble(i)));
		}
		return points;
	}
}
