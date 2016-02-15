package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ExampleEffect extends Effect {
	

	public ExampleEffect(String idName, List<Location> locations, List<Player> players) {
		super(idName, locations, players);
		
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
