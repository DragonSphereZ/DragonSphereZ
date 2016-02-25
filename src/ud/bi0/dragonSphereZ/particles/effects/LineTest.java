package ud.bi0.dragonSphereZ.particles.effects;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.oldmath.shape.Line;
import ud.bi0.dragonSphereZ.oldmath.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.ParticleEffectUtils;

public class LineTest extends ParticleEffect {
	

	public LineTest(String idName, Object center, List<Player> players) {
		super(idName, center, players);
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				Location location;
				Line line = new Line(new Vector3(0,0,0), new Vector3(0,0,0));
				Vector3 origin = new Vector3(0,0,0);
				List<Vector3> points = new ArrayList<Vector3>();
				
				@Override
				public void run() {
					if (center instanceof Entity) {
						location = ((Entity) center).getLocation();
						origin.copy(location);
					}
					else if (center instanceof Location){
						location = new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ());
						origin.copy(location);
					}
					
					for (Player player : players) {
						line.setOrigin(origin);
						line.setDirection(new Vector3(player.getLocation()).subtract(origin));
						points = line.render();
						for (Vector3 point : points) {
							ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, point.toLocation(location), visibleRange, false, offset, speed, particleCount);
						}
					}
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}

