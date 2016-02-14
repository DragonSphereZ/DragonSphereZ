package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class Effect extends BukkitRunnable {
	
	
	public String idName;
	public Material dataMat;
	public byte dataID;
	
	@Nullable
	public List<Location> locations;
	@Nullable
	public List<Entity> entities;
	@Nullable
	public List<Player> players;
	public long delayTick = 0;
	public long pulseTick = 20;
	
	public String particle = "limeglassparticle";
	public int particleCount = 1;
	public float speed = 0;
	public boolean dynamic = false;
	public double visibleRange = 32;
	public double offsetX = 0;
	public double offsetY = 0;
	public double offsetZ = 0;
	
	public boolean cancel = false;
	
	protected Runnable task;

	public Effect(String idName, String particle, Material dataMat, byte dataID, List<Location> locations, List<Entity> entities, List<Player> players) {
		init(idName, particle, dataMat, dataID, locations, entities, players);
	}
	
	private void init (String idName, String particle, Material dataMat, byte dataID, List<Location> locations, List<Entity> entities, List<Player> players) {
		// Check if effect already exists.
		this.idName = idName;
		this.particle = particle;
		this.dataMat = dataMat;
		this.dataID = dataID;
		if (locations != null) {
			this.locations = locations;
			this.dynamic = false;
		}
		if (entities != null) {
			this.entities = entities;
			this.dynamic = true;
		}
		if (players != null) {
			this.players = players;
		}
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
	
	@Override
 	public void run() {
		cancel();
	}
	
	public void start() {
	}
}
