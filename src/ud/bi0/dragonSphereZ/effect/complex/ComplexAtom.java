package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;
import ud.bi0.dragonSphereZ.util.RandomUtils;
import ud.bi0.dragonSphereZ.util.VectorUtils;

public class ComplexAtom extends ParticleEffect {
	protected double radius;
	protected double circleDensity;
	protected float height; 
	protected float effectMod;	
	protected boolean clockwise;
	protected boolean scan;
	protected Vector axis;
	Vector offset;
	String particle2;
	
	public ComplexAtom(
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
		boolean rainbowMode,
		//this
		boolean scan,
		Vector offset,
		Vector displacement,		
		double radius,
		double circleDensity,
		float height,
		float effectMod,
		boolean clockwise,
		Vector axis)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset);
		init(radius, circleDensity, height, effectMod, scan, clockwise, axis, offset, displacement);

	}
	public ComplexAtom(String idName, Object center, List<Player> players) {
		super(idName, center, players);
		init(1, 1, (float) 6, (float) 0.5, false, false, new Vector(0,0,1), new Vector(0,0,0), new Vector(0,0,0));
	}
	
	public void init(double radius, double circleDensity, float height, float effectMod, boolean scan, boolean clockwise, Vector axis, Vector offset, Vector displacement) {
		this.radius = radius;
		this.circleDensity = circleDensity;
		this.height = height;
		this.effectMod = effectMod;
		this.scan = scan;
		this.clockwise = clockwise;
		this.axis = axis;
		this.offset = offset;
		this.displacement = displacement;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				public double angularVelocity = TrigMath.PI / 40d;
				public double autoYRotationDensity = TrigMath.PI / 200d;
				int step = 0;
				DynamicLocation location = DynamicLocation.init(center);
				@Override
				public void run() {
					if (!location.hasMoved(pulseTick)) {
						location.update();
						location.add(displacement.getX(), 3 + displacement.getY(), displacement.getZ());
						if (rainbowMode)
							ParticleEffectUtils.simpleRainbowHelper(offset, particle);
						//TODO NOT DONE YET xD
						for (int i = 0; i < innerParticles; i++) {
							Vector v = RandomUtils.getRandomVector().multiply(0.5 * innerRadius);
							ParticleEffectUtils.valueOf(particle2).display(dataMat, dataID, players, location.add(v), visibleRange, rainbowMode, offset, speed, 1);							location.subtract(v);
						}
						for (int i = 0; i < orbitParticles; i++) {
							//double angle = step * angularVelocity;
							double angle = GenericMath.wrapAngleRad(step * angularVelocity);
							for (int j = 0; j < orbitalCount; j++) {
								
								double xRotation = (TrigMath.PI / orbitalCount) * j;
								
								Vector v = new Vector(TrigMath.sin(angle) * ( 0.5 + innerRadius ), 0, TrigMath.cos(angle) * ( 0.5 + innerRadius ));

								VectorUtils.rotateAroundAxisX(v, xRotation);
								VectorUtils.rotateAroundAxisY(v, manualYRotation);
								if (autoYRotation)
									VectorUtils.rotateAroundAxisY(v, autoYRotationDensity);
								
								//location.add(v);
								//location.display(ComplexAtom.this);
								ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, location.add(v), visibleRange, rainbowMode, offset, speed, 1);
								location.subtract(v);
							}
							step++;
						}
					} else location.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}