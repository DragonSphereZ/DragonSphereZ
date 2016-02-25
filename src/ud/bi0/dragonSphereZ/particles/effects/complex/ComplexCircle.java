package ud.bi0.dragonSphereZ.particles.effects.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

//import ud.bi0.dragonSphereZ.maths.vector.Vector3;
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
				double angularVelocityX = Math.PI / 200;
				double angularVelocityY = Math.PI / 170;
				double angularVelocityZ = Math.PI / 155;
				int step = 0;
				//public float counter;
				//Location location;// = player.getLocation().clone();
				DynamicLocation location = DynamicLocation.init(center);
				//Vector v = new Vector();
				//Vector v = new Vector(0D,0D,0D);
				//Vector test = new Vector(1D,0D,0D);;
				@Override
				public void run() {
					if (!location.isDynamic() || !location.needsUpdate(pulseTick)) {
						//location = EffectUtils.getLocation2(center);
						location.update();
						//location.add(0D, 1D, 0D);
						location.add(displacement.getX(), 1 + displacement.getY(), displacement.getZ());
						double inc = (Math.PI * 2) / particleDensity;
						double angle = step * inc;
						Vector v = new Vector(Math.cos(angle) * radius, 0, Math.sin(angle) * radius);
						VectorUtils.rotateVector(v, axis.getX(), axis.getY(), axis.getZ());
						if (enableRotation)
							VectorUtils.rotateVector(v, angularVelocityX * step, angularVelocityY * step, angularVelocityZ * step);
						//if (rainbowMode) offset.setX(offset.getX() + 1);//TODO Get this working with note particles as wellxD
						if (rainbowMode)
							Bukkit.getServer().broadcastMessage("[rainbow true]");
							//offset.setX(offset.getX() + 1);
							if (particle == ParticleEffectUtils.note.getName()){
								Bukkit.getServer().broadcastMessage("[particle note]");
								if (offset.getX() > 24){
									offset.setX(0);
									Bukkit.getServer().broadcastMessage("[ifGreater]");
								}
								offset.setX(offset.getX() + 1);
							}
							if (particle == "redstone" || particle == "mobspell" || particle == "mobspellambient"){
								if (offset.getX() >= 100){
									offset.setX(0);
								}
								offset.setX(offset.getX() + 1);
							}
							//offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
							//ParticleEffectUtils.simpleRainbowHelper(offset, particle);
							Bukkit.getServer().broadcastMessage("[input name] " + particle);
							Bukkit.getServer().broadcastMessage("[note.getName] " + ParticleEffectUtils.note.getName());
						Bukkit.getServer().broadcastMessage("[offset x] " + offset.getX());
						//location.add(v.getZ(), v.getX(), v.getY());
						//location.add(v);
						//location.display(ComplexCircle.this);
						ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, location.add(v), visibleRange, rainbowMode, offset, speed, 1);
						location.subtract(v);
						//}
						step++;
					} else location.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}