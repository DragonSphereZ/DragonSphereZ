package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;
import ud.bi0.dragonSphereZ.math.DoubleFunction;

public class Ellipsoid extends Shape {
	
	private final Base3d base;
	private final Vector3d radius;
	
	public Ellipsoid(){
		super();
		base = new Base3d();
		radius = new Vector3d(1,1,1);
	}
	
	public Ellipsoid(Ellipsoid ellipsoid) {
		this(ellipsoid.base, ellipsoid.radius);
	}
	
	public Ellipsoid(Base3d base, Vector3d radius) {
		this(base, radius.getX(), radius.getY(), radius.getZ());
	}
	
	public Ellipsoid(Base3d base, double radiusU, double radiusV, double radiusW) {
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
	
	public Ellipsoid setBase(Base3d base) {
		return new Ellipsoid(base, this.radius);
	}
	
	public Ellipsoid setRadius(double radius) {
		return setRadius(radius, radius, radius);
	}
	
	public Ellipsoid setRadius(Vector3d radius) {
		return setRadius(radius.getX(), radius.getY(), radius.getZ());
	}
	
	public Ellipsoid setRadius(double radiusU, double radiusV , double radiusW) {
		return new Ellipsoid(this.base, radiusU, radiusV, radiusW);
	}
	
	public Ellipse getEllipse(double radiusU, double radiusV, double thetha) {
		double sin = TrigMath.sin(thetha);
		return new Ellipse(this.base, sin * radiusU, sin * radiusV);
	}
	
	//TODO:
	public Ellipse getEllipse(double radiusU, double radiusV, double thetha, double phi) {
		Ellipse ellipse = new Ellipse();
		Vector3d dir = getPoint(radiusU, radiusV, radius.getZ(), thetha, phi).sub(getOrigin());
		return ellipse;
		
	}
	
	public Vector3d getPoint(double thetha, double phi) {
		return getPoint(getRadius(), thetha, phi);
	}
	
	public Vector3d getPoint(Vector3d radius, double thetha, double phi) {
		return getPoint(radius.getX(), radius.getY(), radius.getZ(), thetha, phi);
	}
	
	public Vector3d getPoint(double radiusU, double radiusV, double radiusW, double thetha, double phi) {
		return Coordinate.Spherical3d.getPoint(getBase(), radiusU, radiusV, radiusW, thetha, phi).add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction thetha, DoubleFunction phi) {
		double u = getRadius().getX();
		double v = getRadius().getY();
		double w = getRadius().getZ();
		DoubleFunction radiusU = (i) -> u;
		DoubleFunction radiusV = (i) -> v;
		DoubleFunction radiusW = (i) -> w;
		return getPointN(n, radiusU, radiusV, radiusW, thetha, phi);	
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction radius, DoubleFunction thetha, DoubleFunction phi) {
		return getPointN(n, radius, radius, radius, thetha, phi);
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction radiusU, DoubleFunction radiusV, DoubleFunction radiusW, DoubleFunction thetha, DoubleFunction phi ) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.apply(i), radiusV.apply(i), radiusW.apply(i), thetha.apply(i), phi.apply(i)));
		}
		return points;
	}
}
