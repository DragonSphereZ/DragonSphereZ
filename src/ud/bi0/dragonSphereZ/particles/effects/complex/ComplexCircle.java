package ud.bi0.dragonSphereZ.particles.effects.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.flowpowered.math.TrigMath;

import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;
import ud.bi0.dragonSphereZ.utils.VectorUtils;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;

public class ComplexCircle extends ParticleEffect {
	protected double radius;
	protected double particleDensity;
	protected boolean rainbowMode;
	protected boolean enableRotation;
	protected Vector axis;
	Vector offset;
	//String particle;
	
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

		//this
		Vector offset,
		Vector displacement,		
		double radius,
		double particleDensity,
		boolean rainbowMode,
		boolean enableRotation,
		Vector axis)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset);
		init(radius, particleDensity, rainbowMode, enableRotation, axis, offset, displacement);

	}
	public ComplexCircle(String idName, Object center, List<Player> players) {
		super(idName, center, players);
		init(1 ,1 ,false ,false, new Vector(0,0,1), new Vector(0,1,1), new Vector(0,0,0));
	}
	
	public void init(double radius, double particleDensity, boolean rainbowMode, boolean enableRotation, Vector axis, Vector offset, Vector displacement) {
		this.radius = radius;
		this.particleDensity = particleDensity;
		this.rainbowMode = rainbowMode;
		this.enableRotation = enableRotation;
		this.axis = axis;
		this.offset = offset;
		this.displacement = displacement;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				double angularVelocityX = TrigMath.PI / 200;
				double angularVelocityY = TrigMath.PI / 170;
				double angularVelocityZ = TrigMath.PI / 155;
				int step = 0;
				DynamicLocation location = DynamicLocation.init(center);
				@Override
				public void run() {
					if (!location.hasMoved(pulseTick)) {
						location.update();
						location.add(displacement.getX(), 1 + displacement.getY(), displacement.getZ());
						double inc = (TrigMath.TWO_PI) / particleDensity;
						double angle = step * inc;
						Vector v = new Vector(TrigMath.cos(angle) * radius, 0, TrigMath.sin(angle) * radius);
						VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
						if (enableRotation)
							VectorUtils.rotateVector(v, angularVelocityX * step, angularVelocityY * step, angularVelocityZ * step);
						if (rainbowMode)
							ParticleEffectUtils.simpleRainbowHelper(offset, particle);
						location.add(v);
						location.display(ComplexCircle.this);
						//ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, location.add(v), visibleRange, rainbowMode, offset, speed, 1);
						location.subtract(v);
						step++;
					} else location.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}