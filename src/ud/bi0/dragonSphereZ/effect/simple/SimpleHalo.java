package ud.bi0.dragonSphereZ.effect.simple;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.shape.Cylinder;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;

public class SimpleHalo extends ParticleEffect {
	
	boolean clockwise;
	
	public SimpleHalo(String idName, String particle, DynamicLocation center, List<Player> players, boolean clockwise) {
		super(idName, particle, center, players);
		this.pulseTick = 2;
		this.clockwise = clockwise;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				Cylinder circle = new Cylinder().setBase(Base3d.MINECRAFT).setRadius(0.5);
				Vector3d vector = new Vector3d();
				double stepAngle = clockwise ? -0.3 : 0.3;
				double angle = 0;
				
				@Override
				public void run() {
					if (!center.isDynamic() || !center.needsUpdate(pulseTick)) {
						vector = center.getVector3d();
						vector = vector.add(circle.getPoint(angle, 2));
						SimpleHalo.this.display(vector);
						angle = GenericMath.wrapAngleRad(angle + stepAngle);
					} else center.update();
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}
