package ud.bi0.dragonSphereZ.math.shape;

import com.flowpowered.math.vector.Vector3d;

public abstract class Shape
	implements Cloneable {
	
	public static Vector3d DEFAULT_ORIGIN = Vector3d.ZERO;
	
	private Vector3d origin = DEFAULT_ORIGIN;
	
	public Shape() {
	}
	
	public Shape(Shape shape) {
		this(shape.getOrigin());
	}
	
	public Shape(Vector3d origin) {
		this.origin = origin.clone();
	}
	
	public Vector3d getOrigin() {
		return origin.clone();
	}
	
	public void setOrigin(Vector3d origin) {
		this.origin = origin.clone();
	}
}
