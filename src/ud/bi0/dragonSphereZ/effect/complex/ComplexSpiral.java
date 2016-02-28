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

public class ComplexSpiral extends ParticleEffect {
	protected double radius;
	protected double circleDensity;
	protected float height; 
	protected float effectMod;	
	protected boolean clockwise;
	protected boolean scan;
	protected Vector3d axis;
	
	public ComplexSpiral(
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
		boolean rainbowMode,
		//this
		boolean scan,
		Vector3d offset,
		Vector3d displacement,		
		double radius,
		double circleDensity,
		float height,
		float effectMod,
		boolean clockwise,
		Vector3d axis)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
		init(radius, circleDensity, height, effectMod, scan, clockwise, axis);

	}
	public ComplexSpiral(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		init(1, 1, (float) 2, (float) 0.1, false, false, new Vector3d(0,0,1));
	}
	
	public void init(double radius, double circleDensity, float height, float effectMod, boolean scan, boolean clockwise, Vector3d axis) {
		this.radius = radius;
		this.circleDensity = circleDensity;
		this.height = height;
		this.effectMod = effectMod;
		this.scan = scan;
		this.clockwise = clockwise;
		this.axis = axis;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				float i;
				boolean up = true;
				Vector3d v;
				int step = 0;
				DynamicLocation location = DynamicLocation.init(center);
				@Override
				public void run() {
					if (!location.hasMoved(pulseTick)) {
						location.update();
						location.add(displacement.getX(), displacement.getY(), displacement.getZ());
						

						double angle = ( TrigMath.TWO_PI / circleDensity ) * step;
						angle = GenericMath.wrapAngleRad(angle);
						double y = i;
			            if (clockwise == false)
				            v = new Vector3d(TrigMath.sin(angle) * radius, y, TrigMath.cos(angle) * radius);
			            if (clockwise == true)
			                v = new Vector3d(TrigMath.cos(angle) * radius, y, TrigMath.sin(angle) * radius);

						
						VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
						if (rainbowMode)
							ParticleEffectUtils.simpleRainbowHelper(offset, particle);
						v = v.add(location.getVector3d());
						ComplexSpiral.this.display(v);
						//location.display(ComplexSpiral.this);
						//ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, location.add(v), visibleRange, rainbowMode, offset, speed, 1);
						//location.subtract(v);
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
							i += effectMod / 2;
						if (up == false)
							i -= effectMod / 2;
					} else location.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}