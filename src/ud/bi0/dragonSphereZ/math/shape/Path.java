package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector3d;

public class Path
	extends Shape 
	implements Cloneable {
	
	private LinkedList<Vector3d> points = new LinkedList<>();
	
	public Path(Path path) {
		this(path.getOrigin(), path.getNodes());
	}
	
	public Path(Collection<Vector3d> points) {
		addAll(points);
	}
	
	public Path(Vector3d origin, Collection<Vector3d> points) {
		super(origin);
		addAll(points);
	}
	
	public int getSize() {
		return points.size();
	}
	
	public double getTotalLength() {
		int lines = this.getSize() - 1;
		double length = 0;
		for (int i = 0; i < lines; i++) {
			length += this.getLineLenght(i);
		}
		return length;
	}
	
	public double getLineLenght(int index) {
		Vector3d point1;
		Vector3d point2;
		try {
			point1 = points.get(index);
			point2 = points.get(index+1);
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
		int lines = this.getSize() - 1;
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
			point2 = points.get(index+1);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Path contains no line with index " + index);
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
				throw new IndexOutOfBoundsException("Path does not contain line with index " + i);
			}
			try {
				point2 = points.get(i + 1);
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("Path does not contain line with index " + i);
			}
			lines.add(new Line(point1, point2));
		}
		return lines;
	}
		
	@Override
	public Path clone() {
		return new Path(this);
	}
}
