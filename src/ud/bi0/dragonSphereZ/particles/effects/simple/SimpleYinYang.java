package ud.bi0.dragonSphereZ.particles.effects.simple;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.maths.vector.Rotator3;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;

public class SimpleYinYang extends ParticleEffect {
	
	boolean clockwise;
	boolean color = false;
	String yin;
	String yang;
	Material matYin = Material.DIRT;
	Material matYang = Material.DIRT;
	byte dataYin = (byte) 0;
	byte dataYang = (byte) 0;
	int countYin = 1;
	int countYang = 1;
	double angle = 0.3;
	
	public SimpleYinYang(String idName, String particle, Object center, List<Player> players, boolean clockwise) {
		super(idName, particle, center, players, 0, 2, 1, Material.DIRT,(byte) 0, 0, 32, false, new Vector3(0,0,0));
		this.clockwise = clockwise;
		particle.toLowerCase();
		switch(particle) {
		case "limeglassparticle":
		case "happyvillager":
		case "witchspell":
			init("limeglassparticle", "witchspell");
			angle = 0.2;
			break;
		case "mobspell":
			init("mobspell", 20);
			color = true;
			break;
		case "mobspellambient":
			init("mobspellambient", 3);
			color = true;
			break;
		case "redstone":
			init("redstone", 4);
			angle = 0.2;
			color = true;
			break;
		case "blockdust":
			matYin = Material.LAVA;
			matYang = Material.WATER;
			yin = "blockdust";
			yang = "blockdust";
			break;
		default:
			init("lavadrip", "waterdrip", 3);
			angle = 0.1;
		}
	}
	
	@SuppressWarnings("unused")
	private void init(String yin) {
		init(yin, 1);
	}
	
	private void init(String yin, int count) {
		init(yin, yin, count);
	}
	private void init(String yin, String yang) {
		init(yin, yang, Material.DIRT, Material.DIRT, 1, 1);
	}
	
	private void init(String yin, String yang, int count) {
		init(yin, yang, Material.DIRT, Material.DIRT, count, count);
	}
	private void init(String yin, String yang, Material matYin, Material matYang, int countYin, int countYang) {
		this.yin = yin;
		this.yang = yang;
		this.matYin = matYin;
		this.matYang = matYang;
		this.countYin = countYin;
		this.countYang = countYang;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				Vector3 vectorYin = new Vector3(0.5, 0.5, 2);
				Vector3 vectorYang = new Vector3(0.5, 0.5, 2);
				DynamicLocation location = DynamicLocation.init(center);
				final double stepPhi = clockwise ? -angle : angle;
				final Rotator3 rotatorYin = new Rotator3(new Vector3(0,0,1), stepPhi);
				final Rotator3 rotatorYang = new Rotator3(new Vector3(0,0,1), -stepPhi);
				final Vector3 black = new Vector3(0, 0, 0);
				final Vector3 white = color ? new Vector3(254, 254, 254) : new Vector3(0,0,0);
				
				@Override
				public void run() {
					if (!location.isDynamic() || !location.needsUpdate(pulseTick)) {
						location.update();
						vectorYin.rot(rotatorYin);
						vectorYang.rot(rotatorYang);
						vectorYin.addTo(location);
						ParticleEffectUtils.valueOf(yin).display(matYin, dataYin, players, location, visibleRange, false, white, speed, countYin);
						vectorYin.subtractFrom(location);
						vectorYang.addTo(location);
						ParticleEffectUtils.valueOf(yang).display(matYang, dataYang, players, location, visibleRange, false, black, speed, countYang);
						vectorYang.subtractFrom(location);
					} else location.update();
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}
