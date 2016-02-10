package ud.bi0.dragonSphereZ.maths.vector;

public class Base2 {
	
	protected Vector3 u;
	protected Vector3 v;
	protected Vector3 normal;
	
	public Base2() {
		u = new Vector3(1,0,0);
		v = new Vector3(0,1,0);
		normal = new Vector3(0,0,1);
	}
	
	public Base2(double x, double y) {
		u = new Vector3(x,0,0);
		v = new Vector3(0,y,0);
		normal = new Vector3(0,0,1);
	}
	
	public Base2(Vector3 u, Vector3 v) {
		this.u = u;
		this.v = v;
		normal = u.getNormal(v);
	}
	
	public Vector3 getU() {
		return u;
	}
	
	public void setU(double u) {
		this.u.normalize().multiply(u);
	}
	
	public void setU(Vector3 u) {
		this.u = u;
	}
	
	public void setU(Vector3 u, boolean adjust) {
		if (adjust) this.v.adjust(this.u, u);
		this.u = u;
		this.normal = u.getNormal(v);
	}
	
	public Vector3 getV() {
		return v;
	}
	
	public void setV(double v) {
		this.v.normalize().multiply(v);
	}
	
	public void setV(Vector3 v) {
		this.v = v;
	}
	
	public void setV(Vector3 v, boolean adjust) {
		if (adjust) this.u.adjust(this.v, v);
		this.v = v;
		this.normal = u.getNormal(v);
	}
	
	public Vector3 getNormal() {
		return normal;
	}
	
	public void setNormal(Vector3 direction) {
		this.normal = direction.clone().normalize();
		this.u.adjust(normal);
		this.v.adjust(normal);
	}
}
