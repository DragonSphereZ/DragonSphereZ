package ud.bi0.dragonSphereZ.particles.effects;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.maths.shape.Ellipsoid;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.Effect;
import ud.bi0.dragonSphereZ.utils.EffectUtils;
import ud.bi0.dragonSphereZ.utils.ParticleEffect;

public class ComplexCircle extends Effect {
	
	
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
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, offset);
		init(radius, particleDensity, rainbowMode, enableRotation, axis);

	}
	public ComplexCircle(String idName, Object center, List<Player> players) {
		super(idName, center, players);
		init(1,1,false,false, new Vector3(0,0,0));
	}
	
	public void init(double radius, double particleDensity, boolean rainbowMode, boolean enableRotation, Vector3 axis) {
		this.radius = radius;
		this.particleDensity = particleDensity;
		this.rainbowMode = rainbowMode;
		this.enableRotation = enableRotation;
		this.axis = axis;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			int idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				// Holds the total number of steps per circle.
				final int steps = (int) (2 * Math.PI * radius * particleDensity);
				// Holds the current angle.
				double phi = 0;
				// Holds the angle between two following particles.
				final double stepPhi = 2 * Math.PI / steps;
				// Holds the displacement angle if enableRotation is true.
				double thetha = 0.5 * Math.PI;
				// Holds the change of the displacement angle if enableRotation is true.
				final double stepThetha = 0.01;
				final Ellipsoid circle = new Ellipsoid(radius);
				//Location location;				<-----changed to EffectUtils helper
				boolean setAxis = true;
				Location location;
				
				@Override
				public void run() {
					// Sets the axis of the circle.
					if (setAxis) {
						setAxis = false;
						circle.getBase().setW(axis.normalize().multiply(radius), true);
					}
					//if (center instanceof Entity) {			<-----changed to EffectUtils helper
					//	location = ((Entity) center).getLocation();
					//}
					//else if (center instanceof Location){
					//	location = (Location) center;
					//}
					location = EffectUtils.getLocation(center);
					circle.getPoint(thetha, phi).addToLocation(location);
					ParticleEffect.valueOf(particle).display(dataMat, dataID, players, location, visibleRange, rainbowMode, offset, speed, particleCount);
					
					if (rainbowMode) offset.setX(offset.getX() + 0.01);
					phi += stepPhi;
					thetha += stepThetha;
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(idName, idTask);
		}
		
	}
}