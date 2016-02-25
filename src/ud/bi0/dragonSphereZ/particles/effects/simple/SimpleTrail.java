package ud.bi0.dragonSphereZ.particles.effects.simple;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.oldmath.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;

public class SimpleTrail extends ParticleEffect {
	

	public SimpleTrail(String idName, String particle, Object center, List<Player> players) {
		super(idName, particle, center, players);
		this.particleCount = 1;
		this.pulseTick = 2;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				DynamicLocation location = DynamicLocation.init(center);
				Vector3 vector = new Vector3();
				Random random = new Random();
				double x = 0;
				double y = 0;
				double z = 0;
				
				@Override
				public void run() {
					if (location.needsUpdate(pulseTick)) {
						location.displacement(vector, pulseTick);
						vector.normalize().multiply(-0.3);
						location.update();
						x = random.nextDouble() * 0.5 - 0.25;
						y = random.nextDouble() * 0.5 - 0.25;
						z = random.nextDouble() * 0.25;
						vector.add(x, y, z);
						vector.addTo(location);
						location.display(SimpleTrail.this);
						vector.subtractFrom(location);
					} else location.update();
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}
