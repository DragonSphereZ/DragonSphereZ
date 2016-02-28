package ud.bi0.dragonSphereZ.math;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

public class Coordinate {
	
	public static class Cartesian3d {
		
		public static Vector3d getPoint(Base3d base, Vector3d position) {
			return getPoint(base, position.getX(), position.getY(), position.getZ());
		}
		
		public static Vector3d getPoint(Base3d base, double u, double v, double w) {
			return getPoint(base.getU(), base.getV(), base.getW(), u, v, w);
		}
		
		public static Vector3d getPoint(Vector3d vecU, Vector3d vecV, Vector3d vecW, double u, double v, double w) {
			return new Vector3d()
						.add(vecU.mul(u))
						.add(vecV.mul(v))
						.add(vecW.mul(w));
		}
	}
	
	public static class Cylindrical3d {
		
		public static Vector3d getPoint(Vector3d u, Vector3d v, Vector3d w, double radiusU, double radiusV, double angle, double height) {
			double sinAngle = TrigMath.sin(angle);
			double cosAngle = TrigMath.cos(angle);
			return new Vector3d()
						.add(u.mul(cosAngle*radiusU))
						.add(v.mul(sinAngle*radiusV))
						.add(w.mul(height));
		}
		
		public static Vector3d getPoint(Base3d base, double radiusU, double radiusV, double angle, double height) {
			return getPoint(base.getU(), base.getV(), base.getW(), radiusU, radiusV, angle, height);
		}
		
		public static Vector3d getPoint(Base3d base, Vector2d radius, double angle, double height) {
			return getPoint(base, radius.getX(), radius.getY(), angle, height);
		}
	}
	
	public static class Spherical3d {
		
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
}
