package ud.bi0.dragonSphereZ.effect;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.DragonSphereCore;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;


public class ParticleEffect extends BukkitRunnable {
	
	
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
	
	
	@SuppressWarnings("unused")
	protected final Plugin plugin = DragonSphereCore.dragonSphereCore;
	protected final NewParticleEffectManager effectManager = DragonSphereCore.newEffectManager;
	
	public ParticleEffect(String idName, String particle, DynamicLocation center, List<Player> players, long delayTick, long pulseTick, int particleCount, Material dataMat, byte dataID, float speed, double visibleRange, boolean rainbowMode, Vector3d offset, Vector3d displacement) {
		this.idName = new String(idName);
		this.center = center;
		this.players = players;
		this.particle = particle;
		this.particleCount = particleCount;
		this.dataMat = dataMat;
		this.dataID = dataID;
		this.speed = speed;
		this.visibleRange = visibleRange;
		this.rainbowMode = rainbowMode;
		this.offset = offset;
		this.displacement = displacement;
		this.delayTick = delayTick;
		this.pulseTick = pulseTick;
	}
	
	public ParticleEffect(String idName, DynamicLocation center, List<Player> players) {
		this(idName, ParticleEffectUtils.limeglassparticle.name(), center, players, 0L, 20L, 1, Material.DIRT, (byte) 0, 0F, 32D, false, new Vector3d(), new Vector3d());
	}
	
	public ParticleEffect(String idName, String particle, DynamicLocation center, List<Player> players) {
		this(idName, particle, center, players, 0L, 20L, 1, Material.DIRT, (byte) 0, 0F, 32D, false, new Vector3d(), new Vector3d());
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
	
	@Override
 	public void run() {
		cancel();
	}
	
	public void start() {
	}
	
	public void display(Vector3d vector) {
		ParticleEffectUtils.valueOf(particle).display(this, center.getWorld(), vector);
	}
	
	public void display(List<Vector3d> vectors) {
		for (Vector3d vector : vectors) {
			display(vector);
		}
	}
}
