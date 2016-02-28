package ud.bi0.dragonSphereZ.effect.simple;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.DoubleFunction;
import ud.bi0.dragonSphereZ.math.shape.Ellipse;
import ud.bi0.dragonSphereZ.utils.DynamicLocation;
import ud.bi0.dragonSphereZ.utils.FlowPoweredHook;

public class NewSimpleHalo extends ParticleEffect {
	
	boolean clockwise;
	
	public NewSimpleHalo(String idName, String particle, Object center, List<Player> players, boolean clockwise) {
		super(idName, particle, center, players, 0, 2, 1, Material.DIRT,(byte) 0, 0, 32, false, new Vector(0,0,0));
		this.clockwise = clockwise;
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				Ellipse ell = new Ellipse()
									.setBase(Base3d.MINECRAFT)
									.setRadius(0.5, 0.5);
				double phi = 0;
				double stepPhi = clockwise ? -0.3 : 0.3;
				int n = 5;
				ArrayList<Vector3d> vecs = new ArrayList<Vector3d>(n);
				DoubleFunction angleFunction = (i) -> phi + i * stepPhi;
				Location loc;
				DynamicLocation location = DynamicLocation.init(center);
				
				@Override
				public void run() {
					if (!location.isDynamic() || !location.needsUpdate(pulseTick)) {
						location.update();
						vecs = ell.getPointN(n, angleFunction);
						for (Vector3d vec : vecs) {
							loc = FlowPoweredHook.Location(vec, location.getWorld());
							loc.add(0, 2, 0);
							location.add(loc);
							location.display(NewSimpleHalo.this);
							location.subtract(loc);
						}
						phi += 5 * stepPhi;
						phi = GenericMath.wrapAngleRad(phi);
					} else location.update();
				}
			}, this.delayTick, this.pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
		
	}
}
