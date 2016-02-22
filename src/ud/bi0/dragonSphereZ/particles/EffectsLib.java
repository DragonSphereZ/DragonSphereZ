package ud.bi0.dragonSphereZ.particles;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ud.bi0.dragonSphereZ.DragonSphereCore;
import ud.bi0.dragonSphereZ.utils.EffectUtils;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;
import ud.bi0.dragonSphereZ.utils.VectorUtils;



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
	
    public static void drawComplexCircle(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final Object center, 
			final String idName, 
			final List<Player> player, 
			final boolean rainbowMode, 
			final boolean enableRotation, 
			final float radius, 
			final float speed, 
			final int particleDensity, 
			final float steps, 
			final double visibleRange, 
			final double xRotation, 
			final double yRotation, 
			final double zRotation, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayByTick) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			
			int circle = Bukkit.getServer().getScheduler().runTaskTimer(DragonSphereCore.dragonSphereCore, new Runnable() {
						public double angularVelocityX = PI / 200;
						public double angularVelocityY = PI / 170;
						public double angularVelocityZ = PI / 155;
						public float step = steps;
						//public float hue;
						float offsetX;
						Location location;// = player.getLocation().clone();
						@Override
						public void run() {
							//if (center instanceof Entity) {
							//	location = ((Entity) center).getLocation();
							//}
							//else if (center instanceof Location){
								//location = ((Location) center);
							//	location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
							//}
							//Location locations = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
							location = EffectUtils.getLocation2(center);
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
							if (rainbowMode) offsetX += 1;
								//offsetX = ParticleEffectUtils.simpleRainbowHelper(offsetX, particle);
								//if (particle == "note"){
								//	offsetX = (float) (offsetX + 1);
								//	if (offsetX >= 24)
								//		offsetX = 0;
								//}else if (particle == "redstone" || particle == "mobspell" || particle == "mobspellambient"){
								//	offsetX = (float) (offsetX + 0.01);
								//}
							//if (rainbowMode)
							//	offsetX = (float) (offsetX + 0.01);
								
							//if (rainbowMode == true){
							//	hue += 0.01F;
							//	hue = (hue >= 1.0F ? 0.0F : hue);
							//	ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, rainbowMode, offsetX, offsetY, offsetZ, speed, 1);
							//}else{

							ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, rainbowMode, offsetX, offsetY, offsetZ, speed, 1);
							//}
							step++;
						}
					}, 1, delayByTick).getTaskId();//1,1
			EffectsLib.arraylist.put(idName, circle);
		}
	}
    
    
  //Add flapping wings in the future
  	public static void drawWingsColor1(
  			final String particle, 
  			final Material dataMat, 
  			final byte dataID, 
  			final float speed, 
  			final float offsetX, 
  			final float offsetY, 
  			final float offsetZ, 
  			final Object center, 
  			final String idName, 
  			final boolean rainbowMode, 
  			final float wingAngle, 
  			final double visibleRange, 
  			final boolean[][] shape, 
  			final float height,
  			final double space,  
  			final long delayByTick,
  			final List<Player> player) {
  		if (!EffectsLib.arraylist1.containsKey(idName)) {
  			int wings = Bukkit.getServer().getScheduler().runTaskTimer(DragonSphereCore.dragonSphereCore, new Runnable() {
  						private float hue;
  						Location location;
  						@Override
  						public void run() {
  							//Location location = player.getLocation().clone();
  							if (center instanceof Entity) {
  								location = ((Entity) center).getLocation();
  							}
  							else if (center instanceof Location){
  								location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
  							}
  					        if (rainbowMode == true)
  					        	//need to change hue to offsetX now
  								hue += 0.01F;
  								hue = (hue >= 1.0F ? 0.0F : hue);
  					        double x;
  					        final double defX = x = location.getX() + space;
  					        double y = location.clone().getY() + 2.7 + height;
  					        double y2 = location.clone().getY() + 2.7 + height;
  					        for (int i = 0; i < shape.length; ++i) {
  					            for (int j = 0; j < shape[i].length; ++j) {
  					                if (shape[i][j]) {
  					                    final Location target = location.clone();
  					                    target.setX(x);
  					                    target.setY(y);
  					                    Vector vR = target.toVector().subtract(location.toVector());
  					                    final Vector v2 = VectorUtils.getBackVector(location);
  					                    double rightWing = Math.toRadians(location.getYaw() + 90.0f - wingAngle);
  					                    vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
  		                                v2.setY(0).multiply(-0.2);
  					                    location.add(vR);
  					                    location.add(v2);
  					                    //for (int k = 0; k < 2; ++k) {
  					                    ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, rainbowMode, offsetX, offsetY, offsetZ, speed, 1);
  					                    //}
  					                    location.subtract(v2);
  					                    location.subtract(vR);
  					                }
  					                x += space;
  					            }
  					            y -= space;
  					            x = defX;
  					        }
  					        for (int i2 = 0; i2 < shape.length; ++i2) {
  					            for (int j2 = 0; j2 < shape[i2].length; ++j2) {
  					                if (shape[i2][j2]) {
  					                    final Location target = location.clone();
  					                    target.setX(x);
  					                    target.setY(y2);
  					                    Vector vL = target.toVector().subtract(location.toVector());
  					                    final Vector v2 = VectorUtils.getBackVector(location);
  					                    double leftWing = Math.toRadians(location.getYaw() + 90.0f + wingAngle);
  					                    vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
  		                                v2.setY(0).multiply(-0.2);
  					                    location.add(vL);
  					                    location.add(v2);
  					                    //for (int k = 0; k < 2; ++k) {
  					                    ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, rainbowMode, offsetX, offsetY, offsetZ, speed, 1);
  					                    //}
  					                    location.subtract(v2);
  					                    location.subtract(vL);
  					                }
  					                x += space;
  					            }
  					            y2 -= space;
  					            x = defX;
  					        }
  						}
  					}, 1, delayByTick).getTaskId();//2,1
  				EffectsLib.arraylist1.put((idName), wings);
  			}
  		}
    
	
}
