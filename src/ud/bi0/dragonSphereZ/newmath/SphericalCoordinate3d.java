package ud.bi0.dragonSphereZ.newmath;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

public class SphericalCoordinate3d {
	
	public static Vector3d getPoint(Vector3d u, Vector3d v, Vector3d w, double radiusU, double radiusV, double radiusW, double thetha, double phi) {
		double sinThetha = TrigMath.sin(thetha);
		double cosThetha = TrigMath.cos(thetha);
		double sinPhi = TrigMath.sin(phi);
		double cosPhi = TrigMath.cos(phi);
		return new Vector3d()
					.add(u.mul(radiusU * sinThetha * cosPhi))
					.add(v.mul(radiusV * sinThetha * sinPhi))
					.add(w.mul(radiusW * cosThetha));
	}
	
	public static Vector3d getPoint(Base3d base, double radiusU, double radiusV, double radiusW, double thetha, double phi) {
		return getPoint(base.getU(), base.getV(), base.getW(), radiusU, radiusV, radiusW, thetha, phi);
	}
	
	public static Vector3d getPoint(Base3d base, Vector3d radius, double thetha, double phi) {
		return getPoint(base, radius.getX(), radius.getY(), radius.getZ(), thetha, phi);
	}
}
