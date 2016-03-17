package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector3d;

public class ClosedPath
	extends Shape 
	implements Cloneable {
	
	private LinkedList<Vector3d> points;
	
	public ClosedPath(ClosedPath ClosedPath) {
		this(ClosedPath.getOrigin(), ClosedPath.getNodes());
	}
	
	public ClosedPath(Collection<Vector3d> points) {
		this(DEFAULT_ORIGIN, points);
	}
	
	public ClosedPath(Vector3d origin, Collection<Vector3d> points) {
		super(origin);
		this.points = new LinkedList<Vector3d>();
		this.points.addAll(points);
	}
	
	public int getSize() {
		return points.size();
	}
	
	public double getTotalLength() {
		int lines = this.getSize();
		double length = 0;
		for (int i = 0; i < lines; i++) {
			length += this.getLineLenght(i);
		}
		return length;
	}
	
	public double getLineLenght(int index) {
		int size = this.getSize();
		Vector3d point1;
		Vector3d point2;
		try {
			point1 = points.get(index);
			if (index < size) {
				point2 = points.get(index+1);
			}
			else {
				point2 = points.get(0);
			}
			
		} catch (Exception e){
			return 0;
		}
		return point1.distance(point2);
	}
	
	public void add(Vector3d point) {
		points.add(point);
	}
	
	public void add(int index, Vector3d point) {
		points.add(index, point);
	}
	
	public void addAll(Collection<Vector3d> points) {
		points.addAll(points);
	}
	
	public void removeFirst() {
		points.removeFirst();
	}
	
	public void removeLast() {
		points.removeLast();
	}
	
	public void remove(int index) {
		points.remove(index);
	}
	
	public Vector3d getPoint(double percentage) {
		double percent = GenericMath.clamp(percentage, 0, 1);
		double finalLength = percent * this.getTotalLength();
		double pathLength = 0;
		int lines = this.getSize();
		int finalLine = 0;
		while (finalLine < lines || pathLength < finalLength) {
			pathLength += this.getLineLenght(finalLine);
			finalLine++;
		}
		finalLine--;
		double finalPercent = 1 - (pathLength - finalLength) / this.getLineLenght(finalLine);
		Line line = this.getLine(finalLine);
		return line.getPoint(finalPercent);
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction percentage) {
		ArrayList<Vector3d> points = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			points.add(this.getPoint(percentage.applyAsDouble(i)));
		}
		return points;
	}
	
	public LinkedList<Vector3d> getNodes() {
		LinkedList<Vector3d> points = new LinkedList<Vector3d>();
		points.addAll(this.points);
		return points;
	}
	
	public Line getLine(int index) {
		Vector3d point1;
		Vector3d point2;
		try {
			point1 = points.get(index);
			if (index < this.getSize()) {
				point2 = points.get(index+1);
			} else {
				point2 = points.get(0);
			}
			
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("ClosedPath contains no line with index " + index);
		}
		return new Line(point1, point2);
	}
	
	public ArrayList<Line> getLines(int fromIndex, int toIndex) {
		ArrayList<Line> lines = new ArrayList<Line>();
		Vector3d point1;
		Vector3d point2;
		for (int i = fromIndex; i < toIndex; i++) {
			try {
				point1 = points.get(i);
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("ClosedPath does not contain line with index " + i);
			}
			try {
				if (i < this.getSize()) {
					point2 = points.get(i + 1);
				} else {
					point2 = points.get(0);
				}
				
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("ClosedPath does not contain line with index " + i);
			}
			lines.add(new Line(point1, point2));
		}
		return lines;
	}
		
	@Override
	public ClosedPath clone() {
		return new ClosedPath(this);
	}
}
