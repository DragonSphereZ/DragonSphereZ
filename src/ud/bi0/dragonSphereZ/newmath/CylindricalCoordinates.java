package ud.bi0.dragonSphereZ.newmath;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

public class CylindricalCoordinates {
	
	public static Vector3d getPoint(Vector3d u, Vector3d v, Vector3d w, double radiusU, double radiusV, double angle, double height) {
		double sinAngle = TrigMath.sin(angle);
		double cosAngle = TrigMath.cos(angle);
		return new Vector3d().add(u.mul(cosAngle*radiusU)).add(v.mul(sinAngle*radiusV)).add(w.mul(height));
	}
	
	public static Vector3d getPoint(Base3d base, double radiusU, double radiusV, double angle, double height) {
		return getPoint(base.getU(), base.getV(), base.getW(), radiusU, radiusV, angle, height);
	}
	
}
