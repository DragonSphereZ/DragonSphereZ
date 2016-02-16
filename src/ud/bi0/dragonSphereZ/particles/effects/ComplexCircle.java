package ud.bi0.dragonSphereZ.particles.effects;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.maths.shape.Cylinder;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.EffectUtils;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;

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
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, offset);
		init(radius, particleDensity, rainbowMode, enableRotation, axis);

	}
	public ComplexCircle(String idName, Object center, List<Player> players) {
		super(idName, center, players);
		init(1,1,false,false, new Vector3(0,0,1));
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
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				// Holds the total number of steps per circle.
				final int steps = (int) (2 * Math.PI * radius * particleDensity);
				// Holds the current angle.
				double phi = 0;
				// Holds the angle between two following particles.
				final double stepPhi = 2 * Math.PI / steps;
				//double height = 0;
				//double stepHeight = 2 * Math.PI / (4 * steps);
				// Holds the displacement angle if enableRotation is true.
				// Holds the change of the displacement angle if enableRotation is true.
				final Cylinder circle = new Cylinder(new Vector3(0,0,0), radius, radius);
				//Location location;				<-----changed to EffectUtils helper
				boolean setAxis = true;
				Location location;
				Vector3 vector = new Vector3(0,0,0);
				
				@Override
				public void run() {
					// Sets the axis of the circle.
					if (setAxis) {
						setAxis = false;
						circle.getBase().setNormal(axis);;
					}
					//if (center instanceof Entity) {			<-----changed to EffectUtils helper
					//	location = ((Entity) center).getLocation();
					//}
					//else if (center instanceof Location){
					//	location = (Location) center;
					//}
					location = EffectUtils.getLocation2(center);
					//vector.copy(circle.getPoint(1,phi,Math.abs(Math.sin(height))*2));
					vector.copy(circle.getPoint(1,phi,0));
					location.add(vector.getY(), vector.getZ(), vector.getX());
					ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, location, visibleRange, rainbowMode, offset, speed, particleCount);
					
					if (rainbowMode) offset.setX(offset.getX() + 0.01);
					if (enableRotation) {
						circle.getBase().setNormal(circle.getBase().getNormal().rotXYZ(1, 1, 1));
						//height += stepHeight;
					}
					phi += stepPhi;
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}