package ud.bi0.dragonSphereZ.effect.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntToDoubleFunction;

import org.bukkit.entity.Player;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.shape.Cylinder;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class SimpleHalo extends ParticleEffect {
	
	boolean clockwise;
	Iterator<Vector3d> iterator;
	ArrayList<Vector3d> vectors;
	
	public SimpleHalo(String idName, String particle, DynamicLocation center, List<Player> players, boolean clockwise) {
		super(idName, particle, center, players);
		this.pulseTick = 2;
		this.clockwise = clockwise;
		renderCircle();
	}
	
	void renderCircle() {
		int n = 20; //Amount of points on the circle.
		Cylinder circle = new Cylinder();
		circle.setRadius(0.5);
		IntToDoubleFunction angle = t -> t * TrigMath.TWO_PI / n;
		IntToDoubleFunction height = t -> 2;
		ArrayList<Vector3d> vectors = circle.getPointN(n, angle , height);
		this.vectors = vectors;
		this.iterator = vectors.iterator();
	}
	
	@Override
	public void onRun() {
		if (!center.isDynamic() || !center.needsUpdate(pulseTick)) {
			if (!iterator.hasNext()) iterator = vectors.iterator(); // Resets the iterator.
			display(iterator.next());
		} else center.update();
	}
}
