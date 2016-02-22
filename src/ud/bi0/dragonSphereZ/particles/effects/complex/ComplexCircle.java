package ud.bi0.dragonSphereZ.particles.effects.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.maths.shape.Cylinder;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;

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
				DynamicLocation location = DynamicLocation.init(center);
				Vector3 vector = new Vector3(0,0,0);
				
				@Override
				public void run() {
					// Sets the axis of the circle.
					if (setAxis) {
						setAxis = false;
						circle.getBase().setNormal(axis);;
					}

					location.update();
					vector.copy(circle.getPoint(1,phi,0));
					location.add(vector.getY(), vector.getZ(), vector.getX());
					location.display(ComplexCircle.this);
					if (rainbowMode) offset.setX(offset.getX() + 0.01);
					if (enableRotation) {
						circle.getBase().setNormal(circle.getBase().getNormal().rotXYZ(1, 1, 1));
					}
					phi += stepPhi;
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}