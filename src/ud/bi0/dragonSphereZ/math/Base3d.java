package ud.bi0.dragonSphereZ.math;

import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

public class Base3d implements Cloneable {
	
	public static final Base3d CARTESIAN = new Base3d();
	public static final Base3d MINECRAFT = new Base3d( 	1, 0, 0,
														0, 0, 1,
														0, 1, 0);
	private final Matrix3d base;
	
	/**
	 * Returns a new Base3d of the standard
	 * cartesian coordinate system.
	 * 
	 */
	public Base3d() {
		this(	1, 0, 0,
				0, 1, 0,
				0, 0, 1);
	}
	
	public Base3d(Vector3d u, Vector3d v, Vector3d w) {
		this(	u.getX(), v.getX(), w.getX(),
				u.getY(), v.getY(), w.getY(),
				u.getZ(), v.getZ(), w.getZ());
	}
	
	public Base3d(Base3d base) {
		this( 	base.getU().getX(), base.getV().getX(), base.getW().getX(),
				base.getU().getY(), base.getV().getY(), base.getW().getY(),
				base.getU().getZ(), base.getV().getZ(), base.getW().getZ());
	}
	
	private Base3d(Matrix3d matrix) {
		this(	matrix.get(0, 0), matrix.get(0, 1), matrix.get(0, 2),
				matrix.get(1, 0), matrix.get(1, 1), matrix.get(1, 2),
				matrix.get(2, 0), matrix.get(2, 1), matrix.get(2, 2));
	}
	
	private Base3d(	double b00, double b01, double b02,
					double b10, double b11, double b12,
					double b20, double b21, double b22) {
		this.base = new Matrix3d(b00, b01, b02, b10, b11, b12, b20, b21, b22);
	}
	
	public Vector3d getU() {
		return base.getColumn(0);
	}
	
	public Vector3d getV() {
		return base.getColumn(1);
	}
	
	public Vector3d getW() {
		return base.getColumn(2);
	}
	
	public Base3d rotate(Quaterniond rotation) {
		return new Base3d(rotation.rotate(getU()), rotation.rotate(getV()), rotation.rotate(getW()));
	}
	
	public Base3d adjust(Vector3d from, Vector3d to) {
		if (!from.equals(to)) return rotate(Quaterniond.fromRotationTo(from, to));
		return new Base3d(base);
	}
	
	public Base3d transform(Matrix3d matrix) {
		return new Base3d(base.mul(matrix));
	}
	
	@Override
	public Base3d clone() {
		return new Base3d(this);
	}
	
}
