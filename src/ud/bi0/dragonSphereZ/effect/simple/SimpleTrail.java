package ud.bi0.dragonSphereZ.effect.simple;

import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class SimpleTrail extends ParticleEffect {
	
	Vector3d vector = new Vector3d();
	Random random = new Random();
	double x = 0;
	double y = 0;
	double z = 0;

	public SimpleTrail(String idName, String particle, DynamicLocation center, List<Player> players) {
		super(idName, particle, center, players);
		this.particleCount = 1;
		this.pulseTick = 2;
	}
	
	@Override
	public void onRun() {
		if (center.needsUpdate(pulseTick)) {
			vector = center.getDisplacement(pulseTick).normalize().mul(-0.3);
			center.update();
			x = random.nextDouble() * 0.5 - 0.25;
			y = random.nextDouble() * 0.5 - 0.25;
			z = random.nextDouble() * 0.25;
			vector = vector.add(x, y, z).add(center.getVector3d());
			SimpleTrail.this.display(vector);
		} else center.update();
	}
}
