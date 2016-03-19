package ud.bi0.dragonSphereZ.effect.simple;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.shape.Cylinder;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.FlowPoweredHook;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;
import ud.bi0.dragonSphereZ.effect.ParticleEffect;

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
	
	Vector3d vectorYin = new Vector3d();
	Vector3d vectorYang = new Vector3d();
	Location location;
	Cylinder circle;
	double height = 2;
	double stepPhi;
	double phi = 0;
	Vector3d black = new Vector3d();
	Vector3d white = new Vector3d(); 
	
	
	
	public SimpleYinYang(String idName, String particle, DynamicLocation center, List<Player> players, boolean clockwise) {
		super(idName, particle, center, players, 0, 2, 1, Material.DIRT,(byte) 0, 0, 32, false, new Vector3d(0,0,0), new Vector3d(0,0,0));
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
		
		
		white = color ? new Vector3d(254, 254, 254) : new Vector3d(0,0,0);
		location = new Location(center.getWorld(), 0, 0, 0);
		circle = new Cylinder();
		circle.setRadius(0.5);
		stepPhi = clockwise ? -angle : angle;
	}
	
	@Override
	public void onRun() {
		if (!center.isDynamic() || !center.needsUpdate(pulseTick)) {
			center.update();
			vectorYin = center.getVector3d().add(circle.getPoint(phi, height)).add(this.getDisplacement());
			vectorYang = center.getVector3d().add(circle.getPoint(-phi, height)).add(this.getDisplacement());
			location = FlowPoweredHook.Location(vectorYin, center.getWorld());
			ParticleEffectUtils.valueOf(yin).display(idName, matYin, dataYin, players, location, visibleRange, false, white, speed, countYin);
			location = FlowPoweredHook.Location(vectorYang, center.getWorld());
			ParticleEffectUtils.valueOf(yang).display(idName, matYang, dataYang, players, location, visibleRange, false, black, speed, countYang);
			phi = GenericMath.wrapAngleRad(phi + stepPhi);
		} else center.update();
	}
}
