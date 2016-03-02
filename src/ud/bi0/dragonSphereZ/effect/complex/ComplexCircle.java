package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.shape.Ellipse;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexCircle extends ParticleEffect {
	
	protected double radius;
	protected double particleDensity;
	protected boolean enableRotation;
	protected Vector3d axis;
	
	public ComplexCircle(
		//super
		String idName,
		String particle,
		DynamicLocation center,
		List<Player> players,
		long delayTick,
		long pulseTick,
		int particleCount,
		Material dataMat,
		byte dataID,
		float speed,
		double visibleRange,

		//this
		Vector3d offset,
		Vector3d displacement,
		double radius,
		double particleDensity,
		boolean rainbowMode,
		boolean enableRotation,
		Vector3d axis)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
		init(radius, particleDensity, enableRotation, axis);

	}
	public ComplexCircle(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		//this.offset = new Vector3d(0,1,1);
		//this.displacement = new Vector3d();
		init(1, 1, false, new Vector3d(0,0,1));
	}
	
	public void init(double radius, double particleDensity, boolean enableRotation, Vector3d axis) {
		this.radius = radius;
		this.particleDensity = particleDensity;
		this.enableRotation = enableRotation;
		this.axis = axis.normalize();
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				Quaterniond rotation = new Quaterniond(TrigMath.PI / 200, 2, 1.7, 1.55);
				Ellipse circle = new Ellipse()
										.setBase(Base3d.MINECRAFT.adjust(new Vector3d(0,1,0), axis)) //Makes sure that the orientation is right and changes the axis of the circle if necessary.
										.setRadius(radius); //Sets the radius of the circle.
				double angle = 0;
				double stepAngle = TrigMath.TWO_PI / particleDensity;
				Vector3d v = new Vector3d();
				
				@Override
				public void run() {
					if (!center.hasMoved(pulseTick)) {
						center.update();
						v = circle.getPoint(angle); //Gets the next point on the circle.
						if (enableRotation)
							v = rotation.rotate(v); //Rotates the point.
							rotation = rotation.mul(rotation); //Prepares the next rotation (same as angle += angleStep).
						v = v.add(center.getVector3d()); //Translates the vector to the center position.
						v = v.add(displacement).add(0,1,0);	//Adds final translation to vector.
						if (rainbowMode)
							offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
						ComplexCircle.this.display(v);
						angle = GenericMath.wrapAngleRad(angle + stepAngle);
						
						Bukkit.getServer().broadcastMessage("[test] axis" + axis);
						
					} else center.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}