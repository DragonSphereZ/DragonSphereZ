package ud.bi0.dragonSphereZ.effect;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class NewParticleEffectManager {
	
	private final static HashMap<String, ParticleEffect> effectMap = new HashMap<String, ParticleEffect>();
	
	public NewParticleEffectManager() {
	}
	
	/**
	 * Starts an effect.
	 */
	public boolean startEffect(ParticleEffect effect) {
		if (isActive(effect.idName)) return false;
		effectMap.put(effect.idName, effect);
		return true;
	}
	
	/**
	 * Stops an effect.
	 */
	public void stopEffect(String idName) {
		if (effectMap.containsKey(idName)) {
			Bukkit.getScheduler().cancelTask(effectMap.get(idName).idTask);
			effectMap.remove(idName);
		}
	}
	
	/**
	 * Stops all effects targeting this entity.
	 * 
	 */
	public void stopEffect(Entity entity) {
		if (entity != null) {
			ParticleEffect[] values = new ParticleEffect[effectMap.values().size()];
			effectMap.values().toArray(values);
			for (ParticleEffect effect : values) {
				if (effect.getCenter().getEntity().equals(entity)) stopEffect(effect.idName);
			}
		}
	}
	
	/**
	 * Stops all effects.
	 */
	public void stopAll() {
		String[] keys = new String[effectMap.keySet().size()];
		effectMap.keySet().toArray(keys);
		for (String key : keys) stopEffect(key);
	}
	
	/**
	 * Returns true if the effect is active.
	 */
	public boolean isActive(String idName) {
		if (effectMap.containsKey(idName)) return true;
		return false;
	}
	
	public Set<String> getKeys(){
		return effectMap.keySet();
	}
	
}
