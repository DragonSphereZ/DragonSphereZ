package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.matrix.Matrix2d;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Cylinder 
	extends BaseShape 
	implements Cloneable {
	
	public static final Vector2d DEFAULT_RADIUS = Vector2d.ONE;
	public static final double DEFAULT_START_HEIGHT = 0;
	public static final double DEFAULT_END_HEIGHT = 1;
	
	private Vector2d radius = DEFAULT_RADIUS;
	private double startHeight = DEFAULT_START_HEIGHT; 
	private double endHeight = DEFAULT_END_HEIGHT;
	
	public Cylinder() {
	}
	
	public Cylinder(Cylinder cylinder) {
		this(cylinder.getOrigin(), cylinder.getBase(), cylinder.getRadius(), cylinder.getStartHeight(), cylinder.getEndHeight());
	}
	
	public Cylinder(Vector2d radius, double startHeight, double endHeight) {
		setRadius(radius);
		setStartHeight(startHeight);
		setEndHeight(endHeight);
	}
	
	public Cylinder(Vector3d origin, Base3d base, Vector2d radius, double startHeight, double endHeight) {
		super(origin, base);
		setRadius(radius);
		setStartHeight(startHeight);
		setEndHeight(endHeight);
	}
	
	public Vector2d getRadius() {
		return radius.clone();
	}
	
	public Vector3d getAxis() {
		return getBase().getW();
	}
	
	public double getStartHeight() {
		return startHeight;
	}
	
	public double getEndHeight() {
		return endHeight;
	}

	public void setRadius(Vector2d radius) {
		this.radius = radius.clone();
	}
	
	public void setRadius(double radiusU, double radiusV) {
		setRadius(new Vector2d(radiusU, radiusV));
	}
	
	public void setRadius(double radius) {
		setRadius(radius, radius);
	}
	
	public void setStartHeight(double startHeight) {
		this.startHeight = startHeight;
	}
	
	public void setEndHeight(double endHeight) {
		this.endHeight = endHeight;
	}
	
	public void transformRadius(Matrix2d matrix) {
		Vector2d radius = getRadius();
		radius = matrix.transform(radius);
		setRadius(radius);
	}
	
	public Vector3d getPoint(double angle, double percentHeight) {
		double height = GenericMath.lerp(getStartHeight(), getEndHeight(), percentHeight);
		Vector3d point = Coordinate.Cylindrical3d.getPoint(getBase(), getRadius(), angle, height).add(getOrigin());
		return point.add(getOrigin());
	}

	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction angle, IntToDoubleFunction percentHeight) {
		ArrayList<Vector3d> points = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(angle.applyAsDouble(i), percentHeight.applyAsDouble(i)));
		}
		return points;
	}
	
	@Override
	public Cylinder clone() {
		return new Cylinder(this);
	}
}
