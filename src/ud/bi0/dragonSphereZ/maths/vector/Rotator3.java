package ud.bi0.dragonSphereZ.maths.vector;

public class Rotator3 {
	
	protected Vector3 rotX;
	protected Vector3 rotY;
	protected Vector3 rotZ;
	
	public Rotator3(Vector3 axis, double angle){
		init(axis, angle);
	}
	
	private void init(Vector3 normalAxis, double angle) {
		
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		
		rotX = setRotatorX(normalAxis, sina, cosa);
		rotY = setRotatorY(normalAxis, sina, cosa);
		rotZ = setRotatorZ(normalAxis, sina, cosa);
		
	}
	
	private static Vector3 setRotatorX(Vector3 normalAxis, double sinAngle, double cosAngle) {
		return new Vector3((normalAxis.getX()*normalAxis.getX()*(1-cosAngle)+cosAngle), (normalAxis.getX()*normalAxis.getY()*(1-cosAngle)-normalAxis.getZ()*sinAngle), (normalAxis.getX()*normalAxis.getZ()*(1-cosAngle)+normalAxis.getY()*sinAngle));
	}
	
	private static Vector3 setRotatorY(Vector3 normalAxis, double sinAngle, double cosAngle) {
		return new Vector3((normalAxis.getY()*normalAxis.getX()*(1-cosAngle)+normalAxis.getZ()*sinAngle), (normalAxis.getY()*normalAxis.getY()*(1-cosAngle)+cosAngle), (normalAxis.getY()*normalAxis.getZ()*(1-cosAngle)-normalAxis.getX()*sinAngle));
	}
	
	private static Vector3 setRotatorZ(Vector3 normalAxis, double sinAngle, double cosAngle) {
		return new Vector3((normalAxis.getZ()*normalAxis.getX()*(1-cosAngle)-normalAxis.getY()*sinAngle), (normalAxis.getZ()*normalAxis.getY()*(1-cosAngle)+normalAxis.getX()*sinAngle), (normalAxis.getZ()*normalAxis.getZ()*(1-cosAngle)+cosAngle));
	}

	public Vector3 getRotatorX() {
		return rotX;
	}
	
	public Vector3 getRotatorY() {
		return rotY;
	}
	
	public Vector3 getRotatorZ() {
		return rotZ;
	}
}
