package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Polygon
	extends BaseShape
	implements Cloneable {

	public static Vector2d DEFAULT_RADIUS = Vector2d.ONE;
	public static int DEFAULT_SIZE = 3;
	
	Vector2d radius = DEFAULT_RADIUS;
	int size = DEFAULT_SIZE;
	
	public Polygon() {
	}
	
	public Polygon(Polygon polygon) {
		this(polygon.getOrigin(), polygon.getBase(), polygon.getSize(), polygon.getRadius());
	}
	
	public Polygon(int sides) {
		setSize(sides);
	}
	
	public Polygon(Vector3d origin, Base3d base, int size, Vector2d radius) {
		super(origin, base);
		setSize(size);
		setRadius(radius);
	}
	
	public Vector2d getRadius() {
		return radius;
	}
	
	public double getRadiusU() {
		return getRadius().getX();
	}
	
	public double getRadiusV() {
		return getRadius().getY();
	}
		
	public int getSize() {
		return size;
	}
	
	public void setRadius(double radius) {
		setRadius(radius, radius);
	}
	
	public void setRadius(double radiusU, double radiusV) {
		setRadius(new Vector2d(radiusU, radiusV));
	}
	
	public void setRadius(Vector2d radius) {
		this.radius = radius.clone();
	}
	
	public void setSize(int size) {
		if (size < DEFAULT_SIZE) {
			throw new IllegalArgumentException("Size of polygon must be at least " + DEFAULT_SIZE);
		}
		this.size = size;
	}
	

	public double getAngle(int verticeIndex) {
		if (!validIndex(verticeIndex)) {
			throw new IllegalArgumentException("Polygon has no vertice with index " + verticeIndex);
		}
		return verticeIndex * TrigMath.TWO_PI / getSize();
	}
	
	public double getSideLength() {
		Vector2d radius = getRadius();
		Base3d base = getBase();
		Vector3d point1 = Coordinate.Cylindrical3d.getPoint(base, radius, 0D, 0D);
		Vector3d point2 = Coordinate.Cylindrical3d.getPoint(base, radius, getAngle(1), 0D);
		return point1.distance(point2);
	}
	
	public double getCircumradius() {
		double sideLength = getSideLength();
		double circumradius = sideLength / ( 2 * TrigMath.sin(TrigMath.PI / getSize()));
		return circumradius;
	}
	
	public Vector3d getVertice(int verticeIndex) {
		if (!validIndex(verticeIndex)) {
			throw new IllegalArgumentException("Polygon has no vertice with index " + verticeIndex);
		}
		Vector3d point = Coordinate.Cylindrical3d.getPoint(getBase(), getRadius(), getAngle(verticeIndex), 0D);
		return point.add(getOrigin());
	}
	
	public ArrayList<Vector3d> getAllVertices() {
		ArrayList<Vector3d> vertices = new ArrayList<>(getSize());
		int size = getSize();
		for (int i = 0; i < size; i++) {
			vertices.add(getVertice(i));
		}
		return vertices;
	}
	
	public Line getLine(int startVerticeIndex, int endVerticeIndex) {
		if (!validIndex(startVerticeIndex)) {
			throw new IllegalArgumentException("Polygon has no vertice with index " + startVerticeIndex);
		}
		if (!validIndex(endVerticeIndex)) {
			throw new IllegalArgumentException("Polygon has no side with index " + endVerticeIndex);
		}
		Vector3d point1 = getVertice(startVerticeIndex);
		Vector3d point2 = getVertice(endVerticeIndex);
		return new Line(point1, point2);
	}
	
	public Line getSide(int sideIndex) {
		int startIndex = sideIndex;
		int endIndex = sideIndex + 1;
		if (sideIndex == getSize()) {
			endIndex = 0;
		}
		return getLine(startIndex, endIndex);
	}
	
	public ArrayList<Line> getAllSides() {
		ArrayList<Line> lines = new ArrayList<>(getSize());
		int size = getSize();
		for (int i = 0; i < size; i++) {
			lines.add(getSide(i));
		}
		return lines;
	}
	
	public Vector3d getPoint(double percent) {
		int lineIndex = (int) (percent * getSize());
		if (!validIndex(lineIndex)) {
			throw new IllegalArgumentException("Polygon has no side with index " + lineIndex);
		}
		Line line = getSide(lineIndex);
		Vector3d point = line.getPoint(percent);
		return point;
	}
	
	public ArrayList<Vector3d> getPointN(int n, IntToDoubleFunction percent) {
		ArrayList<Vector3d> points = new ArrayList<Vector3d>(n);
		for (int i = 0; i < n; i++) {
			points.add(getPoint(percent.applyAsDouble(i)));
		}
		return points;
	}
	
	private boolean validIndex(int i) {
		if (i < 0 || i > getSize()) {
			return false;
		}
		return true;
	}
	
	@Override
	public Polygon clone() {
		return new Polygon(this);
	}
}
