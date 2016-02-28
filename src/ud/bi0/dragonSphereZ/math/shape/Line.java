package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.DoubleFunction;

public class Line extends Shape	{

	private final Vector3d start;
	private final Vector3d end;
	private final Vector3d direction;
	
	
	public Line() {
		super();
		start = new Vector3d();
		end = new Vector3d(1,0,0);
		direction = new Vector3d(1,0,0);
	}
	
	public Line(Line line) {
		this(line.start, line.end);
	}
	
	public Line(Vector3d start, Vector3d end) {
		super();
		this.start = new Vector3d(start);
		this.end = new Vector3d(end);
		this.direction = new Vector3d();
		this.updateDirection();
	}
	
	public Line setStart(Vector3d start) {
		return new Line(start, this.end);
	}
	
	public Line setEnd(Vector3d end) {
		return new Line(this.start, end);
	}
	
	public Vector3d getStart() {
		return new Vector3d(start);
	}
	
	public Vector3d getEnd() {
		return new Vector3d(end);
	}
	
	public Vector3d getDirection() {
		return new Vector3d(direction);
	}
	
	public Line transform(Matrix3d matrix) {
		return new Line(matrix.transform(start), matrix.transform(end));
	}
	
	/**
	 * Get a point from a percent between start and end.
	 * 
	 */
	public Vector3d getPoint(double percent) {
		return GenericMath.lerp(start, end, percent).add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction percent) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i<n; i++) {
			points.add(getPoint(percent.apply(i)));
		}
		return points;
	}
	
	private void updateDirection() {
		direction = end.sub(start).normalize();
	}
	
	
}
