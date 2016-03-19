package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Pyramid 
	extends BaseShape 
	implements Cloneable {

	public static final Vector2d DEFAULT_RADIUS = Vector2d.ONE;
	public static final double DEFAULT_HEIGHT = 1;
	public static final int DEFAULT_SIZE = 3;
	
	private int size = DEFAULT_SIZE;
	private Vector2d radius = DEFAULT_RADIUS;
	private double height = DEFAULT_HEIGHT;

	
	public Pyramid() {
	}
	
	public Pyramid(Pyramid pyramid) {
		this(pyramid.getOrigin(), pyramid.getBase(), pyramid.getSize(), pyramid.getRadius(), pyramid.getHeight());
	}
	
	public Pyramid(int size, Vector2d radius, double height) {
		setSize(size);
		setRadius(radius);
		setHeight(height);
	}
	
	public Pyramid(Vector3d origin, Base3d base, int size, Vector2d radius, double height) {
		super(origin, base);
		setSize(size);
		setRadius(radius);
		setHeight(height);
	}
	
	public int getSize() {
		return size;
	}
	
	public Vector2d getRadius() {
		return radius.clone();
	}
	
	public double getRadiusU() {
		return getRadius().getX();
	}
	
	public double getRadiusV() {
		return getRadius().getY();
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setSize(int size) {
		if (!validSize(size)) {
			throw new IllegalArgumentException("Size of a pyramid must be at least " + DEFAULT_SIZE);
		}
		this.size = size;
	}
	
	public void setRadius(Vector2d radius) {
		this.radius = radius.clone();
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public Vector3d getSpire() {
		Vector3d point = getBase().getW().mul(height);
		return point.add(getOrigin());
	}
	
	public Vector3d getVertice(int verticeIndex) {
		if (!validIndex(verticeIndex)) {
			throw new IllegalArgumentException("Pyramid has no vertice with index " + verticeIndex);
		}
		double angle = verticeIndex * TrigMath.TWO_PI / getSize();
		Vector3d point = Coordinate.Cylindrical3d.getPoint(getBase(), getRadius(), angle, 0D);
		return point.add(getOrigin());
	}
	
	public Line getEdge(int edgeIndex) {
		if (!validIndex(edgeIndex)) {
			throw new IllegalArgumentException("Pyramid has no edghe with index " + edgeIndex);
		}
		return new Line(getVertice(edgeIndex), getSpire());
	}
	
	public ArrayList<Line> getAllEdges() {
		int size = getSize();
		ArrayList<Line> edges = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			edges.add(getEdge(i));
		}
		return edges;
	}
	
	public Line getBaseEdge(int edgeIndex) {
		if (!validIndex(edgeIndex)) {
			throw new IllegalArgumentException("Pyramid has no base edge with index " + edgeIndex);
		}
		int vertice1 = edgeIndex;
		int vertice2 = edgeIndex + 1;
		if (vertice2 > getSize()) {
			vertice2 = 0;
		}
		return new Line(getVertice(vertice1), getVertice(vertice2));
	}
	
	public ArrayList<Line> getAllBaseEdges() {
		int size = getSize();
		ArrayList<Line> edges = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			edges.add(getBaseEdge(i));
		}
		return edges;
	}
	
	public Plane getFace(int faceIndex) {
		if (!validIndex(faceIndex)) {
			throw new IllegalArgumentException("Pyramid has no face with index " + faceIndex);
		}
		int vertice1 = faceIndex;
		int vertice2 = faceIndex + 1;
		if (vertice2 > getSize()) {
			vertice2 = 0;
		}
		return new Plane(getVertice(vertice1), getVertice(vertice2), getSpire());
	}
	
	public ArrayList<Plane> getAllFaces() {
		int size = getSize();
		ArrayList<Plane> faces = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			faces.add(getFace(i));
		}
		return faces;
	}
	
	public Polygon getBaseAsPolygon() {
		return new Polygon(getOrigin(), getBase(), getSize(), getRadius());
	}
	
	public Vector3d getPoint(int edgeIndex, double percent) {
		if (!validIndex(edgeIndex)) {
			throw new IllegalArgumentException("Pyramid has no edge with index " + edgeIndex);
		}
		return getEdge(edgeIndex).getPoint(percent);
	}
	
	public Vector3d getBasePoint(int edgeIndex, double percent) {
		if (!validIndex(edgeIndex)) {
			throw new IllegalArgumentException("Pyramid has no base edge with index " + edgeIndex);
		}
		return getBaseEdge(edgeIndex).getPoint(percent);
	}
	
	private boolean validIndex(int index) {
		if (index < 0 || index > getSize()) {
			return false;
		}
		return true;
	}
	
	private boolean validSize(int size) {
		if (size < DEFAULT_SIZE) {
			return false;
		}
		return true;
	}
	
	@Override
	public Pyramid clone() {
		return new Pyramid(this);
	}
}
