package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;


public class Line 
	extends BaseShape
	implements Cloneable {

	public static final Vector3d DEFAULT_START = Vector3d.ZERO;
	public static final Vector3d DEFAULT_END = Vector3d.ONE;
	public static final Vector3d DEFAULT_DIRECTION = Vector3d.ONE;
	
	private Vector3d start = DEFAULT_START;
	private Vector3d end = DEFAULT_END;
	private Vector3d direction = DEFAULT_DIRECTION;
	
	public Line() {
	}
	
	public Line(Line line) {
		this(line.getOrigin(), line.getBase(), line.getStart(), line.getEnd());
	}
	
	public Line(Vector3d start, Vector3d end) {
		this(DEFAULT_ORIGIN, DEFAULT_BASE, start, end);
	}
	
	public Line(Vector3d origin, Base3d base, Vector3d start, Vector3d end) {
		super(origin, base);
		setStart(start);
		setEnd(end);
	}
	
	public void setStart(Vector3d start) {
		if (!start.equals(getEnd())) {
			this.start = start.clone();
			updateDirection();
		}
	}
	
	public void setEnd(Vector3d end) {
		if (!end.equals(getStart())) {
			this.end = end.clone();
			updateDirection();
		}
	}
	
	public Vector3d getStart() {
		return start.clone();
	}
	
	public Vector3d getEnd() {
		return end.clone();
	}
	
	public Vector3d getDirection() {
		return direction.clone();
	}
	
	public void transform(Matrix3d matrix) {
		setStart(matrix.transform(getStart()));
		setEnd(matrix.transform(getEnd()));
	}
	
	public Vector3d getPoint(double percent) {
		Vector3d point = GenericMath.lerp(getStart(), getEnd(), percent);
		return point.add(getOrigin());
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction percent) {
		ArrayList<Vector3d> points = new ArrayList<>(n);
		for (int i = 0; i<n; i++) {
			points.add(getPoint(percent.applyAsDouble(i)));
		}
		return points;
	}
	
	private void updateDirection() {
		this.direction = getEnd().sub(getStart()).normalize();
	}
	
	@Override
	public Line clone() {
		return new Line(this);
	}
	
}
