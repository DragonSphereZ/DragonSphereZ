package ud.bi0.dragonSphereZ.effect.simple;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;

public class SimpleAura extends ParticleEffect {
	

	public SimpleAura(String idName, String particle, DynamicLocation center, List<Player> players) {
		super(idName, particle, center, players);
		pulseTick = 2; 
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				Vector3d vector = new Vector3d();
				Random random = new Random();
				double x = 0;
				double y = 0;
				double z = 0;
				
				@Override
				public void run() {
					center.update();
					vector = center.getVector3d();
					x = random.nextDouble();
					y = random.nextDouble();
					z = random.nextDouble();
					x = x * 4 - 2;
					y = y * 3;
					z = z * 4 - 2;
					SimpleAura.this.display(vector);
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}
