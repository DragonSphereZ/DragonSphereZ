package ud.bi0.dragonSphereZ.oldmath.shape;

import java.util.HashMap;
import java.util.Map;

import ud.bi0.dragonSphereZ.oldmath.vector.Base3;
import ud.bi0.dragonSphereZ.oldmath.vector.Vector3;

public class ShapeSet {
	
	protected Vector3 origin;
	protected Base3 base;
	protected Map<String, Shape> shapes;
	
	public ShapeSet() {
		origin = new Vector3(0,0,0);
		base = new Base3();
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
	
	public Base3 getBase() {
		return base;
	}
	
	public void setBase(Base3 base) {
		this.base = base;
	}
	
	
	
}




