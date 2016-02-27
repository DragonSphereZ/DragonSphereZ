package ud.bi0.dragonSphereZ.newmath.shape;

import java.util.ArrayList;
import java.util.List;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.newmath.DoubleFunction;
import ud.bi0.dragonSphereZ.newmath.Shape;

public class Line extends Shape	{

	private Vector3d start;
	private Vector3d end;
	private Vector3d direction;
	
	
	public Line() {
		super();
		start = new Vector3d();
		end = new Vector3d(0,1,0);
		direction = new Vector3d(0,1,0);
	}
	
	public Line(Line line) {
		this();
		this.setStart(line.start.clone());
		this.setEnd(line.end.clone());
	}
	
	public Line setStart(Vector3d start) {
		this.start = start;
		updateDirection();
		return this;
	}
	
	public Line setEnd(Vector3d end) {
		this.end = end;
		updateDirection();
		return this;
	}
	
	public Vector3d getStart() {
		return start;
	}
	
	public Vector3d getEnd() {
		return end;
	}
	
	public Vector3d getDirection() {
		return direction;
	}
	
	public void transform(Matrix3d mat) {
		start = mat.transform(start);
		end = mat.transform(end);
		updateDirection();
	}
	
	/**
	 * Returns a point on the line with the given percent.
	 * 
	 */
	public Vector3d getPoint(double p) {
		return GenericMath.lerp(start, end, p);
	}
	
	public ArrayList<Vector3d> getPointN(int n, DoubleFunction function) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i<n; i++) {
			points.add(getPoint(function.apply(i)));
		}
		return points;
	}
	
	private void updateDirection() {
		direction = end.sub(start).normalize();
	}
	
}
