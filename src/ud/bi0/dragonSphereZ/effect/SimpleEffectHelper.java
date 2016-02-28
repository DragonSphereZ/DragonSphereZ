package ud.bi0.dragonSphereZ.effect;

import java.util.List;

import org.bukkit.entity.Player;

import ud.bi0.dragonSphereZ.effect.simple.*;
import ud.bi0.dragonSphereZ.particles.effects.simple.SimpleAura;
import ud.bi0.dragonSphereZ.particles.effects.simple.SimpleHalo;
import ud.bi0.dragonSphereZ.particles.effects.simple.SimpleTrail;
import ud.bi0.dragonSphereZ.particles.effects.simple.SimpleYinYang;


public class SimpleEffectHelper {
	
	boolean clockwise = false;
	
	public SimpleEffectHelper() {
	}
	
	public ParticleEffect getEffect(String name, String id, String particle, Object target, List<Player> players) {
		
		this.parse(name);
		// initializes all effects
		switch(name) { 
		case "halo":
			return new SimpleHalo(id, particle, target, players, clockwise);
		case "yinyang":
			return new SimpleYinYang(id, particle, target, players, clockwise);
		case "aura":
			return new SimpleAura(id, particle, target, players);
		case "trail":
			return new SimpleTrail(id, particle, target, players);
		}
		return null;
	}
	
	public String parse(String name) {
		name.toLowerCase();
		if (name.contains("-")) clockwise = true; //changes the orientation of the effect
		name.replaceAll("\\P{Alpha}",""); //removes all non-alphanumeric characters
		return name;
	}
	
}
