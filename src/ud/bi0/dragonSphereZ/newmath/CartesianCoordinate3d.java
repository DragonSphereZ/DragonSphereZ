package ud.bi0.dragonSphereZ.newmath;

import com.flowpowered.math.vector.Vector3d;

public class CartesianCoordinate3d {
	
	public Vector3d getPoint(Vector3d vecU, Vector3d vecV, Vector3d vecW, double u, double v, double w) {
		return new Vector3d()
					.add(vecU.mul(u))
					.add(vecV.mul(v))
					.add(vecW.mul(w));
	}
	
	public Vector3d getPoint(Base3d base, double u, double v, double w) {
		return getPoint(base.getU(), base.getV(), base.getW(), u, v, w);
	}
	
	public Vector3d getPoint(Base3d base, Vector3d position) {
		return getPoint(base, position.getX(), position.getY(), position.getZ());
	}
}
