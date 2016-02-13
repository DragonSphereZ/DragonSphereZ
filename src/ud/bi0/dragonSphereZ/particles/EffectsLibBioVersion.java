package ud.bi0.dragonSphereZ.particles;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ud.bi0.dragonSphereZ.dragonSphereCore;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;
import ud.bi0.dragonSphereZ.utils.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.VectorUtils;



/**
 * Not to be confused with EffectLib. 
 * Eventually this Lib will contain every effect/trail/whatever I can find.
 * Available to Skript
 * This is still a major work in progress.
 *  - Sashie <3
*/
public class EffectsLibBioVersion {
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
	
	
	
	
    
    
    
    
    public static void drawComplexCircle(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final boolean enableRotation, 
			final float radius, 
			final float speed, 
			final int particleDensity, 
			final float steps, 
			final double visibleRange, 
			final Vector3 rotation,
			final Vector3 offset, 
			final Vector3 dis,
			final long delayTicks, 
			final long delayBySecond) {
		if (!EffectsLibBioVersion.arraylist.containsKey(idName)) {
			int circle = Bukkit.getServer().getScheduler().runTaskTimer(dragonSphereCore.dragonSphereCore, new Runnable() {
						public Vector3 angularVelocity = new Vector3(PI/200, PI/170, PI/155);
						public float step = steps;
						public float hue;
						Location location;// = player.getLocation().clone();
						@Override
						public void run() {
							if (center instanceof Entity) {
								location = ((Entity) center).getLocation();
							}
							else if (center instanceof Location){
								//location = ((Location) center);
								location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
							}
							//Location locations = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
							location.add(0D, 1D, 0D);
							location.add(disX, disY, disZ);
							double inc = PI2 / particleDensity;
							double angle = step * inc;
							Vector v = new Vector();
							v.setX(Math.cos(angle) * radius);
							v.setZ(Math.sin(angle) * radius);
							
							//final double x = location.getX() + radius * Math.cos(angle);
				            //final double z = location.getZ() + radius * Math.sin(angle);
				            //location.add(new Location(locations.getWorld(), x, locations.getY(), z));
							
							VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
							if (enableRotation)
								VectorUtils.rotateVector(v, angularVelocityX * step, angularVelocityY * step, angularVelocityZ * step);
							if (rainbowMode == true){
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, offsetX, offsetY, offsetZ, speed, 1);
							}else{
								//ParticleEffect.valueOf(particle).display(new Vector(Math.cos(angle) * radius, 0, Math.sin(angle) * radius), 1.0f, this.location.clone(), 100.0);
								//ParticleEffect.valueOf(particle).display(v, speed, location, visibleRange);
								//ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);

								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, offsetX, offsetY, offsetZ, speed, 1);
							}
							step++;
						}
					}, delayTicks, delayBySecond).getTaskId();//1,1
			EffectsLibBioVersion.arraylist.put(idName, circle);
		}
	}
    
    
    
    
	
}
