package ud.bi0.dragonSphereZ.math.shape;

import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;

abstract class BaseShape
	extends Shape
	implements Cloneable {

	public static final Base3d DEFAULT_BASE = Base3d.MINECRAFT;
	
	private Base3d base = DEFAULT_BASE;
	
	public BaseShape() {
	}
	
	public BaseShape(BaseShape baseShape) {
		this(baseShape.getOrigin(), baseShape.getBase());
	}
	
	public BaseShape(Vector3d origin, Base3d base) {
		super(origin);
		setBase(base);
	}
	
	public Base3d getBase() {
		return base.clone();
	}
	
	public void setBase(Base3d base) {
		this.base = base.clone();
	}
	
	public void adjustBase(Vector3d from, Vector3d to) {
		Base3d newBase = getBase().adjust(from, to);
		setBase(newBase);
	}
	
	
	public void rotateBase(Quaterniond rotation) {
		Base3d newBase = getBase().rotate(rotation);
		setBase(newBase);
	}
	
	public void transformBase(Matrix3d matrix) {
		Base3d newBase = getBase().transform(matrix);
		setBase(newBase);
	}
	
}
