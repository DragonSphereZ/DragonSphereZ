package ud.bi0.dragonSphereZ.effect;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
//import org.bukkit.scheduler.BukkitRunnable;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.DragonSphereCore;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;


//public abstract class ParticleEffect extends BukkitRunnable {
public abstract class ParticleEffect {
	
	protected final String idName; 
	
	protected DynamicLocation center;
	@Nullable
	protected List<Player> players;
	
	protected String particle; //Default ParticleEffectUtils.limeglassparticle.name()
	protected int particleCount; //Default 1
	protected Material dataMat; //Default Material.DIRT
	protected byte dataID; //Default 0
	protected float speed; //Default 0
	protected double visibleRange; //Default 32
	protected boolean rainbowMode; //Default false
	protected Vector3d offset; //Default (0,0,0)
	protected Vector3d displacement; //Default (0,0,0)
	
	protected long delayTick; //Default 0
	protected long pulseTick; //Default 20
	
	protected int idTask = 0;
	protected int idTask2 = 2;
	
	
	protected final Plugin plugin = DragonSphereCore.dragonSphereCore;
	protected final ParticleEffectManager effectManager = DragonSphereCore.effectManager;
	
	//public ParticleEffect(String idName, String particle, DynamicLocation center, List<Player> players, long delayTick, long pulseTick, int particleCount, Material dataMat, byte dataID, float speed, double visibleRange, boolean rainbowMode, Vector3d offset, Vector3d displacement) {
	public ParticleEffect(int particleCount, String particle, Material dataMat, byte dataID, float speed, Vector3d offset, String idName, DynamicLocation center, List<Player> players, boolean rainbowMode, double visibleRange, Vector3d displacement, long delayTick, long pulseTick) {

		this.particleCount = particleCount;
		this.particle = particle;
		this.dataMat = dataMat;
		this.dataID = dataID;
		this.speed = speed;
		this.offset = offset;
		this.idName = new String(idName);
		this.center = center;
		this.players = players;
		this.rainbowMode = rainbowMode;
		this.visibleRange = visibleRange;
		this.displacement = displacement;
		this.delayTick = delayTick;
		this.pulseTick = pulseTick;
	}
	
	public ParticleEffect(String idName, DynamicLocation center, List<Player> players) {
		this(1, ParticleEffectUtils.limeglassparticle.name(), Material.DIRT, (byte) 0, 0F, new Vector3d(), idName, center, players, false, 32D, new Vector3d(), 0L, 20L);
	}
	
	public ParticleEffect(String idName, String particle, DynamicLocation center, List<Player> players) {
		this(1, particle, Material.DIRT, (byte) 0, 0F, new Vector3d(), idName, center, players, false, 32D, new Vector3d(), 0L, 20L);
	}
		
	public void setOffset(double x, double y, double z) {
		this.offset = new Vector3d(x,y,z);
	}
	
	public void setDisplacement(double x, double y, double z) {
		this.displacement = new Vector3d(x, y, z);
	}

	public void setParticle(String particle, Material dataMat, byte dataID) {
		this.particle = particle;
		this.dataMat = dataMat;
		this.dataID = dataID;
	}
	
	public String getIdName() {
		return idName;
	}
	
	public DynamicLocation getCenter() {
		return center;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public String getParticle() {
		return particle;
	}
	
	public int getParticleCount() {
		return particleCount;
	}
	
	public Material getDataMat() {
		return dataMat;
	}
	
	public byte getDataID() {
		return dataID;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public double getVisibleRange() {
		return visibleRange;
	}
	
	public boolean getRainbowMode() {
		return rainbowMode;
	}
	
	public Vector3d getOffset() {
		return offset;
	}
	
	public Vector3d getDisplacement() {
		return displacement;
	}
	
	public long getDelayTick() {
		return delayTick;
	}
	
	public long getPulseTick() {
		return pulseTick;
	}
	
	public int getIdTask() {
		return idTask;
	}

	public void stop() {
		effectManager.stopEffect(idName);
	}
	
	//@Override
 	//public void run() {
	//	cancel();
	//}
	
	public void display(Vector3d vector) {
		Vector3d finalVector = vector.add(this.getCenter().getVector3d()).add(this.getDisplacement());
		ParticleEffectUtils.valueOf(this.getParticle()).display(this, finalVector);
	}
	
	/**
	 * Using this method for the second or third particle in an effect
	 */
	public void display(String particle2, Vector3d offset2, float speed2, Material dataMat2, byte dataID2, Vector3d vector) {
		Vector3d v = vector.add(this.center.getVector3d()).add(this.displacement);
		ParticleEffectUtils.valueOf(particle2).display(this, center.getWorld(), v, offset2, speed2, dataMat2, dataID2);
	}
	
	public void display(List<Vector3d> vectors) {
		for (Vector3d vector : vectors) {
			display(vector);
		}
	}
	
	public abstract void onRun();
		
	public void start(ParticleEffect effect) {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				@Override
				public void run() {
					effect.onRun();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(effect);
		}
	}
	
	public void startUndelayed(ParticleEffect effect) {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				@Override
				public void run() {
					effect.onRun();
				}
			}, 0, pulseTick).getTaskId();
			effectManager.startEffect(effect);
		}
	}
	
	public void stopDelayed(ParticleEffect effect) {
			idTask2 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					effectManager.stopEffect(idName);
					Bukkit.getScheduler().cancelTask(idTask);
					Bukkit.getScheduler().cancelTask(idTask2);
				    }
			}, delayTick);
			effectManager.startEffect(effect);
	}
}
