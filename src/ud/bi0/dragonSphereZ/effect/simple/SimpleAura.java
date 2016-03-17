package ud.bi0.dragonSphereZ.effect.simple;

import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class SimpleAura extends ParticleEffect {
	
	Random random = new Random();
	double x;
	double y;
	double z;

	public SimpleAura(String idName, String particle, DynamicLocation center, List<Player> players) {
		super(idName, particle, center, players);
		pulseTick = 2; 
	}

	@Override
	public void onRun() {
		center.update();
		x = random.nextDouble();
		y = random.nextDouble();
		z = random.nextDouble();
		x = x * 4 - 2;
		y = y * 3;
		z = z * 4 - 2;
		Vector3d vector = new Vector3d(x,y,z);
		display(vector);
	}
}
