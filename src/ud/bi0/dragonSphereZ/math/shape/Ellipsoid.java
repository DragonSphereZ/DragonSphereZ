package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

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
	
	public Ellipsoid adjust(Vector3d from, Vector3d to) {
		return setBase(base.adjust(from, to));
	}
	
	public Ellipsoid rotate(Quaterniond rotation) {
		return setBase(base.rotate(rotation));	
	}
	
	public Ellipsoid transform(Matrix3d matrix) {
		return setBase(base.transform(matrix));
	}
	
	public Ellipse getEllipse(double radiusU, double radiusV, double thetha) {
		double sin = TrigMath.sin(thetha);
		return new Ellipse(this.base, sin * radiusU, sin * radiusV);
	}
	
	//TODO: this ;-p
	public Ellipse getEllipse(double radiusU, double radiusV, double thetha, double phi) {
		Vector3d dir = getPoint(thetha, phi).sub(getOrigin());
		return new Ellipse(this.getBase().adjust(this.getBase().getW(), dir), radiusU, radiusV);
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
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction thetha, IntToDoubleFunction phi) {
		double u = getRadius().getX();
		double v = getRadius().getY();
		double w = getRadius().getZ();
		IntToDoubleFunction radiusU = (i) -> u;
		IntToDoubleFunction radiusV = (i) -> v;
		IntToDoubleFunction radiusW = (i) -> w;
		return getPointN(n, radiusU, radiusV, radiusW, thetha, phi);	
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction radius, IntToDoubleFunction thetha, IntToDoubleFunction phi) {
		return getPointN(n, radius, radius, radius, thetha, phi);
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction radiusU, IntToDoubleFunction radiusV, IntToDoubleFunction radiusW, IntToDoubleFunction thetha, IntToDoubleFunction phi ) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(radiusU.applyAsDouble(i), radiusV.applyAsDouble(i), radiusW.applyAsDouble(i), thetha.applyAsDouble(i), phi.applyAsDouble(i)));
		}
		return points;
	}
}
