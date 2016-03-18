package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Ellipse 
	extends BaseShape 
	implements Cloneable {
	
	public static final Vector2d DEFAULT_RADIUS = Vector2d.ONE;
	
	private Vector2d radius = DEFAULT_RADIUS;
	
	public Ellipse() {
	}
	
	public Ellipse(Ellipse ellipse) {
		this(ellipse.getOrigin(), ellipse.getBase(), ellipse.getRadius());
	}
	
	public Ellipse(Vector2d radius) {
		setRadius(radius);
	}
	
	public Ellipse(Vector3d origin, Base3d base, Vector2d radius) {
		super(origin, base);
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
	
	public Vector3d getAxis() {
		return getBase().getW();
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
	
	public Cylinder getCylinder(double startHeight, double endHeight) {
		return new Cylinder(getOrigin(), getBase(), getRadius(), startHeight, endHeight);
	}

	public Vector3d getPoint(double angle) {
		Vector3d point = Coordinate.Cylindrical3d.getPoint(getBase(), getRadius(), angle, 0);
		return point.add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction angle) {
		ArrayList<Vector3d> points = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(angle.applyAsDouble(i)));
		}
		return points;
	}
	
	@Override
	public Ellipse clone() {
		return new Ellipse(this);
	}
}
