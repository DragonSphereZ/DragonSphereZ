package ud.bi0.dragonSphereZ.particles;

import java.util.List;

import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.particles.effects.SimpleHalo;
import ud.bi0.dragonSphereZ.particles.effects.SimpleYinYang;

public class SimpleEffectHelper {
	
	public static ParticleEffect getEffect(String name, String id, String particle, Object target, List<Player> players) {
		
		// changes "-string" to "string" and changes the orientation of the effect
		name.toLowerCase();
		boolean clockwise = false;
		if (name.startsWith("-")) {
			name = name.substring(1);
			clockwise = true;
		}
		
		// initializes the effect
		switch(name) {
		case "halo":
			return new SimpleHalo(id, particle, target, players, clockwise);
		case "yinyang":
			return new SimpleYinYang(id, particle, target, players, clockwise);
		}
		return null;
	}
	
}
