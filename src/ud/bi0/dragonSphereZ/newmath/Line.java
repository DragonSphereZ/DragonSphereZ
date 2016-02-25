package ud.bi0.dragonSphereZ.newmath;

import java.util.Iterator;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

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
	
	public void setStart(Vector3d start) {
		this.start = start;
		updateDirection();
	}
	
	public void setEnd(Vector3d end) {
		this.end = end;
		updateDirection();
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

	/**
	 * Returns an iterator over the line with the given amount of points.
	 * 
	 */
	public LineIterator iterator(int points) {
		return new LineIterator(this, points);
	}
	
	private void updateDirection() {
		direction = end.sub(start).normalize();
	}
	
	public class LineIterator implements Iterator<Vector3d> {
		
		private Vector3d start;
		private Vector3d position;
		private Vector3d toNext;
		private int current;
		private int points;
		
		public LineIterator(Line line, int points) {
			this.start = line.start.add(line.getOrigin());
			this.position = start.clone();
			this.current = 0;
			this.points = points;
			this.toNext = line.start.sub(line.end).div(points);
		}
		
		public boolean hasNext() {
			return current < points;
		}
		
		public boolean hasPrevious() {
			return current > 0;
		}
		
		/**
		 * Does nothing!
		 * 
		 */
		public void remove() {};
		
		public void reset() {
			this.position = start.clone();
			this.current = 0;
		}
		
		public Vector3d next() {
			Vector3d point = position.clone();
			if (hasNext()) {
				position = position.add(toNext);
				current++;
			}
			return point;
		}
		
		public Vector3d previous() {
			Vector3d point = position.clone();
			if (hasPrevious()) {
				position = position.sub(toNext);
				current--;
			}
			return point;
		}
	}	
	
	
}
