package ud.bi0.dragonSphereZ.particles;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.utils.ParticleEffect;

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
	
	
	
	//TODO Will add in a delay to keep particle up for x ticks or seconds also make the loop shut itself down
	public static void drawSimpleDot(
			final int particleCount, 
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
 			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final double visibleRange, 
			final long delayTicks, 
			final long delayBySecond) {
			float hue = 0;
			Location location = null;
			//Will replace this with location getter
			if (center instanceof Entity) {
				location = ((Entity) center).getLocation();
			}
			else if (center instanceof Location){
				location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
			}
			if (rainbowMode == true)
				hue += 0.01F;
				hue = (hue >= 1.0F ? 0.0F : hue);
			ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, particleCount);

	}
	
}
