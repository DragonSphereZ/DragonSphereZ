package ud.bi0.dragonSphereZ.effect.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntToDoubleFunction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.shape.Cylinder;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class SimpleHalo extends ParticleEffect {
	
	boolean clockwise;
	ArrayList<Vector3d> vectors;
	
	public SimpleHalo(String idName, String particle, DynamicLocation center, List<Player> players, boolean clockwise) {
		super(idName, particle, center, players);
		this.pulseTick = 2;
		this.clockwise = clockwise;
		renderCircle();
	}
	
	void renderCircle() {
		int n = 20; //Amount of points on the circle.
		Cylinder circle = new Cylinder().setBase(Base3d.MINECRAFT).setRadius(0.5);
		IntToDoubleFunction angle = t -> t * TrigMath.TWO_PI / n;
		IntToDoubleFunction height = t -> 2;
		ArrayList<Vector3d> vectors = circle.getPointN(n, angle , height);
		this.vectors = vectors;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				Vector3d vector = new Vector3d();
				Iterator<Vector3d> iterator = vectors.iterator();
				
				@Override
				public void run() {
					if (!center.isDynamic() || !center.needsUpdate(pulseTick)) {
						vector = center.getVector3d();
						if (!iterator.hasNext()) iterator = vectors.iterator(); // Resets the iterator.
						vector = vector.add(iterator.next());
						SimpleHalo.this.display(vector);
					} else center.update();
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}
