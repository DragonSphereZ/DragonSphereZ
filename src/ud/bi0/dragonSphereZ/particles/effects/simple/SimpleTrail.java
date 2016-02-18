package ud.bi0.dragonSphereZ.particles.effects.simple;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.maths.vector.Vector3;
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
						location.update();
						vector.copy(location.getEntity().getVelocity());
						vector.normalize().multiply(-0.3); // Gets location 0.3 horizontally behind and 0.3 above player
						x = random.nextDouble() * 0.3 - 0.3;
						y = random.nextDouble() * 0.3 - 0.3;
						z = random.nextDouble() * 0.3 - 0.3;
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
