package ud.bi0.dragonSphereZ.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.flowpowered.math.vector.Vector3d;

public class DynamicLocation extends Location {

	private Entity entity = null;
	private boolean dynamic = false;
	private float epsilon = 0.05F; //lower than sneaking speed
	
	public DynamicLocation(Location location) {
		super(location.getWorld(), location.getX(), location.getY(), location.getZ());
	}
	
	public DynamicLocation(Entity entity) {
		super(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
    	this.entity = entity;
    	this.dynamic = true;
	}
	
	public DynamicLocation update() {
		if (dynamic) {
			this.setX(entity.getLocation().getX());
			this.setY(entity.getLocation().getY());
			this.setZ(entity.getLocation().getZ());
		}
		return this;
	}
	
	public boolean needsUpdate() {
		if (dynamic && this.distanceSquared(entity.getLocation()) > epsilon * epsilon) return true;
		return false;
	}
	
	public boolean needsUpdate(long ticks) {
		if (dynamic && this.distanceSquared(entity.getLocation()) > epsilon * epsilon / (ticks > 0 ? ticks : 1)) return true;
		return false;
	}
	
	public boolean hasMoved() {
		return needsUpdate();
	}
	
	public boolean hasMoved(long ticks) {
		return needsUpdate(ticks);
	}
	
	public boolean isDynamic() {
		return dynamic;
	}
	
	public float getEpsilon() {
		return epsilon;
	}
	
	public void setEpsilon(float epsilon) {
		this.epsilon = epsilon;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Vector3d getVector3d() {
		return new Vector3d(this.getX(), this.getY(), this.getZ());
	}
	
	/**
	 * Gets the displacement vector between the location and the
	 * entity's location divided by the time interval.
	 */
	public Vector3d getDisplacement(long pulseTick) {
		if (dynamic) {
			Vector3d displacement = FlowPoweredHook.Vector3d(this.getEntity().getLocation());
			displacement = displacement.sub(this.getVector3d()).mul(1D / (pulseTick > 0 ? pulseTick : 1));
			return displacement;
		} else return new Vector3d(0,0,0);
	}
	
	public static DynamicLocation init(Object center) {
		if (center instanceof Entity) return new DynamicLocation((Entity) center);
		else if (center instanceof Location) return new DynamicLocation((Location) center);
		else throw new IllegalArgumentException("The object is not of type Entity or Location");
	}
	
	//public void display(ParticleEffect effect) {
	//	ParticleEffectUtils.valueOf(effect.particle).display(effect.dataMat, effect.dataID, effect.players, this, effect.visibleRange, effect.rainbowMode, effect.offset, effect.speed, effect.particleCount);
	//}
	
	//public static void displayList(ParticleEffect effect, List<DynamicLocation> locs) {
	//	for (DynamicLocation loc : locs) {
	//		loc.display(effect);
	//	}
	//}
	
}
