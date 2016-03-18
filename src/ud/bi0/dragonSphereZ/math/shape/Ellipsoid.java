package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Ellipsoid 
	extends BaseShape
	implements Cloneable {
	
	public static final Vector3d DEFAULT_RADIUS  = Vector3d.ONE;
	
	private Vector3d radius = DEFAULT_RADIUS;
	
	public Ellipsoid() {
	}
	
	public Ellipsoid(Ellipsoid ellipsoid) {
		this(ellipsoid.getOrigin(), ellipsoid.getBase(), ellipsoid.getRadius());
	}
	
	public Ellipsoid(Vector3d radius) {
		setRadius(radius);
	}
	
	public Ellipsoid(Vector3d origin, Base3d base, Vector3d radius) {
		super(origin, base);
		setRadius(radius);
	}
	
	public Vector3d getRadius() {
		return radius.clone();
	}
	
	public double getRadiusU() {
		return getRadius().getX();
	}
	
	public double getRadiusV() {
		return getRadius().getY();
	}
	
	public double getRadiusW() {
		return getRadius().getZ();
	}
	
	public void setRadius(double radius) {
		setRadius(radius, radius, radius);
	}
	
	public void setRadius(double radiusU, double radiusV, double radiusW) {
		setRadius(new Vector3d(radiusU, radiusV, radiusW));
	}
	public void setRadius(Vector3d radius) {
		this.radius = radius.clone(); 
	}
	
	public Ellipse getEllipse(double thetha) {
		thetha = GenericMath.wrapAngleRad(thetha);
		double sin = TrigMath.sin(thetha);
		Vector2d radius = new Vector2d(sin * getRadiusU(), sin * getRadiusV());
		return new Ellipse(getOrigin(), getBase(), radius);
	}

	public Vector3d getPoint(double thetha, double phi) {
		Vector3d point = Coordinate.Spherical3d.getPoint(getBase(), getRadius(), thetha, phi);
		return point.add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction thetha, IntToDoubleFunction phi) {
		ArrayList<Vector3d> points = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			points.add(getPoint(thetha.applyAsDouble(i), phi.applyAsDouble(i)));
		}
		return points;
	}
}
