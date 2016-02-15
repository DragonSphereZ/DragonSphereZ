package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExampleEffect extends Effect {
	

	public ExampleEffect(String idName, Object center, List<Player> players) {
		super(idName, center, players);
		
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			Integer idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				@Override
				public void run() {
					cancel();
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(idName, idTask);
		}
		
	}
}
