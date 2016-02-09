package ud.bi0.dragonSphereZ.maths.shape;

import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Point extends Shape {
	
	protected Vector3 origin;
	
	public Point(Vector3 origin) {
		this.origin = origin;
	}
	
	@Override
	public ShapeType getShapeType() {
		return ShapeType.POINT;
	}
	
	public Vector3 getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
	
	public Vector3 render() {
		return origin;
	}
}
