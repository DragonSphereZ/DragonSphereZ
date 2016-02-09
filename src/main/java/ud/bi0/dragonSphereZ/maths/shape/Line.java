package ud.bi0.dragonSphereZ.maths.shape;

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
		direction = pos2.clone().subtract(pos1).normalize();
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
		this.direction = direction.clone().normalize();
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
	public Vector3[] render() {
		return render(0,1,1);
	}
	
	/**
	 * Get a set of points on the line from
	 * origin to origin + direction with
	 * density (density).
	 * 
	 */
	public Vector3[] render(double density) {
		return render(0,1,density);
	}
	
	/**
	 * Get a set of points on the line from
	 * origin to origin + t * direction with
	 * density (density).
	 * 
	 */
	public Vector3[] render(double t, double density) {
		return render(0,t,density);
	}
	
	/**
	 * Get a set of points on the line from
	 * origin + start * direction to
	 * origin + end * direction with density
	 * (density).
	 * 
	 */
	public Vector3[] render(double start, double end, double density) {
		double distance = end - start;
		int pointAmount = (int) (Math.abs(distance) * density);
		double step = distance / pointAmount;
		double point = start;
		Vector3 points[] = new Vector3[pointAmount];
		for (int i = 0; i < pointAmount; i++) {
			points[i] = getPoint(point);
			point += step;
		}
		return points;
	}
	
	/**
	 * Outlines a set of points. It connects
	 * lines from one point to the next one
	 * and gets then a set of points on that line.
	 * For each line is a new array created:
	 * points[line][]
	 * 
	 * If closed is true the last point will be
	 * connected to the first one.
	 * 
	 */
	public Vector3[][] renderOutline(Vector3[] pointArray, double density, boolean closed) {
		Line line;
		int size = pointArray.length;
		if (!closed) size--;
		Vector3[][] points = new Vector3[size][];
		for (int i = 0; i < size - 1; i++) {
			line = new Line(pointArray[i], pointArray[i+1]);
			Vector3[] pointsLine = line.render(0, 1, density);
			System.arraycopy(pointsLine, 0, points[i], 0, pointsLine.length);
		}
		if (closed) {
			line = new Line(pointArray[size-1], pointArray[0]);
			Vector3[] pointsLine = line.render(0, 1, density);
			System.arraycopy(pointsLine, 0, points[size-1], 0, pointsLine.length);
		}
		return points;
	}
	
	/**
	 * Connects all points with one line and returns 
	 * a set of points from these lines.
	 * For each line is a new array created:
	 * points[line][].
	 * 
	 * Note: Every line is only drawn once! This means
	 * that the line from point A to B exists but B to A does not.
	 * 
	 */
	public Vector3[][] renderConnect(Vector3[] pointArray, double density) {
		Line line;
		int sizeLines = factorial(pointArray.length - 1);
		Vector3[][] points = new Vector3[sizeLines][];
		int k = 0;
		for (int i = 0; i < sizeLines; i++) {
			for (int j = 0; j < i; j++) {
				if (i!=j) {
					line = new Line(pointArray[i],pointArray[j]);
					Vector3[] lineLine = line.render(0, 1, density);
					System.arraycopy(lineLine, 0, points[k], 0, lineLine.length);
					k++;
				}
			}
		}
		return points;
	}
	
	/**
	 * Connects all points with lines and returns 
	 * a set of points from these lines.
	 * For each point and line is a new array created:
	 * points[point][line][].
	 * 
	 * Note that every line exists two times! Once from
	 * A to B and once from B to A.
	 * 
	 */
	public Vector3[][][] renderConnectAll(Vector3[] pointArray, double density) {
		Line line;
		int sizePoints = pointArray.length;
		int sizeLines = sizePoints - 1;
		Vector3[][][] points = new Vector3[sizePoints][sizeLines][];
		for (int i = 0; i < sizePoints; i++) {
			int k = 0;
			for (int j = 0; j < sizeLines + 1; j++) {
				if (i!=j) {
					line = new Line(pointArray[i],pointArray[j]);
					Vector3[] pointsLine = line.render(0, 1, density);
					System.arraycopy(pointsLine, 0, points[i][k], 0, pointsLine.length);
					k++;
				}
			}
		}
		return points;
	}	
	
	/**
	 * Calculates n! (factorial of n).
	 * 
	 */
	public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
