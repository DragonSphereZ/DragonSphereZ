package ud.bi0.dragonSphereZ.skriptAPI;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ud.bi0.dragonSphereZ.utils.ParticleEffect;

/**
 * SkriptHandler written by Sashie and bi0qaw.
 * This handler allows easy hooks into Skripts input for use with DragonSphereZ.
 */
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
		if(inputParticleCount != null){
    		return inputParticleCount.getSingle(e).intValue();
    	}
		return 1;
    }
	
	/**
	 * This method checks that a particle effect was entered correctly otherwise it will default to limeglassparticle(happyvillager) particle.
	 */
	public static String inputParticleString(@Nullable Event e, @Nullable Expression<String> inputParticleString) {
		if (inputParticleString != null){
	    	if (ParticleEffect.NAME_MAP.containsKey(inputParticleString.getSingle(e).toLowerCase()) == true)
				return (String)inputParticleString.getSingle(e).toLowerCase();
		}
		return "limeglassparticle";
    }
	
	/**
	 * Handles the data values of a particle.
	 */
	public static Material inputParticleDataMat(@Nullable Event e, @Nullable Expression<ItemStack> inputParticleData) {
		if(inputParticleData != null){
			return inputParticleData.getSingle(e).getType();
		}
		return Material.DIRT;
    }
	@SuppressWarnings("deprecation")
	public static byte inputParticleDataID(@Nullable Event e, @Nullable Expression<ItemStack> inputParticleData) {
		if(inputParticleData != null){
			return inputParticleData.getSingle(e).getData().getData();
		}
		return 0;
    }
	
	/**
	 * This method can be null. It affects the speed of a particle(not the entire effect)
	 */
	public static float inputParticleSpeed(@Nullable Event e, @Nullable Expression<Number> inputParticleSpeed) {
		if(inputParticleSpeed != null){
			return inputParticleSpeed.getSingle(e).floatValue();	
		}
		return 0;
    }
	
	/**
	 * Some particles can use an offset to randomize how far from their center they spawn.
	 * This also handles RGB(color) and will soon handle HSB <3
	 */
	public static float inputParticleOffset(@Nullable Event e, @Nullable Expression<Number> inputParticleOffset) {
		if(inputParticleOffset != null){
			return inputParticleOffset.getSingle(e).floatValue();
		}
		return 0;
    }
	
	/**
	 * This method can return null because the final result relies on isSinglePlayer being true and are required together.
	 * If isSinglePlayer is false then the final effect helpers skip this return.
	 */
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
		if (isRainbowTrue != null && isRainbowTrue.getSingle(e) != null){
			return isRainbowTrue.getSingle(e).booleanValue();
		}
		return false;
    }
	
	/**
	 * The amount of points along the effects path.
	 */
	public static int inputParticleDensity(@Nullable Event e, @Nullable Expression<Number> inputParticleDensity) {
		if (inputParticleDensity != null && inputParticleDensity.getSingle(e) != null){
			return inputParticleDensity.getSingle(e).intValue();
		}
		return 20;
    }

	
	/**
	 * This will place the particle anywhere around the player they want for instance if they want to make their own halo using the circle.
	 */
	public static double inputLocDisplacement(@Nullable Event e, @Nullable Expression<Number> inputLocDisplacement) {
		if(inputLocDisplacement != null){
			return inputLocDisplacement.getSingle(e).doubleValue();
		}
		return 0;
    }
	
	/**
	 * For manually rotating the whole effect around its axis.
	 */
	public static double inputEffectRotation(@Nullable Event e, @Nullable Expression<Number> inputEffectRotation) {
		if(inputEffectRotation != null){
			return inputEffectRotation.getSingle(e).doubleValue();
		}
		return 0;
    }
	

}
