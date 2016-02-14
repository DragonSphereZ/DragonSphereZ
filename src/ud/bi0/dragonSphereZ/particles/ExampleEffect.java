package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.DragonSphereCore;

public class ExampleEffect extends Effect {

	public ExampleEffect(String idName, String particle, Material dataMat, byte dataID, List<Location> locations,
			List<Entity> entities, List<Player> players) {
		super(idName, particle, dataMat, dataID, locations, entities, players);
	}
	
	@Override
	public void start() {
		if (true)  {	//TODO check if idName is already in effect list: if (!EffectsLib.arraylist.containsKey(idName))
			int circle = Bukkit.getServer().getScheduler().runTaskTimer(DragonSphereCore.dragonSphereCore, new Runnable() {
				@Override
				public void run() {
				}
			}, this.delayTick, this.pulseTick).getTaskId();
		}
		//TODO Add idName and taskID to effect list: EffectsLib.arraylist.put(idName, circle);
	}
	

	
	
	

}
