package ud.bi0.dragonSphereZ.math.shape;

import com.flowpowered.math.vector.Vector3d;

public class Shape {
	
	private Vector3d origin;
	
	public Shape() {
		origin = new Vector3d();
	}
	
	public Shape(Vector3d origin) {
		this.origin = new Vector3d(origin);
	}
	
	public Vector3d getOrigin() {
		return origin;
	}
	
	public Shape setOrigin(Vector3d origin) {
		this.origin = origin;
		return this;
	}
	
}
