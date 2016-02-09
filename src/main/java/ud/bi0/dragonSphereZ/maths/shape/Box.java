package ud.bi0.dragonSphereZ.maths.shape;

import ud.bi0.dragonSphereZ.maths.base.Base3;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class Box extends Shape {
	
	protected Vector3 origin;
	protected Base3 base;
	
	public Box(Vector3 origin, Vector3 u, Vector3 v, Vector3 w) {
		this.origin = origin;
		this.base = new Base3(u,v,w);
	}
	
	public Box(Vector3 origin, double x, double y, double z) {
		this.origin = origin;
		this.base = new Base3(x,y,z);
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
	
	public Vector3 getPoint(double u, double v, double w) {
		return origin.getCoordinate(base.getU(), base.getV(), base.getW(), u, v, w);
	}

}
