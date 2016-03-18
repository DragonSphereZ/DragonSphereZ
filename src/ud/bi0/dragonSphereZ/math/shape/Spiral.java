package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Spiral 
	extends BaseShape
	implements Cloneable {

	public static final Vector2d DEFAULT_START_RADIUS = Vector2d.ONE;
	public static final Vector2d DEFAULT_END_RADIUS	= Vector2d.ZERO;
	public static final double DEFAULT_START_HEIGHT = 0;
	public static final double DEFAULT_END_HEIGHT = 1;
	public static final double DEFAULT_SLOPE = 1;
	
	private Vector2d startRadius = DEFAULT_START_RADIUS;
	private Vector2d endRadius = DEFAULT_END_RADIUS;
	private double startHeight = DEFAULT_START_HEIGHT;
	private double endHeight = DEFAULT_END_HEIGHT;
	private double slope = DEFAULT_SLOPE;
	
	public Spiral() {
	}
	
	public Spiral(Spiral spiral) {
		this(spiral.getOrigin(), spiral.getBase(), spiral.getStartRadius(), spiral.getEndRadius(), spiral.getStartHeight(), spiral.getEndHeight(), spiral.getSlope());
	}
	
	public Spiral(Vector2d startRadius, Vector2d endRadius, double startHeight, double endHeight, double slope) {
		this(DEFAULT_ORIGIN, DEFAULT_BASE, startRadius, endRadius, startHeight, endHeight, slope);
	}
	
	public Spiral(Vector3d origin, Base3d base, Vector2d startRadius, Vector2d endRadius, double startHeight, double endHeight, double slope) {
		super(origin, base);
		this.setStartRadius(startRadius);
		this.setEndRadius(endRadius);
		this.setStartHeight(startHeight);
		this.setEndHeight(endHeight);
		this.setSlope(slope);
	}

	public Vector2d getStartRadius() {
		return startRadius.clone();
	}
	
	public Vector2d getEndRadius() {
		return endRadius.clone();
	}
	
	public double getStartHeight() {
		return startHeight;
	}
	
	public double getEndHeight() {
		return endHeight;
	}
	
	public double getSlope() {
		return slope;
	}
	
	public double getTotalHeight() {
		double height = Math.abs(this.getEndHeight() - this.getStartHeight());
		return height;
	}
	
	public double getTotalRotations() {
		double rotations = this.getTotalHeight() / this.getSlope();
		return rotations;
	}
	
	public void setStartRadius(double startRadius) {
		this.setStartRadius(startRadius, startRadius);
	}
	
	public void setStartRadius(double startRadiusU, double startRadiusV) {
		this.setStartRadius(new Vector2d(startRadiusU, startRadiusV));
	}

	public void setStartRadius(Vector2d startRadius) {
		this.startRadius = startRadius.clone();
	}
	
	public void setEndRadius(double endRadius) {
		this.setEndRadius(endRadius, endRadius);
	}
	
	public void setEndRadius(double endRadiusU, double endRadiusV) {
		this.setEndRadius(new Vector2d(endRadiusU, endRadiusV));
	}
	
	public void setEndRadius(Vector2d endRadius) {
		this.endRadius = endRadius.clone();
	}
	
	public void setStartHeight(double startHeight) {
		this.startHeight = startHeight;
	}
	
	public void setEndHeight(double endHeight) {
		this.endHeight = endHeight;
	}
	
	public void setSlope(double slope) {
		this.slope = Math.abs(slope);
	}
	
	public Vector3d getPoint(double percentHeight) {
		double height = GenericMath.lerp(this.getStartHeight(), this.getEndHeight(), percentHeight);
		double radiusU = GenericMath.lerp(this.getStartRadius().getX(), this.getEndRadius().getX(), percentHeight);
		double radiusV = GenericMath.lerp(this.getStartRadius().getY(), this.getEndRadius().getY(), percentHeight);
		double angle = height / this.getSlope();
		angle = angle - GenericMath.floor(angle);
		angle *= TrigMath.TWO_PI;
		Vector3d point = Coordinate.Cylindrical3d.getPoint(this.getBase(), radiusU, radiusV, angle, height).add(this.getOrigin());
		return point;
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction percentHeight) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(percentHeight.applyAsDouble(i)));
		}
		return points;
	}
	
	@Override
	public Spiral clone() {
		return new Spiral(this);
	}
	
}
