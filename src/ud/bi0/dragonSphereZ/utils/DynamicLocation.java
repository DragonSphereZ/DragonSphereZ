package ud.bi0.dragonSphereZ.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import ud.bi0.dragonSphereZ.oldmath.vector.Vector3;
import ud.bi0.dragonSphereZ.particles.ParticleEffect;

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
		if (dynamic && this.distanceSquared(entity.getLocation()) > epsilon * epsilon / ticks) return true;
		return false;
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
	
	/**
	 * Gets the displacement vector between the location and the
	 * entity's location divided by the time interval.
	 */
	public Vector3 displacement(Vector3 direction, long pulseTick) {
		if (dynamic) {
			direction.copy(this.getEntity().getLocation());
			direction.add(-this.getZ(), -this.getX(), -this.getY());
			return direction.multiply(1D / pulseTick);
		} else return null;
	}
	
	public static DynamicLocation init(Object center) {
		if (center instanceof Entity) return new DynamicLocation((Entity) center);
		return new DynamicLocation(new Location(((Location) center).getWorld(), ((Location) center).getX(), ((Location) center).getY(), ((Location) center).getZ()));
	}
	
	public void display(ParticleEffect effect) {
		ParticleEffectUtils.valueOf(effect.particle).display(effect.dataMat, effect.dataID, effect.players, this, effect.visibleRange, effect.rainbowMode, effect.offset, effect.speed, effect.particleCount);
	}
	
}
