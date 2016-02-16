package ud.bi0.dragonSphereZ.particles;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;

public class ParticleEffectManager {
	
	private final static HashMap<String, ParticleEffect> effectMap = new HashMap<String, ParticleEffect>();
	
	public ParticleEffectManager() {
	}
	
	/**
	 * Starts an effect.
	 */
	public boolean startEffect(ParticleEffect effect) {
		if (isActive(effect.getNameID())) return false;
		effectMap.put(effect.getNameID(), effect);
		return true;
	}
	
	/**
	 * Stops an effect.
	 */
	public void stopEffect(String idName) {
		if (effectMap.containsKey(idName)) {
			Bukkit.getScheduler().cancelTask(effectMap.get(idName).getID());
			effectMap.remove(idName);
		}
	}
	
	/**
	 * Stops all effects.
	 */
	public void stopAll() {
		for (String key : effectMap.keySet()) {
			stopEffect(key);
		}
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
