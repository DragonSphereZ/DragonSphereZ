package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;
import ud.bi0.dragonSphereZ.math.DoubleFunction;

public class Plane extends Shape {
	
	private final Base3d base;
	private final Vector2d radius;
	
	public Plane() {
		super();
		this.base = new Base3d();
		this.radius = new Vector2d(1, 1);
	}
	
	public Plane(Plane plane) {
		this(plane.base, plane.radius);
	}
	
	public Plane(Base3d base, Vector2d radius) {
		this(base, radius.getX(), radius.getY());
	}
	
	public Plane(Base3d base, double radius) {
		this(base, radius, radius);
	}
	
	public Plane(Base3d base, double radiusU, double radiusV) {
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
	
	public Plane setBase(Base3d base) {
		return new Plane(base, this.radius);
	}
	
	public Plane setRadius(double radius) {
		return setRadius(radius, radius);
	}
	
	public Plane setRadius(Vector2d radius) {
		return new Plane(this.base, radius.getX(), radius.getY());
	}
	
	public Plane setRadius(double radiusU, double radiusV) {
		return new Plane(this.base, radiusU, radiusV);
	}
	
	public Vector3d getPoint(double percentU, double percentV) {
		return getPoint(getRadius().getX(), getRadius().getY(), percentU, percentV);
	}
	public Vector3d getPoint(double radiusU, double radiusV, double percentU, double percentV) {
		return getPoint(new Vector2d(radiusU, radiusV), new Vector2d(percentU, percentV));
	}
	
	public Vector3d getPoint(Vector2d radius, Vector2d percent) {
		return Coordinate.Cartesian3d.getPoint(base, radius.toVector3(0).mul(-1).add(radius.toVector3(0).mul(percent.toVector3(0).mul(2)))).add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction percentU, DoubleFunction percentV) {
		double u = getRadius().getX();
		double v = getRadius().getY();
		DoubleFunction radiusU = (i) -> u;
		DoubleFunction radiusV = (i) -> v;
		return getPointN(n, radiusU, radiusV, percentU, percentV);
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction radiusU, DoubleFunction radiusV, DoubleFunction percentU, DoubleFunction percentV) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.apply(i), radiusV.apply(i), percentU.apply(i), percentV.apply(i)));
		}
		return points;
	}
}
