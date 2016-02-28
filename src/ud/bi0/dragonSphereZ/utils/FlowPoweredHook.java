package ud.bi0.dragonSphereZ.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.flowpowered.math.vector.Vector3d;;

public class FlowPoweredHook {
	
	
	
	/**
	 * Returns a new Vector3d from a Location.
	 * 
	 */
	public static Vector3d Vector3d(Location loc) {
		return new Vector3d(loc.getX(), loc.getY(), loc.getZ());
	}
	
	/**
	 * Returns a new Vector3d from a Vector.
	 * 
	 */
	public static Vector3d Vector3d(Vector vec) {
		return new Vector3d(vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * Returns a new Location from a Vectro3d and a world.
	 * 
	 */
	public static Location Location(Vector3d vec, World world) {
		return new Location(world, vec.getX(), vec.getY(), vec.getZ());
	}
	
	/**
	 * Copies the contents of a Vector3d to a Location.
	 * 
	 */
	public static void vectorToLocation(Location loc, Vector3d vec) {
		loc.setX(vec.getX());
		loc.setY(vec.getY());
		loc.setZ(vec.getZ());
	}
		
}
