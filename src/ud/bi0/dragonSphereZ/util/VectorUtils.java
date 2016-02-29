package ud.bi0.dragonSphereZ.util;


import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;


public final class VectorUtils {
    private VectorUtils() {
    }

    public static final Vector3d rotateAroundAxisX(Vector3d v, double angle) {
        double y, z, cos, sin;
        angle = GenericMath.wrapAngleRad(angle);
        cos = TrigMath.cos(angle);
        sin = TrigMath.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return new Vector3d(v.getX(), y, z);
    }

    public static final Vector3d rotateAroundAxisY(Vector3d v, double angle) {
        double x, z, cos, sin;
        angle = GenericMath.wrapAngleRad(angle);
        cos = TrigMath.cos(angle);
        sin = TrigMath.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return new Vector3d(x, v.getY(), z);
    }

    public static final Vector3d rotateAroundAxisZ(Vector3d v, double angle) {
        double x, y, cos, sin;
        angle = GenericMath.wrapAngleRad(angle);
        cos = TrigMath.cos(angle);
        sin = TrigMath.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return new Vector3d(x, y, v.getZ());
    }

    public static final Vector3d rotateVector(Vector3d v, double angleX, double angleY, double angleZ) {
        // double x = v.getX(), y = v.getY(), z = v.getZ();
        // double cosX = Math.cos(angleX), sinX = Math.sin(angleX), cosY =
        // Math.cos(angleY), sinY = Math.sin(angleY), cosZ = Math.cos(angleZ),
        // sinZ = Math.sin(angleZ);
        // double nx, ny, nz;
        // nx = (x * cosY + z * sinY) * (x * cosZ - y * sinZ);
        // ny = (y * cosX - z * sinX) * (x * sinZ + y * cosZ);
        // nz = (y * sinX + z * cosX) * (-x * sinY + z * cosY);
        // return v.setX(nx).setY(ny).setZ(nz);
        // Having some strange behavior up there.. Have to look in it later. TODO (<- this was a note from the lib to begin with -sashie)
        rotateAroundAxisX(v, angleX);
        rotateAroundAxisY(v, angleY);
        rotateAroundAxisZ(v, angleZ);
        return v;
    }
    
    /**
     * Rotate a vector about a location using that location's direction
     *
     * @param v
     * @param location
     * @return
     */
    public static final Vector3d rotateVector(Vector3d v, Location location) {
        return rotateVector(v, location.getYaw(), location.getPitch());
    }

    /**
     * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
     *
     * Thanks to SexyToad!
     *
     * @param v
     * @param yawDegrees
     * @param pitchDegrees
     * @return
     */
    public static final Vector3d rotateVector(Vector3d v, float yawDegrees, float pitchDegrees) {

    	double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = TrigMath.cos(yaw);
        double cosPitch = TrigMath.cos(pitch);
        double sinYaw = TrigMath.sin(yaw);
        double sinPitch = TrigMath.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = v.getX();
        initialY = v.getY();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = v.getZ();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vector3d(x, y, z);
    }

    public static final double angleToXAxis(Vector3d vector) {
        return TrigMath.atan2(vector.getX(), vector.getY());
    }
    
    public static Vector3d getBackVector(final Location location) {
        final float newZ = (float)(location.getZ() + 1.0 * TrigMath.sin(Math.toRadians(location.getYaw() + 90.0f)));
        final float newX = (float)(location.getX() + 1.0 * TrigMath.cos(Math.toRadians(location.getYaw() + 90.0f)));
        return new Vector3d(newX - location.getX(), 0.0, newZ - location.getZ());
    }
   
    
    public static double offset(final Entity a, final Entity b) {
        return offset(FlowPoweredHook.Vector3d(a.getLocation()), FlowPoweredHook.Vector3d(b.getLocation().toVector()));
    }
    
    public static double offset(final Location a, final Location b) {
        return offset(FlowPoweredHook.Vector3d(a), FlowPoweredHook.Vector3d(b));
    }
    
    public static double offset(final Vector3d a, final Vector3d b) {
        return a.distance(b);
    }
    
}
