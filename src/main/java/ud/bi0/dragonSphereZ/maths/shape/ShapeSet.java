package ud.bi0.dragonSphereZ.maths.shape;

import java.util.HashMap;
import java.util.Map;

import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class ShapeSet {
	
	protected Vector3 origin;
	protected Vector3 u;
	protected Vector3 v;
	protected Vector3 w;
	protected Map<String, Shape> shapes;
	
	public ShapeSet() {
		origin = new Vector3(0,0,0);
		u = new Vector3(1,0,0);
		v = new Vector3(0,1,0);
		w = new Vector3(0,0,1);
		shapes = new HashMap<String, Shape>();
	}
	
	public Shape getShape(String key) {
		return shapes.get(key);
	}
	
	public Map<String, Shape> getAllShapes() {
		return shapes;
	}
	
	public ShapeSet reset() {
		return new ShapeSet();
	}
	
	public void addShape(String key, Shape shape) {
		shapes.put(key, shape);
	}
	
	public void removeShape(String key) {
		shapes.remove(key);
	}
	
	public Vector3 getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
	
	public Vector3 getU() {
		return u;
	}
	
	/**
	 * Sets the base vector u. If adjust
	 * is true it will change the other
	 * base vectors accordingly.
	 * 
	 */
	public void setU(Vector3 u, boolean adjust) {
		if (adjust) {
			this.v.adjust(this.u, u);
			this.w.adjust(this.w, w);
		}
		this.u = u;
	}
	
	public Vector3 getV() {
		return v;
	}
	
	/**
	 * Sets the base vector v. If adjust
	 * is true it will change the other
	 * base vectors accordingly.
	 * 
	 */
	public void setV(Vector3 v, boolean adjust) {
		if(adjust) {
			this.u.adjust(this.v, v);
			this.w.adjust(this.v, v);
		}
		this.v = v;
	}
	
	public Vector3 getW() {
		return w;
	}
	
	/**
	 * Sets the base vector w. If adjust
	 * is true it will change the other
	 * base vectors accordingly.
	 * 
	 */
	public void setW(Vector3 w, boolean adjust) {
		if (adjust) {
			this.u.adjust(this.w, w);
			this.v.adjust(this.w, w);
		}
		this.w = w;
	}

	
}




