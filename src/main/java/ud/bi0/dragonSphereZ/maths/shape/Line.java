package ud.bi0.dragonSphereZ.maths.shape;

import java.util.ArrayList;
import java.util.List;

import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Line extends Shape {
	
	/**
	 * A line is defined by a point in space (origin) and a direction (direction).
	 * 
	 */
	protected Vector3 origin;
	protected Vector3 direction;
	
	/**
	 * Creates a line from two points with
	 * the origin at (pos1) and a direction
	 * from (pos1) to (pos2).
	 * 
	 */
	public Line(Vector3 pos1, Vector3 pos2) {
		origin = pos1.clone();
		direction = pos2.clone().subtract(pos1);
	}
	
	@Override
	public ShapeType getShapeType() {
		return ShapeType.LINE;
	}
	
	public Vector3 getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
	
	public Vector3 getDirection() {
		return direction;
	}
	
	public void setDirection(Vector3 direction) {
		this.direction = direction;
	}
	
	/**
	 * Get a point on the line. 
	 * Returns origin + t * direction.
	 * 
	 */
	public Vector3 getPoint(double t) {
		return origin.clone().add(t,direction);
	}
	
	/**
	 * Get a set of points on the line from
	 * origin to origin + direction with 
	 * density 1.
	 * 
	 */
	public List<Vector3> render() {
		return render(0,1,1);
	}
	
	/**
	 * Get a set of points on the line from
	 * origin to origin + direction with
	 * density (density).
	 * 
	 */
	public List<Vector3> render(double density) {
		return render(0,1,density);
	}
	
	/**
	 * Get a set of points on the line from
	 * origin to origin + t * direction with
	 * density (density).
	 * 
	 */
	public List<Vector3> render(double t, double density) {
		return render(0,t,density);
	}
	
	/**
	 * Get a set of points on the line from
	 * origin + start * direction to
	 * origin + end * direction with density
	 * (density).
	 * 
	 */
	public List<Vector3> render(double start, double end, double density) {
		double distance = end - start;
		int pointAmount = (int) (Math.abs(distance) * direction.length() * density);
		double step = distance / pointAmount;
		double point = start;
		List<Vector3> points = new ArrayList<Vector3>(pointAmount+1);
		for (int i = 0; i < pointAmount+1; i++) {
			points.add(getPoint(point));
			point += step;
		}
		return points;
	}
	
	/**
	 * Outlines a set of points. It connects
	 * lines from one point to the next one
	 * and gets then a set of points on that line.
	 * 
	 * If closed is true the last point will be
	 * connected to the first one.
	 * 
	 */
	public List<Vector3> renderOutline(List<Vector3> pointList, double density, boolean closed) {
		Line line;
		int size = pointList.size();
		if (!closed) size--;
		List<Vector3> points = new ArrayList<Vector3>();
		for (int i = 0; i < size - 1; i++) {
			line = new Line(pointList.get(i), pointList.get(i+1));
			points.addAll(line.render(0, 1, density));
		}
		if (closed) {
			line = new Line(pointList.get(size-1), pointList.get(0));
			points.addAll(line.render(0, 1, density));
		}
		return points;
	}
	
	/**
	 * Connects all points with lines.
	 * 
	 */
	public List<Vector3> renderConnect(List<Vector3> pointList, double density) {
		Line line;
		int pointAmount = pointList.size();
		List<Vector3> points = new ArrayList<Vector3>();
		for (int i = 0; i < pointAmount; i++) {
			for (int j = i+1; j < pointAmount; j++) {
				line = new Line(pointList.get(i), pointList.get(j));
				points.addAll(line.render(0, 1, density));
			}
		}
		return points;
	}
}
