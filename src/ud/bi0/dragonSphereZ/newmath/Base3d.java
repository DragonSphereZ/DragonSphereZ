package ud.bi0.dragonSphereZ.newmath;

import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

public class Base3d {
	
	private Vector3d u;
	private Vector3d v;
	private Vector3d w;
	
	/**
	 * Returns a new Base3d with the standard 
	 * orthonormal base x, y, z.
	 * 
	 */
	public Base3d() {
		u = new Vector3d(1,0,0);
		v = new Vector3d(0,1,0);
		w = new Vector3d(0,0,1);
	}
	
	public Vector3d getU() {
		return u;
	}
	
	public Vector3d getV() {
		return v;
	}
	
	public Vector3d getW() {
		return w;
	}
	
	/**
	 * Transforms the current base. Note that the
	 * base will be re-orthonormalized after the
	 * transformation.
	 * 
	 */
	public void transform(Matrix3d mat) {
		u = mat.transform(u);
		v = mat.transform(v);
		this.orthoNormalize();
	}
	
	private void orthoNormalize() {
		w = u.cross(v);
		this.normalize();
	}
	
	private void normalize() {
		u = u.normalize();
		v = v.normalize();
		w = w.normalize();
	}
}
