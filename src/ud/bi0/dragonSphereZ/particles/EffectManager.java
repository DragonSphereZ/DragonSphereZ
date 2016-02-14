package ud.bi0.dragonSphereZ.particles;

import java.util.HashMap;

import org.bukkit.Bukkit;

public class EffectManager {
	
	private final static HashMap<String, Integer> effectMap = new HashMap<String, Integer>();
	
	public EffectManager() {
	}
	
	public boolean startEffect(String idName, Integer idTask) {
		if (isActive(idName)) return false;
		effectMap.put(idName, idTask);
		return true;
	}
	
	public void stopEffect(String idName) {
		if (effectMap.containsKey(idName)) {
			Bukkit.getScheduler().cancelTask(effectMap.get(idName));
			effectMap.remove(idName);
		}
	}
	
	public boolean isActive(String idName) {
		if (effectMap.containsKey(idName)) return true;
		return false;
	}
	
}
