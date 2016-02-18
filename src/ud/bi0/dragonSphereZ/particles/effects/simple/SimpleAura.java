package ud.bi0.dragonSphereZ.particles.effects.simple;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;

public class SimpleAura extends ParticleEffect {
	

	public SimpleAura(String idName, String particle, Object center, List<Player> players) {
		super(idName, particle, center, players);
		pulseTick = 2; 
		
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				DynamicLocation location = DynamicLocation.init(center);
				Random random = new Random();
				double x = 0;
				double y = 0;
				double z = 0;
				
				@Override
				public void run() {
					location.update();
					x = random.nextDouble();
					y = random.nextDouble();
					z = random.nextDouble();
					x = x * 4 - 2;
					y = y * 3;
					z = z * 4 - 2;
					location.add(x, y, z);
					location.display(SimpleAura.this);
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}
