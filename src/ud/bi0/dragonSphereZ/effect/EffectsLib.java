package ud.bi0.dragonSphereZ.effect;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ch.njol.skript.Skript;

import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.MathUtils;

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
	public static void stopWings(String idName) {
		if (arraylist1.containsKey(idName)) {
			//Bukkit.getServer().broadcastMessage("[skDragon] " + idName);
			Bukkit.getScheduler().cancelTask(arraylist1.get((idName)));
			arraylist1.remove((idName));
		}
		if (arraylist2.containsKey(idName)) {
			//Bukkit.getServer().broadcastMessage("[skDragon] " + idName);
			Bukkit.getScheduler().cancelTask(arraylist2.get((idName)));
			arraylist2.remove((idName));
		}
		if (arraylist3.containsKey(idName)) {
			//Bukkit.getServer().broadcastMessage("[skDragon] " + idName);
			Bukkit.getScheduler().cancelTask(arraylist3.get((idName)));;
			arraylist3.remove((idName));
		}
		if (arraylist4.containsKey(idName)) {
			//Bukkit.getServer().broadcastMessage("[skDragon] " + idName);
			Bukkit.getScheduler().cancelTask(arraylist4.get((idName)));;
			arraylist4.remove((idName));
		}
	}
	
	public static boolean EffectActive(String idName) {
		if (!arraylist.containsKey(idName)) {
			return true;
		} else {
			return false;
		}
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
//TODO	Add stationary effect as well so that particles aren't spinning
	public static void drawSimpleHalo(
			final String particle, 
			final Object center, 
			final Material dataMat, 
			final byte dataID, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final float speed,
			final double visibleRange,
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final long delayTicks, 
			final long delayBySecond
			) {

		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int normalHalo = Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						float step = 0;
						public float hue;
						final double angle = PI / 6;
						Location location;// = player.getLocation().clone();
						public void run() {
							if (center instanceof Entity) {
								location = ((Entity) center).getLocation();
							}
							else if (center instanceof Location){
								location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
							}
							double x = Math.cos(angle * step) * 0.3f;
							double y = 1.15f;
							double z = Math.sin(angle * step) * 0.3f;
							Vector v = new Vector(x, y, z);
							location.add(v);
							if (rainbowMode == true){
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
							}else{
								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
							}
//							location.subtract(v);
							step++;
						}
					}, delayTicks, delayBySecond).getTaskId();//1,1
			EffectsLib.arraylist.put(idName, normalHalo);
		//}else {
			//EffectsLib.stopEffect(idName);
		}
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
			final double xRotation, 
			final double yRotation, 
			final double zRotation, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayTicks, 
			final long delayBySecond) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int circle = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						public double angularVelocityX = PI / 200;
						public double angularVelocityY = PI / 170;
						public double angularVelocityZ = PI / 155;
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
								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
							}else{
								//ParticleEffect.valueOf(particle).display(new Vector(Math.cos(angle) * radius, 0, Math.sin(angle) * radius), 1.0f, this.location.clone(), 100.0);
								//ParticleEffect.valueOf(particle).display(v, speed, location, visibleRange);
								//ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);

								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
							}
							step++;
						}
					}, delayTicks, delayBySecond).getTaskId();//1,1
			EffectsLib.arraylist.put(idName, circle);
		}
	}

	public static void drawAtom(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final String particle2, 
			final Material dataMat2, 
			final byte dataID2, 
			final float speed2, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final float innerRadius, 
			final int innerParticles, 
			final int orbitParticles, 
			final int orbitalCount, 
			final float steps, 
			final double visibleRange, 
			final double rotations, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final float offsetX2, 
			final float offsetY2, 
			final float offsetZ2, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayTicks, 
			final long delayBySecond
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {

			int atom = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						public double angularVelocity = PI / 40d;
						public float step = steps;//0;
						public float hue;
						Location location;
						@Override
						public void run() {
							//Location location = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 3, player.getLocation().getZ());
							//Location location = new Location((Entity) center).getWorld(), player.getLocation().getX(), player.getLocation().getY() + 3, player.getLocation().getZ());

							if (center instanceof Entity) {
								location = new Location(((Entity) center).getWorld(), ((Entity) center).getLocation().getX(), ((Entity) center).getLocation().getY() + 3, ((Entity) center).getLocation().getZ());

							}
							else if (center instanceof Location){
								location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
							}
							
							location.add(disX, disY, disZ);
							if (rainbowMode == true)
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);							
							for (int i = 0; i < innerParticles; i++) {
								Vector v = RandomUtils.getRandomVector().multiply(0.5 * innerRadius);
								ParticleEffect.valueOf(particle2).display(dataMat2, dataID2, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
								location.subtract(v);
							}
							for (int i = 0; i < orbitParticles; i++) {
								double angle = step * angularVelocity;
								for (int j = 0; j < orbitalCount; j++) {
									double xRotation = (PI / orbitalCount) * j;
//									Vector v = new Vector(Math.sin(angle), Math.cos(angle), 0).multiply(0.8);
									Vector v = new Vector();
									v.setX(Math.sin(angle) * ( 0.6 + innerRadius ));
									v.setY(Math.cos(angle) * ( 0.6 + innerRadius ));
									VectorUtils.rotateAroundAxisX(v, xRotation);
									VectorUtils.rotateAroundAxisY(v, rotations);
									ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
									location.subtract(v);
								}
								step++;
							}
						}
					}, delayTicks, delayBySecond).getTaskId();//0,0
			EffectsLib.arraylist.put(idName, atom);
		}
	}

	@SuppressWarnings("unused")
	public static void drawArc(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 			
			final Object center, 
			final Object getTarget, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final int particleDensity, 
			final float height, 
			final float pitchMuliplier,  
			final double visibleRange, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final double disX2, 
			final double disY2, 
			final double disZ2, 
			final long delayTicks, 
			final long delayBySecond
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int arc = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
			    //public float height = 2;
			    //public int particleDensity = 100;
				int step = 0;
				public float hue;
				Location location;
				Location target;
				@Override
	            public void run() {
					if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
					}
					else if (center instanceof Location){
						//location = ((Location) center);
						location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
					}
					if (getTarget instanceof Entity) {
						target = ((Entity) getTarget).getLocation();
					}
					else if (getTarget instanceof Location){
						//location = ((Location) center);
						target = new Location(((Location) getTarget).getWorld(), ((Location) getTarget).getX(), ((Location) getTarget).getY(), ((Location) getTarget).getZ());
					}
			        if (location == null || target == null) {
			            return;
			        }
			        location.add(disX, disY, disZ);
			        target.add(disX2, disY2, disZ2);
					if (rainbowMode == true)
						hue += 0.01F;
						hue = (hue >= 1.0F ? 0.0F : hue);
			        Vector link = target.toVector().subtract(location.toVector());
			        float length = (float) link.length();
			        float pitch = (float) (pitchMuliplier * height / Math.pow(length, 2));//pitchMultiplier default = 4
			        for (int i = 0; i < particleDensity; i++) {
			            Vector v = link.clone().normalize().multiply((float) length * i / particleDensity);
			            float x = ((float) i / particleDensity) * length - length / 2;
			            float y = (float) (-pitch * Math.pow(x, 2) + height);
			            location.add(v).add(0, y, 0);
			            ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
			            //ParticleEffect.flame.display(0, 0, 0, 0, 1, location, 50);
			            location.subtract(0, y, 0).subtract(v);
			            step++;
			        }
				}
			}, delayTicks, delayBySecond).getTaskId();
		    EffectsLib.arraylist.put(idName, arc);
		}
	}

	public static void drawDot(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
 			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final boolean isFacing, 
			final float faceAngle, 

			final float radius, 
			final double xRotation, 
			final double yRotation, 
			final double zRotation, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final double visibleRange, 
			final long delayTicks, 
			final long delayBySecond) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int simpleDot = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						//TODO Future wing designer, add mirrorMode
						public float hue;
						Location location;// = player.getLocation().clone();
						@Override
						public void run() {
							if (center instanceof Entity) {
								location = ((Entity) center).getLocation();
							}
							else if (center instanceof Location){
								location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
							}
							location.add(0 + disX, 0.3f + disY, 0 + disZ);
							
							
							
							double inc = PI2 / 1;
							double angle = 1 * inc;
							Vector v = new Vector();
							v.setX(Math.cos(angle) * radius);
							v.setZ(Math.sin(angle) * radius);
							
							VectorUtils.rotateVector(v, xRotation * MathUtils.degreesToRadians, yRotation * MathUtils.degreesToRadians, zRotation * MathUtils.degreesToRadians);
							
							if (rainbowMode == true)
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
							if (isFacing == true) {
								
								location.setPitch(0.0F);
							    location.add(location.getDirection().multiply(-0.2D));
							    Location loc1R = location.clone();
							    loc1R.setYaw(loc1R.getYaw() + faceAngle);
							    Location loc2R = loc1R.clone().add(loc1R.getDirection().multiply(1));
								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc2R.add(0.0D, 0.0D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
							}else{
								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
	
							}
								
							
						}
	        	}, delayTicks, delayBySecond).getTaskId();					
			EffectsLib.arraylist.put(idName, simpleDot);
		//} else {
			//EffectsLib.stopEffect(idName);
		}
//		Bukkit.getServer().getScheduler().cancelTask(simpleDot);
	}
	
	public static void drawSimpleDot(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
 			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final double visibleRange, 
			final long delayTicks, 
			final long delayBySecond) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int simpleDot = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						//TODO Future wing designer, add mirrorMode
						public float hue;
						Location location;// = player.getLocation().clone();
						@Override
						public void run() {
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
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						}
	        	}, delayTicks, delayBySecond).getTaskId();					
			EffectsLib.arraylist.put(idName, simpleDot);
		}
	}
	
	
	

	public static void drawComplexSpiral(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final boolean clockwise, 
			final boolean scan, 
			final double visibleRange, 
			final double xRotation, 
			final double yRotation, 
			final double zRotation, 
			final float radius, 
			final float height, 
			final float effectMod, 
			final int circleDensity, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final float steps, 
			final long delayTicks, 
			final long delayBySecond
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int complexSpiral = Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				float step = steps;
				private float hue;
				float i;
				boolean up = true;
				Vector v;
				Location location;// = player.getLocation().clone();
				@Override
				public void run() {
					if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
					}
					else if (center instanceof Location){
						location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
					}
					location.add(disX, disY, disZ);
					if (rainbowMode == true)
						this.hue += 0.01F;
						this.hue = (this.hue >= 1.0F ? 0.0F : this.hue);
					double angle = ( PI2 / circleDensity ) * step;
					double y = 0.3 * i;	
		            if (clockwise == false)
			            v = new Vector(Math.sin(angle) * radius, y, Math.cos(angle) * radius);
		            if (clockwise == true)
		                v = new Vector(Math.cos(angle) * radius, y, Math.sin(angle) * radius);
					VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
					ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
					//location.subtract(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
					step++;
					if (scan == true){
						if (i > height) {
							up = false;
						}
						else if (i < 0) {
							up = true;
						}
					}else{
						if (i > height) {
							i = 0;
						}
						if (i < 0) {
							i = height;
						}
					}	
					if (up == true)
						i += effectMod;
					if (up == false)
						i -= effectMod;
					//Bukkit.getServer().broadcastMessage("[skDragon] " + i);
				}
			}, delayTicks, delayBySecond).getTaskId();//0,0
			EffectsLib.arraylist.put(idName, complexSpiral);
		}
	}	
/*
	public static void drawSpiral(			
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final double visibleRange, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final float steps, 
			final long delayTicks, 
			final long delayBySecond, 
			final Player player
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int spiral = Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				float step = steps;
				float i = 0;
				private float hue;
				Location location;// = player.getLocation();
				@Override
				public void run() {
					if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
					}
					else if (center instanceof Location){
						location = ((Location) center);
					}
					location.add(disX, disY, disZ);
					if (rainbowMode == true)
						this.hue += 0.01F;
						this.hue = (this.hue >= 1.0F ? 0.0F : this.hue);
					double x = Math.sin((Math.PI / 10) * step) * 1;
					double y = 0.3 * i;
					double z = Math.cos((Math.PI / 10) * step) * 1;
					Vector v = new Vector(x, y, z);
					location.add(v);
					ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
					step++;
					i += 0.1;
					if (i > 6) {
						i = 0;
					}
				}
			}, delayTicks, delayBySecond).getTaskId();//0,0
			EffectsLib.arraylist.put(idName, spiral);
		}
	}
*/
	public static void drawBand(
			final String particle,
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final double visibleRange, 

			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayTicks, 
			final long delayBySecond, 
			final Player player
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int band = Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				private float hue;
				Location location;
				@Override
				public void run() {
					if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
					}
					else if (center instanceof Location){
						location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
					}
					//Location location = player.getLocation();
					location.add(disX, disY, disZ);
					for (int i = 0; i < 15; i++) {
						location.setY(location.getY() + 0.1D);
						if (rainbowMode == true)
							this.hue += 0.01F;
							this.hue = (this.hue >= 1.0F ? 0.0F : this.hue);
						ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
					}
				}
			}, delayTicks, delayBySecond).getTaskId();//1,1
			EffectsLib.arraylist.put(idName, band);
		}
	}

	public static void drawNyanBand(
		final Object center, 
		final String idName, 
		final boolean isSinglePlayer, 
		final double visibleRange, 
		final long delayTicks, 
		final long delayBySecond, 
		final Player player
		) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int rainbowband = Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				Location location;
				@Override
				public void run() {
					if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
					}
					else if (center instanceof Location){
						location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
					}
					for (int i = 0; i < 15; i++) {
						ParticleEffect.valueOf("redstone").display(player, location, visibleRange, isSinglePlayer, i);
						location.setY(location.getY() + 0.1D);
						}
				}
			}, delayTicks, delayBySecond).getTaskId();//0,0
			EffectsLib.arraylist.put(idName, rainbowband);
		}
	}
	
	public static void drawImage(
			final File file, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean enableRotation, 
			final Plane plane, 
			final int pixelStepX, 
			final int pixelStepY, 
			final long scaleSize, 
			final double visibleRange, 
			final double xRotation, 
			final double yRotation, 
			final double zRotation, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayTicks, 
			final long delayBySecond
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int colorImage = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
			    //String finalFileName = null;
			    int stepX = pixelStepX;//10;
			    int stepY = pixelStepY;//10;
			    float size = (float) 1 / scaleSize;
			    //Plane plane = Plane.valueOf(planeSpin);//Plane.XYZ;//     VectorUtils.Plane.XYZ;
			    double angularVelocityX = PI / 200;
			    double angularVelocityY = PI / 170;
			    double angularVelocityZ = PI / 155;
			    BufferedImage image = null;
			    boolean isGif = false;
			    File gifFile = null;
			    int step = 0;
			    int rotationStep = 0;
			    int delay = 0;
			    Location location;// = getLocation();
			    boolean invert = false;
				@Override
				public void run() {
					if (image == null && file != null) {
			        //    File file;
			        //    if (!finalFileName.startsWith(File.pathSeparator)) {
			        //        file = new File(skDragonCore.skdragoncore.getDataFolder().getAbsolutePath(), finalFileName);
			        //        //Bukkit.getServer().broadcastMessage("[skDragon] 1 -->" + file + "<--");
			        //    } else {
			        //        file = new File(finalFileName);
			        //        //Bukkit.getServer().broadcastMessage("[skDragon] 2 -->" + file + "<--");
			        //    }
			        //    //Bukkit.getServer().broadcastMessage("[skDragon] 3 -->" + finalFileName + "<--");
			        //    //Bukkit.getServer().broadcastMessage("[skDragon] 3 -->" + file + "<--");
			        //    //loadFile(file);
			            try {
			            	//Bukkit.getServer().broadcastMessage("[skDragon] 3 -->" + file + "<--");
			    			image = ImageIO.read(file);
			            	isGif = file.getName().endsWith(".gif");
			            	gifFile = file;
			            	
			            } catch (Exception ex) {
			            	Skript.warning("[skDragon] Error: Invalid file used, make sure the image is in /plugins/skDragon/");
			                //ex.printStackTrace();
			                image = null;
			            }
					}
			        if (image == null) {
			        	EffectsLib.stopEffect(idName);
			        	Skript.warning("[skDragon] Error: The image failed to load, try another? :c");
			        	return;
			        }
			        if (isGif) {
			            try {
			                image = getImg(step);
			            } catch (IOException e) {
			            	Skript.warning("[skDragon] Error: The .gif failed to load..");
			                e.printStackTrace();
			            }
			            if (delay == 5) {
			                step++;
			                delay = 0;
			            }
			            delay++;
			        }
			        if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
					}
					else if (center instanceof Location){
						location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
					}
			        int clr;
			        for (int y = 0; y < image.getHeight(); y += stepY) {
			            for (int x = 0; x < image.getWidth(); x += stepX) {
			                //Removes the image mask/transparency
			            	clr = image.getRGB(x, y);
			                if (!invert && (clr == 0 || clr == 16777215)) {
			                    continue;
			                } else if (invert &&  (clr != 0 || clr == 16777215)) {
			                    continue;
			                }
			                //Bukkit.getServer().broadcastMessage("[skDragon] pixel -->" + clr + "<--");
			                //Bukkit.getServer().broadcastMessage("[skDragon] pixel -->" + Color.black.getRGB() + "<--");
			                //if (!invert && Color.black.getRGB() == clr) {//16777216
			                //    continue;
			                //} else if (invert &&  Color.black.getRGB() != clr) {
			                //    continue;
			                //}
			            	Vector v = new Vector((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).multiply(size);
			                //VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);
			                //VectorUtils.rotateVector(v, Math.toRadians(xRotation), yRotationMath.toRadians(xRotation), Math.toRadians(zRotation));
			                VectorUtils.rotateVector(v, xRotation * MathUtils.degreesToRadians, yRotation * MathUtils.degreesToRadians, zRotation * MathUtils.degreesToRadians);
			                if (enableRotation) {
			                    double rotX = 0;
			                    double rotY = 0;
			                    double rotZ = 0;
			                    switch (plane) {
			                        case X:
			                            rotX = angularVelocityX * rotationStep;
			                            break;
			                        case Y:
			                            rotY = angularVelocityY * rotationStep;
			                            break;
			                        case Z:
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                        case XY:
			                            rotX = angularVelocityX * rotationStep;
			                            rotY = angularVelocityY * rotationStep;
			                            break;
			                        case XZ:
			                            rotX = angularVelocityX * rotationStep;
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                        case XYZ:
			                            rotX = angularVelocityX * rotationStep;
			                            rotY = angularVelocityY * rotationStep;
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                        case YZ:
			                            rotY = angularVelocityY * rotationStep;
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                    }
			                    VectorUtils.rotateVector(v, rotX, rotY, rotZ);
			                }
			                int r = (new Color(image.getRGB(x, y))).getRed();
			                int g = (new Color(image.getRGB(x, y))).getGreen();
			                int b = (new Color(image.getRGB(x, y))).getBlue();
			                //PacketPlayOutWorldParticles test;
			                //PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(this.particletype, this.longdistance, (float)this.location.getX(), (float)this.location.getY(), (float)this.location.getZ(), this.offsetx, this.offsety, this.offsetz, this.speed, this.ammount, 0);
			                //->location.getWorld().spigot().playEffect(location.add(v), Effect.COLOURED_DUST, 0, 0, r/255, g/255, b/255, 1.0f, 0, 64);
			                //location.getWorld().spigot().playEffect(location, effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, radius);
			                //ParticleEffect.valueOf("redstone").display(r, g, b, 1.0f, 0, location.add(v), 50.0);
			                ParticleEffect.valueOf("redstone").display(location.add(v), visibleRange, isSinglePlayer, player, r, g, b);
			                //ParticleEffect.redstone.display(new ParticleEffect.OrdinaryColor(r, g, b), location.add(v), 100);
			                location.subtract(v);
			            }
			        }
			        rotationStep++;
			        //image.flush();
				}
				private BufferedImage getImg(int s) throws IOException {
					//Bukkit.getServer().broadcastMessage("[skDragon] this thing got stuff!");
					ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
			        ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
			        ImageInputStream in = ImageIO.createImageInputStream(gifFile);
			        reader.setInput(in);
			        for (int i = 0, count = reader.getNumImages(true); i < count; i++) {
			            BufferedImage image = reader.read(i);
			            images.add(image);
			            //if (i == count - 1){
			            	//Bukkit.getServer().broadcastMessage("[skDragon] count -->" + count + "<--");
			            	//Bukkit.getServer().broadcastMessage("[skDragon] i -->" + i + "<--");
			            	//image.flush();
			            	//TODO see if this helped :/
			            //}
			        }
			        if (step >= reader.getNumImages(true)) {
			            step = 0;
			            return images.get(s - 1);
			        }
			        reader.dispose();
			        in.close();
			        return images.get(s);
			    }
			}, delayTicks, delayBySecond).getTaskId();
		    EffectsLib.arraylist.put(idName, colorImage); 
		}
	}
	public static void drawBreath(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
 			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final float finalArcPitch, 
			final int finalArcs, 
			final int finalParticleCount, 
			final int finalStepPerIteration, 
			final float finalLength, 
			final double visibleRange, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayTicks, 
			final long delayBySecond) {
		if (!EffectsLib.arraylist1.containsKey(idName)) {
			int breath = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
					    protected int step = 0;
					    protected final List<Float> rndF = new ArrayList<Float>(finalArcs);;
					    protected final List<Double> rndAngle = new ArrayList<Double>(finalArcs);;
					    Location location;
					    public float hue;
						@Override
						public void run() {
					        if (center instanceof Entity) {
								location = ((Entity) center).getLocation();
							}
							else if (center instanceof Location){
								location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
							}
					        location.add(disX, 1.475 + disY, disZ);
					        if (rainbowMode == true)
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);	
					        for (int j = 0; j < finalStepPerIteration; j++) {
					            if (step % finalParticleCount == 0) {
					                rndF.clear();
					                rndAngle.clear();
					            }
					            while (rndF.size() < finalArcs) {
					                rndF.add(RandomUtils.random.nextFloat());
					            }
					            while (rndAngle.size() < finalArcs) {
					                rndAngle.add(RandomUtils.getRandomAngle());
					            }
					            for (int i = 0; i < finalArcs; i++) {
					                float pitch = rndF.get(i) * 2 * finalArcPitch - finalArcPitch;//this.finalArcPitch - this.finalArcPitch;
					                float x = (step % finalParticleCount) * finalLength / finalParticleCount;
					                float y = (float) (pitch * Math.pow(x, 2));
					                Vector v = new Vector(x, y, 0);
					                VectorUtils.rotateAroundAxisX(v, rndAngle.get(i));
					                VectorUtils.rotateAroundAxisZ(v, -location.getPitch() * MathUtils.degreesToRadians);
					                VectorUtils.rotateAroundAxisY(v, -(location.getYaw() + 90) * MathUtils.degreesToRadians);
					                //location.add(v);
					                ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
					                location.subtract(v);
					            }
					            step++;
					        }
						}
					}, delayTicks, delayBySecond).getTaskId();//2,1
				EffectsLib.arraylist1.put((idName), breath);
			}
		}
	public static void drawCylinder(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
 			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final boolean enableRotation, 
			final boolean solid, 
			final float radius, 
			final int finalParticleCount, 
			final float height, 
			final float ratio, 
			final double visibleRange, 
			final double xRotation, 
			final double yRotation, 
			final double zRotation, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayTicks, 
			final long delayBySecond) {
		if (!EffectsLib.arraylist1.containsKey(idName)) {
			int cylinder = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
			    // Radius of cylinder
			    //public float radius = 1;
			    //Height of Cylinder
			    //public float height = 3;
			    public double angularVelocityX = PI / 200;
			    public double angularVelocityY = PI / 170;
			    public double angularVelocityZ = PI / 155;
			    public double rotationX, rotationY, rotationZ;
			    //Particles in each row
			    //public int finalParticleCount = 100;
			    //public boolean enableRotation = true;
			    //Toggles the cylinder to be solid
			    //public boolean solid = false;
			    protected int step = 0;
			    //Ratio of sides to entire surface
			    //protected float sideRatio = 0;
			    float sideRatio = ratio;
			    Location location;
			    public float hue;
				@Override
				public void run() {
			        if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
					}
					else if (center instanceof Location){
						location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
					}
			        location.add(disX, disY, disZ);
			        if (rainbowMode == true)
						hue += 0.01F;
						hue = (hue >= 1.0F ? 0.0F : hue);
					
					if (sideRatio == 0){ 
						float grounds, side;
				        grounds = PI * PI * radius * 2;
				        side = 2 * PI * radius * height;
				        sideRatio = side / (side + grounds);
					}
			        Random r = RandomUtils.random;
			        double xRotation = rotationX, yRotation = rotationY, zRotation = rotationZ;
			        if (enableRotation) {
			            xRotation += step * angularVelocityX;
			            yRotation += step * angularVelocityY;
			            zRotation += step * angularVelocityZ;
			        }
			        for (int i = 0; i < finalParticleCount; i++) {
			            float multi = (solid) ? r.nextFloat() : 1;
			            Vector v = RandomUtils.getRandomCircleVector().multiply(radius);
			            if (r.nextFloat() <= sideRatio) {
			                // SIDE PARTICLE
			                v.multiply(multi);
			                v.setY((r.nextFloat() * 2 - 1) * (height / 2));
			            } else {
			                // GROUND PARTICLE
			                v.multiply(r.nextFloat());
			                if (r.nextFloat() < 0.5) {
			                    // TOP
			                    v.setY(multi * (height / 2));
			                } else {
			                    // BOTTOM
			                    v.setY(-multi * (height / 2));
			                }
			            }
			            if (enableRotation) {
			                VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
			            }
			            //VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
						//if (enableRotation)
						//	VectorUtils.rotateVector(v, angularVelocityX * step, angularVelocityY * step, angularVelocityZ * step);
			            ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
			            location.subtract(v);
			        }
		            ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
			        step++;
				}				
			}, delayTicks, delayBySecond).getTaskId();
			EffectsLib.arraylist1.put((idName), cylinder);
		}
	}
	
	
	public static void drawVortex(final Player p, final String idName, final int r, final int g, final int b) {
		
		if (!EffectsLib.arraylist.containsKey(idName)) {
			
			int vortex = Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
			    /**
			     * Radius of vortex (2)
			     */
			    public float radius = 2;

			    /**
			     * Growing per iteration (0.05)
			     */
			    public float grow = .05f;

			    /**
			     * Radials per iteration (PI / 16)
			     */
			    public double radials = PI / 16;

			    /**
			     * Helix-circles per iteration (3)
			     */
			    public int circles = 3;

			    /**
			     * Amount of helices (4)
			     * Yay for the typo
			     */
			    public int helixes = 4;

			    /**
			     * Current step. Works as counter
			     */
			   // protected int step = 0;
				float step = 2.0F;
				
				@Override
				public void run() {

					// TODO Add this in
					Location location = p.getLocation();
			        for (int x = 0; x < circles; x++) {
			            for (int i = 0; i < helixes; i++) {
			                double angle = step * radials + (2 * Math.PI * i / helixes);
			                Vector v = new Vector(Math.cos(angle) * radius, step * grow, Math.sin(angle) * radius);
			                VectorUtils.rotateAroundAxisX(v, (location.getPitch() + 90) * MathUtils.degreesToRadians);
			                VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);

			                location.add(v);
			                ParticleEffect.redstone.display(new ParticleEffect.OrdinaryColor(r, g, b), location, 50);
			                location.subtract(v);
			            }
			            step++;
			        }
					
					
						for (float k = 3.5F; k > 0.0F; k -= 0.1F)
			            {
			              Location loc = p.getLocation();
			              Vector v = new Vector(k * Math.sin(k * this.step) / 3.0D, -k + 3.8D, k * Math.cos(k * this.step) / 3.0D);
			              loc.add(v);
			              
			              ParticleEffect.redstone.display(new ParticleEffect.OrdinaryColor(r, g, b), loc, 50);
			            }
			            this.step -= 0.1F;
			            if (this.step <= -10.0F) {
			              this.step = 10.0F;
			            }
					}
					
			}, 0l, 0l).getTaskId();
			EffectsLib.arraylist.put(idName, vortex);
		//}else {
			//EffectsLib.stopEffect(idName);
		}
	}
	public static void drawVortex2(final String particle, final Player p, final String idName, final boolean rainbowMode, final int r, final int g, final int b) {
		
		if (!EffectsLib.arraylist.containsKey(idName)) {
			
			int vortex2 = Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				float step = 2.0F;
				private float hue;
				@Override
				public void run() {

					// TODO Add this in
					for (float k = 3.5f; k > 0.0f; k -= 0.1f) {
                        final Location location = p.getLocation();
                        final Vector v = new Vector(k * Math.sin(k * this.step) / 3.0, -k + 3.8, k * Math.cos(k * this.step) / 3.0);
                        location.add(v);
                        if (rainbowMode == true)
							this.hue += 0.01F;
							this.hue = (this.hue >= 1.0F ? 0.0F : this.hue);
                        ParticleEffect.valueOf(particle).display(r, g, b, 1.0f, 0, location, 50.0);
                    }
                    this.step -= 0.1f;
                    if (this.step <= -10.0f) {
                        this.step = 10.0f;
                    }
					}
					
			}, 0l, 0l).getTaskId();
			EffectsLib.arraylist.put(idName, vortex2);
		}
	}
	
	


	public static void drawRainbowLine(final Player p, final String idName, final String particle) {

		if (!EffectsLib.arraylist.containsKey(idName)) {

			int rainbowline = Bukkit.getScheduler()
					.runTaskTimer(skDragonCore.skdragoncore, new Runnable() {

						private float hue = 0.00f;

						@Override
						public void run() {
							// TODO Most likely throw this out
							Location loc = p.getLocation();
							loc.add(0, 0.3f, 0);
							int argb = Color.HSBtoRGB(this.hue, 1.0F, 1.0F);
							float r = (argb >> 16 & 0xFF) / 255.0F;
							float g = (argb >> 8 & 0xFF) / 255.0F;
							float b = (argb & 0xFF) / 255.0F;
							r = r == 0.0F ? 0.001F : r;
							this.hue += 0.01F;
							this.hue = (this.hue >= 1.0F ? 0.0F : this.hue);

							ParticleEffect.valueOf(particle).display(r, g, b,
									1, 0, loc, 50);
							ParticleEffect.valueOf(particle).display(r, g, b,
									1, 0, loc, 50);
							ParticleEffect.valueOf(particle).display(r, g, b,
									1, 0, loc, 50);
							ParticleEffect.valueOf(particle).display(r, g, b,
									1, 0, loc, 50);
						}
					}, 1L, 1L).getTaskId();
			EffectsLib.arraylist.put(idName, rainbowline);
		//} else {
			//EffectsLib.stopEffect(idName);
		}
	}
	

/*	
	public static void drawWings1(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final String particle2, 
			final Material dataMat2, 
			final byte dataID2, 
			final float speed2, 
			final float offsetX2, 
			final float offsetY2, 
			final float offsetZ2, 
			final String particle3, 
			final Material dataMat3, 
			final byte dataID3, 
			final float speed3, 
			final float offsetX3, 
			final float offsetY3, 
			final float offsetZ3, 
			final Player player, 
			final String idName, 
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final float angle, 
			final double visibleRange

			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int wings1 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						private float hue;
						@Override
						public void run() {
							float offsetX4; 
							float offsetY4; 
							float offsetZ4;
						    Location loc = player.getLocation().clone();
						    loc.setPitch(0.0F);
						    loc.add(0.0D, 1.8D, 0.0D);
						    loc.add(loc.getDirection().multiply(-0.2D));
						    if (rainbowMode == true)
								this.hue += 0.01F;
								this.hue = (this.hue >= 1.0F ? 0.0F : this.hue);
						    Location loc1R = loc.clone();
						    loc1R.setYaw(loc1R.getYaw() + angle);
						    Location loc2R = loc1R.clone().add(loc1R.getDirection().multiply(1));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc2R.add(0.0D, 0.8D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc3R = loc1R.clone().add(loc1R.getDirection().multiply(0.8D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc3R.add(0.0D, 0.6D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc4R = loc1R.clone().add(loc1R.getDirection().multiply(0.6D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc4R.add(0.0D, 0.4D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc5R = loc1R.clone().add(loc1R.getDirection().multiply(0.4D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc5R.clone().add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc6R = loc1R.clone().add(loc1R.getDirection().multiply(0.2D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc6R.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    int zu = 0;
						    while (zu <= 3)
						    {
						      zu++;
						      if (zu == 4) {
						        offsetX4 = offsetX; 
						        offsetY4 = offsetY;
						        offsetZ4 = offsetZ;
						      } else {
						        offsetX4 = offsetX2; 
						        offsetY4 = offsetY2;
						        offsetZ4 = offsetZ2;
						      }
						      if (((zu == 4) || (zu == 3))) {
						        offsetX4 = offsetX3; 
						        offsetY4 = offsetY3;
						        offsetZ4 = offsetZ3;  
						      }
								ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc2R.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
								ParticleEffect.valueOf(particle2).display(dataMat, dataID, player, loc3R.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
								ParticleEffect.valueOf(particle2).display(dataMat, dataID, player, loc4R.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
								ParticleEffect.valueOf(particle3).display(dataMat, dataID, player, loc5R.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
								ParticleEffect.valueOf(particle3).display(dataMat, dataID, player, loc6R.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
						    }
						    Location loc1L = loc.clone();
						    loc1L.setYaw(loc1L.getYaw() - angle);
						    Location loc2L = loc1L.clone().add(loc1L.getDirection().multiply(1));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc2L.add(0.0D, 0.8D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc3L = loc1L.clone().add(loc1L.getDirection().multiply(0.8D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc3L.add(0.0D, 0.6D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc4L = loc1L.clone().add(loc1L.getDirection().multiply(0.6D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc4L.add(0.0D, 0.4D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc5L = loc1L.clone().add(loc1L.getDirection().multiply(0.4D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc5L.clone().add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    Location loc6L = loc1L.clone().add(loc1L.getDirection().multiply(0.2D));
							ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc6L.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						    zu = 0;
						    while (zu <= 3)
						    {
						      zu++;
						      if (zu == 4) {
						        offsetX4 = offsetX; 
						        offsetY4 = offsetY;
						        offsetZ4 = offsetZ;
						      } else {
						        offsetX4 = offsetX2; 
						        offsetY4 = offsetY2;
						        offsetZ4 = offsetZ2;
						      }
						      if (((zu == 4) || (zu == 3))) {
						        offsetX4 = offsetX3; 
						        offsetY4 = offsetY3;
						        offsetZ4 = offsetZ3;
						      }
						      ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc2L.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
						      ParticleEffect.valueOf(particle2).display(dataMat, dataID, player, loc3L.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
						      ParticleEffect.valueOf(particle2).display(dataMat, dataID, player, loc4L.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
						      ParticleEffect.valueOf(particle3).display(dataMat, dataID, player, loc5L.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
						      ParticleEffect.valueOf(particle3).display(dataMat, dataID, player, loc6L.add(0.0D, -0.2D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX4, offsetY4, offsetZ4, speed, 1);
						    }   
						}
					}, 2L, 1L).getTaskId();
				EffectsLib.arraylist.put(idName, wings1);
			} else {
				EffectsLib.stopEffect(idName);
			}
		}
*/	
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
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final float wingAngle, 
			final double visibleRange, 
			final boolean[][] shape, 
			final float height,
			final double space, 
			final long delayTicks, 
			final long delayBySecond,
			final Player player) {
		if (!EffectsLib.arraylist1.containsKey(idName)) {
			int wings = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					}, delayTicks, delayBySecond).getTaskId();//2,1
				EffectsLib.arraylist1.put((idName), wings);
			}
		}
	public static void drawWingsColor2(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final float wingAngle, 
			final double visibleRange, 
			final boolean[][] shape, 
			final float height,
			final double space, 
			final long delayTicks, 
			final long delayBySecond,
			final Player player) {
		if (!EffectsLib.arraylist2.containsKey(idName)) {
			int wings = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
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
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
							//final double space = 0.2;
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					}, delayTicks, delayBySecond).getTaskId();//2,1
				EffectsLib.arraylist2.put((idName), wings);
			}
		}
	public static void drawWingsColor3(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final float wingAngle, 
			final double visibleRange, 
			final boolean[][] shape, 
			final float height,
			final double space, 
			final long delayTicks, 
			final long delayBySecond,
			final Player player) {
		if (!EffectsLib.arraylist3.containsKey(idName)) {
			int wings = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
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
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
							//final double space = 0.2;
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					}, delayTicks, delayBySecond).getTaskId();//2,1
				EffectsLib.arraylist3.put((idName), wings);
			}
		}
	public static void drawWingsColor4(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final float wingAngle, 
			final double visibleRange, 
			final boolean[][] shape, 
			final float height,
			final double space, 
			final long delayTicks, 
			final long delayBySecond,
			final Player player) {
		if (!EffectsLib.arraylist4.containsKey(idName)) {
			int wings = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
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
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
							//final double space = 0.2;
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
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
					}, delayTicks, delayBySecond).getTaskId();//2,1
				EffectsLib.arraylist4.put((idName), wings);
			}
		}
	
	
	
	
	public static void drawPlanet(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final String particle2, 
			final Material dataMat2, 
			final byte dataID2, 
			final float speed2, 
			final Object center, 
			final String idName, 
			final boolean isSinglePlayer, 
			final Player player, 
			final boolean rainbowMode, 
			final boolean enableRotation, 
			final Plane plane, 
			final float rotationStep, 
			final boolean enableOrbit, 
			//final Plane plane2, 
			final float orbitalRadius, 
			final float orbitalStepDensity, 
			final double xRotation, 
			final double yRotation, 
			final double zRotation, 
			
			
			final float radius, 
			final int density,  
			final int precision, 
			final float mountainHeight, 
			
			
			
			final double visibleRange, 
			
			//final double orbitalStepDensity, 
			//final double orbitalRadius, 
			//final double xRotation, 
			//final double yRotation, 
			//final double zRotation, 
			
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final float offsetX2, 
			final float offsetY2, 
			final float offsetZ2, 
			final double disX, 
			final double disY, 
			final double disZ, 
			final long delayTicks, 
			final long delayBySecond
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int planet = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						public float hue;
						Location location;
						double angularVelocityX = PI / 360;//40;//200;
					    double angularVelocityY = PI / 360;//40;//170;
					    double angularVelocityZ = PI / 360;//40;//155;
					    //int rotationStep = 1;//0;
					    int orbitalStep = 0;
					    boolean firstStep = true;
					    //Caches vectors to increase performance
					    Set<Vector> cacheGreen = new HashSet<Vector>();
					    Set<Vector> cacheBlue = new HashSet<Vector>();
					    Set<Vector> cache = new HashSet<Vector>();
					    public void invalidate() {
					        firstStep = false;
					        cacheGreen.clear();
					        cacheBlue.clear();
					        int sqrtParticles = (int) Math.sqrt(density);
					        float theta = 0, phi, thetaStep = PI / sqrtParticles, phiStep = PI2 / sqrtParticles;
					        for (int i = 0; i < sqrtParticles; i++) {
					            theta += thetaStep;
					            phi = 0;
					            for (int j = 0; j < sqrtParticles; j++) {
					                phi += phiStep;
					                float x = radius * MathUtils.sin(theta) * MathUtils.cos(phi);
					                float y = radius * MathUtils.sin(theta) * MathUtils.sin(phi);
					                float z = radius * MathUtils.cos(theta);
					                cache.add(new Vector(x, y, z));
					            }
					        }
					        float increase = mountainHeight / precision;
					        for (int i = 0; i < precision; i++) {
					            double r1 = RandomUtils.getRandomAngle(), r2 = RandomUtils.getRandomAngle(), r3 = RandomUtils.getRandomAngle();
					            for (Vector v : cache) {
					                if (v.getY() > 0) {
					                    v.setY(v.getY() + increase);
					                } else {
					                    v.setY(v.getY() - increase);
					                }
					                if (i != precision - 1) {
					                    VectorUtils.rotateVector(v, r1, r2, r3);
					                }
					            }
					        }
					        float minSquared = Float.POSITIVE_INFINITY, maxSquared = Float.NEGATIVE_INFINITY;
					        for (Vector current : cache) {
					            float lengthSquared = (float) current.lengthSquared();
					            if (minSquared > lengthSquared) {
					                minSquared = lengthSquared;
					            }
					            if (maxSquared < lengthSquared) {
					                maxSquared = lengthSquared;
					            }
					        }
					        // COLOR PARTICLES
					        float average = (minSquared + maxSquared) / 2;
					        for (Vector v : cache) {
					            float lengthSquared = (float) v.lengthSquared();
					            if (lengthSquared >= average) {
					                cacheGreen.add(v);
					            } else {
					                cacheBlue.add(v);
					            }
					        }
					    }
						@Override
						public void run() {
							if (center instanceof Entity) {
								location = new Location(((Entity) center).getWorld(), ((Entity) center).getLocation().getX(), ((Entity) center).getLocation().getY() + 3, ((Entity) center).getLocation().getZ());
							}
							else if (center instanceof Location){
								location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
							}
							location.add(disX, disY, disZ);
							if (rainbowMode == true)
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
					        if (firstStep) {
					            invalidate();
					        }
					        //double rotX2 = 0;
		                    //double rotY2 = 0;
		                    //double rotZ2 = 0;
					        if (enableOrbit){
		                    	//Vector v2 = new Vector();
			                    //switch (plane2) {
			                   //     case X:
			                   //         rotX2 = angularVelocityX * 1;
			                    //        break;
			                    //    case Y:
			                    //        rotY2 = angularVelocityY * 1;
			                    //        break;
			                     //   case Z:
			                    //        rotZ2 = angularVelocityZ * 1;
			                    //        break;
			                    //    case XY:
			                     //       rotX2 = angularVelocityX * 1;
			                     //       rotY2 = angularVelocityY * 1;
			                     //       break;
			                    //    case XZ:
			                    //        rotX2 = angularVelocityX * 1;
			                    //        rotZ2 = angularVelocityZ * 1;
			                    //        break;
			                     //   case XYZ:
			                      //      rotX2 = angularVelocityX * 1;
			                     //       rotY2 = angularVelocityY * 1;
			                     //       rotZ2 = angularVelocityZ * 1;
			                     //       break;
			                    //    case YZ:
			                    //        rotY2 = angularVelocityY * 1;
			                    //        rotZ2 = angularVelocityZ * 1;
			                    //        break;
			                    //}
			                    double inc = PI2 / orbitalStepDensity;
			                    double angle = orbitalStep * inc;
				        		Vector v2 = new Vector();
			                    v2.setX(Math.cos(angle) * orbitalRadius);
								v2.setZ(Math.sin(angle) * orbitalRadius);
				                VectorUtils.rotateVector(v2, xRotation * MathUtils.degreesToRadians, yRotation * MathUtils.degreesToRadians, zRotation * MathUtils.degreesToRadians);
		                    	//VectorUtils.rotateVector(v2, rotX2, rotY2, rotZ2);
		                    	location.add(v2);
		                    	orbitalStep++;
					        }
					        //Add orbits and axis spin
					        if (enableRotation){
					        	double rotX = 0;
			                    double rotY = 0;
			                    double rotZ = 0;
			                    switch (plane) {
			                        case X:
			                            rotX = angularVelocityX * rotationStep;
			                            break;
			                        case Y:
			                            rotY = angularVelocityY * rotationStep;
			                            break;
			                        case Z:
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                        case XY:
			                            rotX = angularVelocityX * rotationStep;
			                            rotY = angularVelocityY * rotationStep;
			                            break;
			                        case XZ:
			                            rotX = angularVelocityX * rotationStep;
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                        case XYZ:
			                            rotX = angularVelocityX * rotationStep;
			                            rotY = angularVelocityY * rotationStep;
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                        case YZ:
			                            rotY = angularVelocityY * rotationStep;
			                            rotZ = angularVelocityZ * rotationStep;
			                            break;
			                    }
						        for (Vector v : cache) {
							        VectorUtils.rotateVector(v, rotX, rotY, rotZ);
						        }
					        }
					        for (Vector v : cacheGreen) {
			                    ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
					            location.subtract(v);
					        }
					        for (Vector v : cacheBlue) {
			                    ParticleEffect.valueOf(particle2).display(dataMat2, dataID2, player, location.add(v), visibleRange, isSinglePlayer, rainbowMode, hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
			                    location.subtract(v);
					        }
					        //if (enableOrbit)
					        //    	location.subtract(v2);
						}
					}, delayTicks, delayBySecond).getTaskId();
			EffectsLib.arraylist.put(idName, planet);
		}
	}
	
	
	
	
	
	
	
//  BETA TEST SECTION
	
	//BloodHelix
	public static void drawBetaTest1(
			final Player p, 
			final String idName
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final ParticleEffect.OrdinaryColor color = new ParticleEffect.OrdinaryColor(255, 20, 20);
			final int i = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				int particles = 40;
		        float radius = 2.0f;
		        protected int i;
		        int speed = 25;
		        double height = 0.0;
		        int particles2 = 40;
		        float radius2 = 2.0f;
		        protected int i2;
		        int speed2 = 25;
		        double height2 = 0.0;
		        //ParticleEffect.OrdinaryColor color = new ParticleEffect.OrdinaryColor(255, 20, 20);
		        @Override
		        public void run() {
	
		            final Location location = p.getLocation();
		            final Location location2 = p.getLocation();
		            final double angle = 6.283185307179586 * this.i / (this.particles * this.speed);
		            final double x = Math.cos(angle) * this.radius;
		            final double z = Math.sin(angle) * this.radius;
		            location.add(x, this.height, z);
		            
		            ParticleEffect.redstone.display(color, location, 30.0D);
		            
		            location.subtract(x, 0.0, z);
		            this.i += this.speed;
		            if (this.radius > 0.02) {
		                this.radius -= 0.05;
		                this.height += 0.1;
		            }
		            else {
		                this.radius = 2.0f;
		                this.height = 0.0;
		            }
		            final double angle2 = 6.283185307179586 * this.i2 / (this.particles2 * this.speed2);
		            final double x2 = Math.cos(angle2) * -this.radius2;
		            final double z2 = Math.sin(angle2) * -this.radius2;
		            final Vector v = new Vector(x2, this.height2, z2);
		            location2.add(v);
		            ParticleEffect.redstone.display(color, location, 30.0D);
		            location2.subtract(x, 0.0, z);
		            this.i2 += this.speed2;
		            if (this.radius2 > 0.02) {
		                this.radius2 -= 0.05;
		                this.height2 += 0.1;
		            }
		            else {
		                this.radius2 = 2.0f;
		                this.height2 = 0.0;
		            }
		        }
		    }, 1L, 1L).getTaskId();
			EffectsLib.arraylist.put(idName, i);
		} else {
			EffectsLib.stopEffect(idName);
		}
	}
	
	//Boop
	public static void drawBetaTest2(
			final Player p, 
			final String idName
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int betatest2 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				int particles = 70;
	            float radius = 1.245f;
	            protected int i;
	            int speed = 3;
	            double height = 0.3;
	            boolean up = false;
	            
	            @Override
	            public void run() {
	                final Location location = p.getEyeLocation();
	                final double angle = 6.283185307179586 * this.i / this.particles;
	                final double x = Math.cos(angle) * this.radius;
	                final double z = Math.sin(angle) * this.radius;
	                location.add(x, this.height, z);
	                ParticleEffect.heart.display(0, 0, 0, 0, 1, location, 50);
	                location.subtract(x, 0.0, z);
	                this.i += this.speed;
	                if (this.height < -1.49) {
	                    this.up = true;
	                }
	                else if (this.height > 0.6) {
	                    this.up = false;
	                }
	                if (this.up) {
	                    this.height += 0.05;
	                }
	                else {
	                    this.height -= 0.05;
	                }
	            }
	        }, 1L, 1L).getTaskId();
			
			
			
		    EffectsLib.arraylist.put(idName, betatest2);
		} else {
			EffectsLib.stopEffect(idName);
		}
	}	
	
	
	
	
	
	//Magic
	public static void drawBetaTest3(
			final Player p, 
			final String idName
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			 
			final int betatest3 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
			public float radius = 1.0f;
	            public float grow = 0.0f;
	            public double radials = 0.1963495408493621;
	            public int circles = 1;
	            public int helixes = 1;
	            protected int step = 0;
	            public float radius2 = 1.0f;
	            public float grow2 = 0.0f;
	            public double radials2 = 0.1963495408493621;
	            public int helixes2 = 1;
	            protected int step2 = 0;
	            
	            @Override
	            public void run() {

	                final Location loc = p.getLocation().add(0.0, 1.0, 0.0);
	                final Location loc2 = p.getLocation().add(0.0, 1.0, 0.0);
	                Vector v = null;
	                Vector v2 = null;
	                for (int x = 0; x < this.circles; ++x) {
	                    for (int i = 0; i < this.helixes; ++i) {
	                        final double angle = this.step * this.radials + 6.283185307179586 * i / this.helixes;
	                        v = new Vector(Math.cos(angle) * this.radius, (double)(this.step * this.grow), Math.sin(angle) * this.radius);
	                        VectorUtils.rotateAroundAxisX(v, 2.356194257736206);
	                        VectorUtils.rotateAroundAxisY(v, 0.01745329052209854);
	                        loc.add(v);
	                        ParticleEffect.happyvillager.display(0, 0, 0, 0, 1, loc, 50);
	                        loc.subtract(v);
	                    }
	                    ++this.step;
	                    for (int i = 0; i < this.helixes2; ++i) {
	                        final double angle = this.step2 * this.radials2 + 6.283185307179586 * i / this.helixes2;
	                        v2 = new Vector(Math.cos(angle) * this.radius2, (double)(this.step2 * this.grow2), Math.sin(angle) * this.radius2);
	                        VectorUtils.rotateAroundAxisX(v2, 0.7853980660438538);
	                        VectorUtils.rotateAroundAxisY(v2, 0.01745329052209854);
	                        loc2.add(v2);
	                        ParticleEffect.happyvillager.display(0, 0, 0, 0, 1, loc2, 50);
	                        loc2.subtract(v2);
	                    }
	                    ++this.step2;
	                }
	            }
	        }, 1L, 2L).getTaskId();

		    EffectsLib.arraylist.put(idName, betatest3);
		} else {
			EffectsLib.stopEffect(idName);
		}
	}
	
	//Random Sphere
	public static void drawBetaTest4(
			final Location location, 
			final Location locTest,
			final Player playerTest,
			final String idName
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int betatest4 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            public double radius = 1.5;
            public int particles = 50;

            @Override
            public void run() {
            	final Location loc = playerTest.getEyeLocation();
            	Bukkit.getServer().broadcastMessage("[skDragon] Second Test");
            	Bukkit.getServer().broadcastMessage("[skDragon] skriptLoc" + locTest);
        		Bukkit.getServer().broadcastMessage("[skDragon] playerLoc" + loc);
        		Bukkit.getServer().broadcastMessage("[skDragon actual test] " + location);
            	
                for (int i = 0; i < this.particles; ++i) {
                    final Vector point = RandomUtils.getRandomVector().multiply(this.radius);
                    location.add(point);
                    ParticleEffect.flame.display(0, 0, 0, 0, 1, location, 50);
                    location.subtract(point);
                }
            }
        }, 3L, 3L).getTaskId();

		    EffectsLib.arraylist.put(idName, betatest4);
		} else {
			EffectsLib.stopEffect(idName);
		}
	}
	

	
	//hill
	public static void drawBetaTest6(
			final Player player, 
			final String idName
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int betatest6 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
				public float height = 2.5f;
			    public float particles = 30;
			    public float edgeLength = 6.5f;
			    public double yRotation = PI / 7;
				@Override
				public void run() {
					Location location = player.getLocation();
			        Vector v = new Vector();
			        for (int x = 0; x <= particles; x++) {
			            double y1 = Math.sin(PI * x / particles);
			            for (int z = 0; z <= particles; z++) {
			                double y2 = Math.sin(PI * z / particles);
			                v.setX(edgeLength * x / particles).setZ(edgeLength * z / particles);
			                v.setY(height * y1 * y2);
			                VectorUtils.rotateAroundAxisY(v, yRotation);
			                ParticleEffect.flame.display(0, 0, 0, 0, 1, location.add(v), 50);
			                location.subtract(v);
			            }
			        }
				}
			}, 1L, 2L).getTaskId();
		    EffectsLib.arraylist.put(idName, betatest6);
		} else {
			EffectsLib.stopEffect(idName);
		}
	}
	

	//Random Sphere
	public static void drawBetaTest7(
			final Location location, 
			final Location locTest,
			final Player playerTest,
			final Object center, 
			final Object getTarget, 
			final String idName
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int betatest7 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            Location location;
			Location target;
            
            
            
            
            
            //public ParticleEffect particle = ParticleEffect.flame;

            /**
             * Should it do a zig zag?
             */
            public boolean isZigZag = false;

            /**
             * Number of zig zags in the line
             */
            public int zigZags = 10;

            /**
             * Particles per arc
             */
            public int particles = 100;

            /**
             * Length of arc
             * A non-zero value here will use a length instead of the target endpoint
             */
            public int length = 0;

            /**
             * Internal boolean
             */
            protected boolean zigs = false;

            /**
             * Internal counter
             */
            protected float step = 0;
            @Override
            public void run() {
            	//final Location location = playerTest.getEyeLocation();

            	if (center instanceof Entity) {
					location = ((Entity) center).getLocation();
				}
				else if (center instanceof Location){
					//location = ((Location) center);
					location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
				}
            	if (length > 0) {
            		target = location.clone().add(location.getDirection().normalize().multiply(length));
            	} else {
            		if (getTarget instanceof Entity) {
						target = ((Entity) getTarget).getLocation();
					}
					else if (getTarget instanceof Location){
						//location = ((Location) center);
						target = new Location(((Location) getTarget).getWorld(), ((Location) getTarget).getX(), ((Location) getTarget).getY(), ((Location) getTarget).getZ());
					}
            	}
            	
				double amount = particles / zigZags;
				if (location == null || target == null) {
		        	EffectsLib.stopEffect(idName);
		        	return;
		        }
				
				//SkriptHook hook = new SkriptHook();
            	//Vector3D test3 = new Vector3D(target.getX(), target.getY());
            	//Vector2D test4 = new Vector2D(0, 1);
            	//Point test1 = (Point) location.toVector().normalize();
            	//Line test = new Line((Vector2D) test1, null, 0);
            	//Line test2 = new Line(test4, null, 0);
            	//Location location = getLocation();
                //Location target = null;
                //if (length > 0) {
                //    target = location.clone().add(location.getDirection().normalize().multiply(length));
                //} else {
                //    target = getTarget();
                //}
				
                //if (target == null) {
                //    cancel();
                //    return;
                //}
                
                Vector link = target.toVector().subtract(location.toVector());
                float length = (float) link.length();
                link.normalize();

                float ratio = length / particles;
                Vector v = link.multiply(ratio);
                Location loc = location.clone().subtract(v);
                for (int i = 0; i < particles; i++) {
                    if (isZigZag) {
                        if (zigs) {
                            loc.add(0, .1, 0);
                        } else {
                            loc.subtract(0, .1, 0);
                        }
                    }
                    if (step >= amount) {
                        if (zigs) {
                        	zigs = false;
                        } else {
                        	zigs = true;
                        }
                        step = 0;
                    }
                    step++;
                    loc.add(v);
                    //display(particle, loc);
		            //ParticleEffect.valueOf(particle).display(dataMat, dataID, player, loc, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);

                }
                
            }
        }, 3L, 3L).getTaskId();

		    EffectsLib.arraylist.put(idName, betatest7);
		}
	}
	
	
	
	
	
	public static void drawStatCircle(final Location loc, final String idName) {
    	if (!EffectsLib.arraylist.containsKey(idName)) {
    	final int betatest8 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {

        //Particles.Blocks.put(L, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, (Runnable)new Runnable(loc) {
            Location l = loc;//.add(0.5, 0.1, 0.5);
            
            @Override
            public void run() {
                for (final Location loc : getCircle(this.l, 0.6, 15)) {
                    ParticleEffect.flame.display(0.0f, 0.0f, 0.0f, 0.0f, 1, loc, 100.0);
                }
                for (final Location loc : getCircle(this.l, 1.0, 20)) {
                    ParticleEffect.flame.display(0.0f, 0.0f, 0.0f, 0.0f, 1, loc, 100.0);
                }
            }
        //}, 0L, 5L));
		}, 0L, 5L).getTaskId();
	    EffectsLib.arraylist.put(idName, betatest8);
    	}
    }
	
	
	
    private static ArrayList<Location> getCircle(final Location center, final double radius, final int amount) {
        final World world = center.getWorld();
        final double increment = 6.283185307179586 / amount;
        final ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < amount; ++i) {
            final double angle = i * increment;
            final double x = center.getX() + radius * Math.cos(angle);
            final double z = center.getZ() + radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
    @SuppressWarnings("unused")
	private static ArrayList<Location> getCircleReverse(final Location center, final double radius, final int amount) {
        final World world = center.getWorld();
        final double increment = 6.283185307179586 / amount;
        final ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < amount; ++i) {
            final double angle = i * increment;
            final double x = center.getX() - radius * Math.cos(angle);
            final double z = center.getZ() - radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
	
/*	
	
	public static void drawWings(
			final String particle, 
			final Material dataMat, 
			final byte dataID, 
			final float speed, 
			final float offsetX, 
			final float offsetY, 
			final float offsetZ, 
			final Player player, 
			final String idName, 
			final boolean isSinglePlayer, 
			final boolean rainbowMode, 
			final float wingAngle, 
			final double visibleRange, 
			final boolean[][] shape) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			int wings = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
						private float hue;
						@Override
						public void run() {
							Location location = player.getLocation().clone(); 
					        if (rainbowMode == true)
								hue += 0.01F;
								hue = (hue >= 1.0F ? 0.0F : hue);
							final double space = 0.2;
					        double x;
					        final double defX = x = location.getX() - space * shape[0].length / 2.0 + space;
					        double y = location.clone().getY() + 2.0;
					        double angle = -((location.getYaw() + wingAngle) / 60.0f);
					        angle += ((location.getYaw() < -180.0f) ? 3.25 : 2.985);
					        for (int i = 0; i < shape.length; ++i) {
					            for (int j = 0; j < shape[i].length; ++j) {
					                if (shape[i][j]) {
					                    final Location target = location.clone();
					                    target.setX(x);
					                    target.setY(y);
					                    Vector v = target.toVector().subtract(location.toVector());
					                    final Vector v2 = VectorUtils.getBackVector(location);
					                    //Vector v3 = VectorUtils.rotateVector(v, (float) Math.toRadians(location.getYaw() + 90.0f), 0.0f);
					                    v = VectorUtils.rotateAroundAxisY(v, angle);
					                    v2.setY(0).multiply(-0.2);
					                    //v3.setY(0).multiply(-0.2);
					                    location.add(v);
					                    location.add(v2);
					                    //location.add(v3);
					                    for (int k = 0; k < 3; ++k) {
					                    	ParticleEffect.valueOf(particle).display(dataMat, dataID, player, location, visibleRange, isSinglePlayer, rainbowMode, hue, offsetX, offsetY, offsetZ, speed, 1);
					                    }
					                    //location.subtract(v3);
					                    location.subtract(v2);
					                    location.subtract(v);
					                }
					                x += space;
					            }
					            y -= space;
					            x = defX;
					        }

					        final Location location = p.getLocation();
		                    final double space = 0.2;
		                    double x;
		                    final double defX = x = location.getX() - space * shape[0].length / 2.0 + space;
		                    double y = location.clone().getY() + 2.8;
		                    double angle = -((location.getYaw() + 180.0f) / 60.0f);
		                    angle += ((location.getYaw() < -180.0f) ? 3.25 : 2.985);
		                    for (int i = 0; i < shape.length; ++i) {
		                        for (int j = 0; j < shape[i].length; ++j) {
		                            if (shape[i][j]) {
		                                final Location target = location.clone();
		                                target.setX(x);
		                                target.setY(y);
		                                Vector v = target.toVector().subtract(location.toVector());
		                                final Vector v2 = VectorUtils.getBackVector(location);
		                                v = VectorUtils.rotateAroundAxisY(v, angle);
		                                final double iT = i / 18.0;		//Lifts the wings up a bit
		                                v2.setY(0).multiply(-0.2 - iT);
		                                location.add(v);
		                                location.add(v2);
		                                for (int k = 0; k < 3; ++k) {
		                                    //ParticlesUtil.display(location, effect, 0);
		                                }
		                                location.subtract(v2);
		                                location.subtract(v);
		                            }
		                            x += space;
		                        }
		                        y -= space;
		                        x = defX;
		                    }

						}
					}, 2L, 1L).getTaskId();
				EffectsLib.arraylist.put(idName, wings);
			} else {
				EffectsLib.stopEffect(idName);
			}
		}
	*/

	/**
	 * Template
	 * 
	public static void drawBetaTest2(
			Player p, 
			final String idName
			) {
		if (!EffectsLib.arraylist.containsKey(idName)) {
			final int betatest2 = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
			
				
          private void animation() {
        final Location loc = this.player.getEyeLocation().clone().subtract(0.0, 0.5, 0.0).add(this.player.getLocation().getDirection().normalize().multiply(-0.75));
        for (double i = -10.0; i < 6.2; i += 0.2) {
            final double var = Math.sin(i / 12.0);
            final double x = Math.sin(i) * (Math.exp(Math.cos(i)) - 2.0 * Math.cos(4.0 * i) - Math.pow(var, 5.0)) / 2.0;
            final double z = Math.cos(i) * (Math.exp(Math.cos(i)) - 2.0 * Math.cos(4.0 * i) - Math.pow(var, 5.0)) / 2.0;
            final Vector v = new Vector(-x, 0.0, -z);
            rotateAroundAxisX(v, (loc.getPitch() + 90.0f) * 0.017453292f);
            rotateAroundAxisY(v, -loc.getYaw() * 0.017453292f);
            if (this.effect == ParticleEffect.REDSTONE) {
                ParticleEffect.REDSTONE.display(this.r, this.g, this.b, 0.004f, 0, loc.clone().add(v), 257.0);
            }
            else {
                this.effect.display(0.0f, 0.0f, 0.0f, 0.0f, 1, loc.clone().add(v), 257.0);
            }
        }
    }
			
			}, 1L, 2L).getTaskId();
		    EffectsLib.arraylist.put(idName, betatest2);
		} else {
			EffectsLib.stopEffect(idName);
		}
	}
	*/
	
	
	

}