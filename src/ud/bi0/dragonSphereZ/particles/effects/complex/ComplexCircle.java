package ud.bi0.dragonSphereZ.particles.effects.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ud.bi0.dragonSphereZ.maths.shape.Cylinder;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;
import ud.bi0.dragonSphereZ.utils.EffectUtils;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;
import ud.bi0.dragonSphereZ.utils.VectorUtils;

public class ComplexCircle extends ParticleEffect {
	
	
	protected double radius;
	protected double particleDensity;
	protected boolean rainbowMode;
	protected boolean enableRotation;
	protected Vector3 axis;
	
	public ComplexCircle(
		//super
		String idName,
		String particle,
		Object center,
		List<Player> players,
		long delayTick,
		long pulseTick,
		int particleCount,
		Material dataMat,
		byte dataID,
		float speed,
		double visibleRange,
		Vector3 offset,
		Vector3 displacement,
		//this
		double radius,
		double particleDensity,
		boolean rainbowMode,
		boolean enableRotation,
		Vector3 axis)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset);
		init(radius, particleDensity, enableRotation, axis);

	}
	public ComplexCircle(String idName, Object center, List<Player> players) {
		super(idName, center, players);
		init(1,1,false, new Vector3(0,0,1));
	}
	
	public void init(double radius, double particleDensity, boolean enableRotation, Vector3 axis) {
		this.radius = radius;
		this.particleDensity = particleDensity;
		this.enableRotation = enableRotation;
		this.axis = axis;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {

				double angularVelocityX = Math.PI / 200;
				double angularVelocityY = Math.PI / 170;
				double angularVelocityZ = Math.PI / 155;
				int step = 0;
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
					double inc = (Math.PI * 2) / particleDensity;
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

					ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, location.add(v), visibleRange, rainbowMode, offsetX, offsetY, offsetZ, speed, 1);
					//}
					step++;
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}