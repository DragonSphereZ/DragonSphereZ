package ud.bi0.dragonSphereZ.skriptAPI;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ud.bi0.dragonSphereZ.utils.ParticleEffect;

public class SkriptHandler {
	
	/**
	 * Checks if all expressions are non-null.
	 */
	public static boolean hasNull(@Nullable Event e, @Nullable Expression<?>...args) {
		for (Expression<?> arg : args) {
			if (arg == null) return true;
			if (arg.isSingle()) {
				if (arg.getSingle(e) == null) return true;
			} else if (arg.getAll(e).length == 0 || arg.getAll(e) == null) return true; 
		}
		return false;
	}
	/**
	 * This is the amount of particles that are played at once in a single location or if offset is used they randomize.
	 */
	public static int inputParticleCount(@Nullable Event e, @Nullable Expression<Number> inputParticleCount) {
		int count = 1;
		if(inputParticleCount != null){
    		count = inputParticleCount.getSingle(e).intValue();
    	}
		return count;
    }
	
	/**
	 * This method checks that a particle effect was entered correctly otherwise it will default to limeglassparticle(happyvillager) particle.
	 */
	public static String inputParticleString(@Nullable Event e, @Nullable Expression<String> inputParticleString) {
		String particle = "limeglassparticle";
		if (inputParticleString != null){
	    	if (ParticleEffect.NAME_MAP.containsKey(inputParticleString.getSingle(e).toLowerCase()) == true)
				particle = (String)inputParticleString.getSingle(e).toLowerCase();
		}
		return particle;
    }
	
	/**
	 * This method can be null. It affects the speed of a particle(not the entire effect)
	 */
	public static float inputParticleSpeed(@Nullable Event e, @Nullable Expression<Number> inputParticleSpeed) {
		float finalSpeed = 0;
		if(inputParticleSpeed != null){
			finalSpeed = inputParticleSpeed.getSingle(e).floatValue();	
		}
		return finalSpeed;
    }
	
	
	
	
	/**
	 * If this returns true, it along with getPlayer make it so that only the single player can see the particle effect.
	 * If isSinglePlayer is false then the final effect helpers skip this return.
	 */
	public static boolean inputIsSinglePlayer(@Nullable Event e, @Nullable Expression<Boolean> inputSinglePlayer) {
		boolean isSinglePlayer = false;
		if (inputSinglePlayer != null && inputSinglePlayer.getSingle(e) != null){
			isSinglePlayer = inputSinglePlayer.getSingle(e).booleanValue();
		}
		return isSinglePlayer;
    }
	
	/**
	 * This method can return null because the final result relies on isSinglePlayer being true and are required together.
	 * If isSinglePlayer is false then the final effect helpers skip this return.
	 */
	public static Player inputPlayer(@Nullable Event e, @Nullable Expression<Player> inputPlayer) {
		Player player = null;
		if (inputPlayer != null && inputPlayer.getSingle(e) != null){
			player = (Player)inputPlayer.getSingle(e);
		}
		return player;
    }
	
	public static List<Player> inputPlayers(@Nullable Event e, @Nullable Expression<Player> inputPlayers) {
		if (inputPlayers != null && inputPlayers.getAll(e) != null && inputPlayers.getAll(e).length != 0) {
			return Arrays.asList(inputPlayers.getAll(e));
		}
		return null;
	}

	/**
	 * This method checks if rainbow mode is enabled
	 */
	public static boolean inputRainbowMode(@Nullable Event e, @Nullable Expression<Boolean> isRainbowTrue) {
		boolean rainbowMode = false;
		if (isRainbowTrue != null && isRainbowTrue.getSingle(e) != null){
			rainbowMode = isRainbowTrue.getSingle(e).booleanValue();
		}
		return rainbowMode;
    }
	
	
	
	
	
}
