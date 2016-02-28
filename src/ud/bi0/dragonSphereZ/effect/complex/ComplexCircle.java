package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;
import ud.bi0.dragonSphereZ.utils.VectorUtils;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;

public class ComplexCircle extends ParticleEffect {
	
	protected double radius;
	protected double particleDensity;
	protected boolean enableRotation;
	protected Vector3d axis;
	//String particle;
	
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
		init(radius, particleDensity, rainbowMode, enableRotation, axis);

	}
	public ComplexCircle(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		this.offset = new Vector3d(0,1,1);
		this.displacement = new Vector3d();
		init(1 ,1 ,false ,false, new Vector3d(0,0,1));
	}
	
	public void init(double radius, double particleDensity, boolean rainbowMode, boolean enableRotation, Vector3d axis) {
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
						//double inc = ((TrigMath.TWO_PI) / particleDensity);
						double angle = step * (TrigMath.TWO_PI / particleDensity);
						angle = GenericMath.wrapAngleRad(angle);
						Vector3d v = new Vector3d(TrigMath.cos(angle) * radius, 0, TrigMath.sin(angle) * radius);
						VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
						if (enableRotation)
							VectorUtils.rotateVector(v, angularVelocityX * step, angularVelocityY * step, angularVelocityZ * step);
						if (rainbowMode)
							ParticleEffectUtils.simpleRainbowHelper(offset, particle);
						v = v.add(location.getVector3d());
						ComplexCircle.this.display(v);
						step++;
					} else location.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}