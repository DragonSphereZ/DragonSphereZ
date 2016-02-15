package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import ud.bi0.dragonSphereZ.DragonSphereCore;
import ud.bi0.dragonSphereZ.utils.ParticleEffect;


public class Effect extends BukkitRunnable {
	
	
	public String idName;
	
	@Nullable
	public List<?> locations;
	@Nullable
	public List<Player> players;
	
	public String particle;
	public int particleCount;
	public Material dataMat;
	public byte dataID;
	public float speed;
	public double visibleRange;
	public double offsetX;
	public double offsetY;
	public double offsetZ;
	
	public long delayTick;
	public long pulseTick;
		
	protected final Plugin plugin = DragonSphereCore.dragonSphereCore;
	protected final EffectManager effectManager = DragonSphereCore.effectManager;
	
	public Effect(String idName, String particle, List<?> locations, List<Player> players, int particleCount, Material dataMat, byte dataID, float speed, double visibleRange, double offsetX, double offsetY, double offsetZ) {
		init(idName, particle, locations, players, particleCount, dataMat, dataID, speed, visibleRange, offsetX, offsetY, offsetZ);
	}
	
	public Effect(String idName, List<?> locations, List<Player> players) {
		init(idName, ParticleEffect.limeglassparticle.getName(), locations, players, 1, Material.DIRT, (byte) 0, 0F, 32D, 0D, 0D, 0D);
	}
	
	private void init (String idName, String particle, List<?> locations, List<Player> players, int particleCount, Material dataMat, byte dataID, float speed, double visibleRange, double offsetX, double offsetY, double offsetZ) {

		this.idName = idName;
		this.particle = particle;
		this.locations = locations;
		this.players = players;
		this.particleCount = particleCount;
		this.dataMat = dataMat;
		this.dataID = dataID;
		this.speed = speed;
		this.visibleRange = visibleRange;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}
		
	public void setOffset(double x, double y, double z) {
		this.offsetX = x;
		this.offsetY = y;
		this.offsetZ = z;
	}

	public void setParticle(String particle, Material dataMat, byte dataID) {
		this.particle = particle;
		this.dataMat = dataMat;
		this.dataID = dataID;
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
