package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Plane 
	extends BaseShape
	implements Cloneable {
	
	public static final Vector2d DEFAULT_RADIUS = Vector2d.ONE;
	
	private Vector2d radius = DEFAULT_RADIUS;
	
	public Plane() {
	}
	
	public Plane(Plane plane) {
		this(plane.getOrigin(), plane.getBase(), plane.getRadius());
	}
	
	public Plane(Vector2d radius) {
		setRadius(radius);
	}
	
	public Plane(Vector3d origin, Base3d base, Vector2d radius) {
		super(origin, base);
		setRadius(radius);
	}
	
	public Plane(Vector3d origin, Vector3d point1, Vector3d point2) {
		super(origin, DEFAULT_BASE);
		Vector3d u = point1.sub(origin);
		Vector3d v = point2.sub(origin);
		Vector2d radius = new Vector2d(u.length(), v.length());
		u = u.normalize();
		v = v.normalize();
		Vector3d normal = u.cross(v);
		adjustBase(DEFAULT_BASE.getW(), normal);
		setRadius(radius);
	}
	
	public Vector2d getRadius() {
		return radius.clone();
	}
	
	public double getRadiusU() {
		return getRadius().getX();
	}
	
	public double getRadiusV() {
		return getRadius().getY();
	}
	
	public void setRadius(double radius) {
		setRadius(radius, radius);
	}
	
	public void setRadius(double radiusU, double radiusV) {
		setRadius(new Vector2d(radiusU, radiusV));
	}
	public void setRadius(Vector2d radius) {
		this.radius = radius.clone();
	}
		
	public Vector3d getPoint(double percentU, double percentV) {
		double u = getRadiusU() * ( 2 * percentU - 1);
		double v = getRadiusV() * ( 2 * percentV - 1);
		Vector3d point = Coordinate.Cartesian3d.getPoint(getBase(), new Vector3d(u,v,0));
		return point.add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction percentU, IntToDoubleFunction percentV) {
		ArrayList<Vector3d> points = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(percentU.applyAsDouble(i), percentV.applyAsDouble(i)));
		}
		return points;
	}
	
	@Override
	public Plane clone() {
		return new Plane(this);
	}
}
