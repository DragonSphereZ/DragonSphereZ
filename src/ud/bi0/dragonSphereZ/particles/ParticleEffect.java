package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import ud.bi0.dragonSphereZ.DragonSphereCore;
//import ud.bi0.dragonSphereZ.maths.vector.Vector3;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;


public class ParticleEffect extends BukkitRunnable {
	
	
	public String idName; 
	
	@Nullable
	public Object center;
	@Nullable
	public List<Player> players;
	
	public String particle; //Default "limeglassparticle"
	public int particleCount; //Default 1
	public Material dataMat; //Default Material.DIRT
	public byte dataID; //Default 0
	public float speed; //Default 0
	public double visibleRange; //Default 32
	public boolean rainbowMode; //Default false
	public Vector offset; //Default (0,0,0)
	public Vector displacement; //Default (0,0,0)
	
	public long delayTick; //Default 0
	public long pulseTick; //Default 20
	
	protected int idTask = 0;
	protected final Plugin plugin = DragonSphereCore.dragonSphereCore;
	protected final ParticleEffectManager effectManager = DragonSphereCore.effectManager;
	
	public ParticleEffect(String idName, String particle, Object center, List<Player> players, long delayTick, long pulseTick, int particleCount, Material dataMat, byte dataID, float speed, double visibleRange, boolean rainbowMode, Vector offset) {
		init(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset);
	}
	
	public ParticleEffect(String idName, Object center, List<Player> players) {
		init(idName, ParticleEffectUtils.limeglassparticle.getName(), center, players, 0, 20, 1, Material.DIRT, (byte) 0, 0F, 32D, false, new Vector(0,0,0));
	}
	
	public ParticleEffect(String idName, String particle, Object center, List<Player> players) {
		init(idName, particle, center, players, 0, 20, 1, Material.DIRT, (byte) 0, 0F, 32D, false, new Vector(0,0,0));
	}
	
	private void init (
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
			Vector offset) 
	{

		this.idName = idName;
		this.particle = particle;
		this.delayTick = delayTick;
		this.pulseTick = pulseTick;
		this.center = center;
		this.players = players;
		this.particleCount = particleCount;
		this.dataMat = dataMat;
		this.dataID = dataID;
		this.speed = speed;
		this.visibleRange = visibleRange;
		this.rainbowMode = rainbowMode;
		this.offset = offset;
	}
		
	public void setOffset(float x, float y, float z) {
		//offset.setXYZ(x, y, z);
		offset.setX(x);
		offset.setY(y);
		offset.setZ(z);
	}

	public void setParticle(String particle, Material dataMat, byte dataID) {
		this.particle = particle;
		this.dataMat = dataMat;
		this.dataID = dataID;
	}
	
	public int getID() {
		return idTask;
	}
	
	public String getNameID() {
		return idName;
	}
	
	public void stop() {
		effectManager.stopEffect(idName);
	}
	
	@Override
 	public void run() {
		cancel();
	}
	
	public void start() {
	}
}
