package ud.bi0.dragonSphereZ.oldmath.vector;

public class Base3 {
	
	protected Vector3 u;
	protected Vector3 v;
	protected Vector3 w;
	
	public Base3() {
		u = new Vector3(1,0,0);
		v = new Vector3(0,1,0);
		w = new Vector3(0,0,1);
	}
	
	public Base3(double x, double y, double z) {
		u = new Vector3(x,0,0);
		v = new Vector3(0,y,0);
		w = new Vector3(0,0,z);
	}
	
	public Base3(Vector3 u, Vector3 v, Vector3 w) {
		this.u = u;
		this.v = v;
		this.w = w;
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
		if (adjust) {
			this.v.adjust(this.u, u);
			this.w.adjust(this.u, u);
		}
		this.u = u;
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
		if (adjust) {
			this.u.adjust(this.v, v);
			this.w.adjust(this.v, v);
		}
		this.v = v;
	}
	
	public Vector3 getW() {
		return w;
	}
	
	public void setW(double w) {
		this.w.normalize().multiply(w);
	}
	
	public void setW(Vector3 w) {
		this.w = w;
	}
	
	public void setW(Vector3 w, boolean adjust) {
		if (adjust) {
			this.u.adjust(this.w, w);
			this.v.adjust(this.w, w);
		}
		this.w = w;
	}
}
