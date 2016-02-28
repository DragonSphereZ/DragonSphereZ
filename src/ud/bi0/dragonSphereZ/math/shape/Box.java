package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;

import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;
import ud.bi0.dragonSphereZ.math.DoubleFunction;

public class Box extends Shape {
	
	private final Base3d base;
	private final Vector3d radius;
	
	public Box() {
		super();
		this.base = new Base3d();
		this.radius = new Vector3d(1,1,1);
	}
	
	public Box(Box box) {
		this(box.getBase(), box.getRadius());
	}
	
	public Box(Base3d base, Vector3d radius) {
		this(base, radius.getX(), radius.getY(), radius.getZ());
	}
	
	public Box(Base3d base, double radiusU, double radiusV, double radiusW) {
		super();
		this.base = new Base3d(base);
		this.radius = new Vector3d(radiusU, radiusV, radiusW);
	}
	
	public Base3d getBase() {
		return new Base3d(base);
	}
	
	public Vector3d getRadius() {
		return new Vector3d(radius);
	}
	
	public Box setBase(Base3d base) {
		return new Box(base, this.radius);
	}
	
	public Box setRadius(double radius) {
		return setRadius(radius, radius, radius);
	}
	
	public Box setRadius(double radiusU, double radiusV, double radiusW) {
		return setRadius(new Vector3d(radiusU, radiusV, radiusW));
	}
	
	public Box setRadius(Vector3d radius) {
		return new Box(this.base, radius);
	}
	
	public Box transform(Matrix3d matrix) {
		return new Box(base.transform(matrix), radius);
	}
	
	public Box transformRadius(Matrix3d matrix) {
		return new Box(base, matrix.transform(radius));
	}
	
	public Vector3d getPoint(double percentU, double percentV, double percentW) {
		return getPoint(this.getRadius(), new Vector3d(percentU, percentV, percentW));
	}
	
	public Vector3d getPoint(double radiusU, double radiusV, double radiusW, double percentU, double percentV, double percentW) {
		return getPoint(new Vector3d(radiusU, radiusV, radiusW), new Vector3d(percentU, percentV, percentW));
	}
	
	public Vector3d getPoint(Vector3d radius, Vector3d percent) {
		return Coordinate.Cartesian3d.getPoint(this.base, radius.mul(-1).add(radius.mul(percent).mul(2))).add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPoint(int n, DoubleFunction percentU, DoubleFunction percentV, DoubleFunction percentW) {
		double u = getRadius().getX();
		double v = getRadius().getY();
		double w = getRadius().getZ();
		DoubleFunction radiusU = (i) -> u;
		DoubleFunction radiusV = (i) -> v;
		DoubleFunction radiusW = (i) -> w;
		return getPointN(n, radiusU, radiusV, radiusW, percentU, percentV, percentW);
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction radiusU, DoubleFunction radiusV, DoubleFunction radiusW, DoubleFunction percentU, DoubleFunction percentV, DoubleFunction percentW) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.apply(i), radiusV.apply(i), radiusU.apply(i), percentU.apply(i), percentV.apply(i), percentW.apply(i)));
		}
		return points;
	}
	
}
