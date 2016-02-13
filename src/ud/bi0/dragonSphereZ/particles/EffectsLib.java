package ud.bi0.dragonSphereZ.particles;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;


/**
 * Not to be confused with EffectLib. 
 * Eventually this Lib will contain every effect/trail/whatever I can find.
 * Available to Skript
 * This is still a major work in progress.
 *  - Sashie <3
*/
public class EffectsLib {
	final public static HashMap<String, Integer> arraylist = new HashMap<String, Integer>();
	final public static HashMap<String, Integer> arraylist1 = new HashMap<String, Integer>();
	final public static HashMap<String, Integer> arraylist2 = new HashMap<String, Integer>();
	final public static HashMap<String, Integer> arraylist3 = new HashMap<String, Integer>();
	final public static HashMap<String, Integer> arraylist4 = new HashMap<String, Integer>();
	final public static float PI = 3.141592653589793f;
	final public static float PI2 = 6.283185307179586f;
	public enum Plane {
		X, Y, Z, XY, XZ, XYZ, YZ;
	}

	public static void stopEffect(String idName) {
		if (arraylist.containsKey(idName)) {
			Bukkit.getScheduler().cancelTask(arraylist.get(idName));
			arraylist.remove(idName);
		}
	}
	
	public static boolean EffectActive(String idName) {
		if (!arraylist.containsKey(idName)) {
			return true;
		} else {
			return false;
		}
	}
	

    protected Location getLocation(Object location) {
    	Location finalLocation = null;
    	if (location instanceof Entity) {
    		finalLocation = ((Entity) location).getLocation();
		}else if (location instanceof Location){
			finalLocation = new Location(((Location) location).getWorld(), ((Location) location).getX(), ((Location) location).getY(), ((Location) location).getZ());
		}
        return finalLocation;
    }
	
	
	
	
	
}
