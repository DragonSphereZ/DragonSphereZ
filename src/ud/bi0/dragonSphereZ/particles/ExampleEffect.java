package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.utils.DynamicLocation;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;;

public class ExampleEffect extends ParticleEffect {
	

	public ExampleEffect(String idName, String particle, Object center, List<Player> players) {
		super(idName, particle, center, players);
		
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				@SuppressWarnings("unused")
				DynamicLocation location = DynamicLocation.init(center);
				
				@Override
				public void run() {
					cancel();
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}
