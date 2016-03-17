package ud.bi0.dragonSphereZ.skriptAPI;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.flowpowered.math.vector.Vector3d;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.Timespan;
import ud.bi0.dragonSphereZ.DragonSphereCore;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

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
			if (arg.isSingle() && arg.getSingle(e) == null) return true;
			if (!arg.isSingle() && (arg.getAll(e).length == 0 || arg.getAll(e) == null)) return true; 
		}
		return false;
	}
		
	/**
	 * Checks if an object is an entity or location. TODO TEST THIS IF THE OTHER ONE WORKS
	 */
	public static boolean isEntityOrLocation(@Nullable Expression<Boolean> entLoc) {
		Class<?> type = entLoc.getReturnType();
        if (type != Entity.class || type != Location.class)
            Skript.error(entLoc.toString() + " is neither an entity nor a location.", ErrorQuality.SEMANTIC_ERROR);
            return false;
	}
	
	/**
	 * Initializes the effect ID.
	 */
	@Nullable
	public static String inputID(@Nullable Event e, @Nullable Expression<String> effectID) {
		String id = null;
		if (effectID != null && effectID.getSingle(e) != null) {
			if (!DragonSphereCore.effectManager.isActive(id)) {
				id = effectID.getSingle(e);
			}
		}
		if (id == null) {
			throw new IllegalArgumentException("An effect with this ID is already active.");
		}
		return id;
	}
	
	
	/**
	 * Initializes a DynamicLocation from an object.
	 */
	@Nullable
	public static DynamicLocation inputCenter(@Nullable Event e, @Nullable Expression<Object> center) {
		DynamicLocation dynLoc = null;
		if (center != null && center.getSingle(e) != null) {
			try {
				dynLoc = DynamicLocation.init(center.getSingle(e));
			} catch (IllegalArgumentException ex) {};
		}
		if (dynLoc == null) {
			throw new IllegalArgumentException("The object is not of type Entity or Location");
		}
		return dynLoc;
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
	    	if (ParticleEffectUtils.NAME_MAP.containsKey(inputParticleString.getSingle(e).toLowerCase()) == true)
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
	public static Vector3d inputParticleOffset(@Nullable Event e, @Nullable Expression<Number> inputParticleOffsetX, @Nullable Expression<Number> inputParticleOffsetY, @Nullable Expression<Number> inputParticleOffsetZ) {
		if(inputParticleOffsetX != null && inputParticleOffsetY != null && inputParticleOffsetZ != null){
			return new Vector3d(inputParticleOffsetZ.getSingle(e).floatValue(),inputParticleOffsetY.getSingle(e).floatValue(),inputParticleOffsetZ.getSingle(e).floatValue());
		}
		return new Vector3d();
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
	public static boolean inputRainbowMode(@Nullable Event e, @Nullable Expression<Boolean> inputRainbowMode) {
		if (inputRainbowMode != null && inputRainbowMode.getSingle(e) != null){
			return inputRainbowMode.getSingle(e).booleanValue();
		}
		return false;
    }
	
	public static float inputRadius(@Nullable Event e, @Nullable Expression<Number> inputRadius) {
		if(inputRadius != null){
			return inputRadius.getSingle(e).floatValue();
		}
		return 1;
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
	public static int inputNucleusDensity(@Nullable Event e, @Nullable Expression<Number> inputNucleusDensity) {
		if (inputNucleusDensity != null && inputNucleusDensity.getSingle(e) != null){
			return inputNucleusDensity.getSingle(e).intValue();
		}
		return 10;
    }
	public static int inputOrbitSpeed(@Nullable Event e, @Nullable Expression<Number> inputOrbitSpeed) {
		if (inputOrbitSpeed != null && inputOrbitSpeed.getSingle(e) != null){
			return inputOrbitSpeed.getSingle(e).intValue();
		}
		return 1;
    }
	public static int inputOrbitalCount(@Nullable Event e, @Nullable Expression<Number> inputOrbitalCount) {
		if (inputOrbitalCount != null && inputOrbitalCount.getSingle(e) != null){
			return inputOrbitalCount.getSingle(e).intValue();
		}
		return 3;
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
	public static Vector3d inputLocDisplacement(@Nullable Event e, @Nullable Expression<Number> inputLocDisplacementX, @Nullable Expression<Number> inputLocDisplacementY, @Nullable Expression<Number> inputLocDisplacementZ) {
		if(inputLocDisplacementX != null && inputLocDisplacementY != null && inputLocDisplacementZ != null){
			return new Vector3d(inputLocDisplacementZ.getSingle(e).floatValue(),inputLocDisplacementY.getSingle(e).floatValue(),inputLocDisplacementZ.getSingle(e).floatValue());
		}
		return new Vector3d(0,0,0);
    }
	
	/**
	 * This method checks if random rotation mode is enabled for certain effects.
	 */
	public static boolean inputRotationMode(@Nullable Event e, @Nullable Expression<Boolean> inputRotationMode) {
		if (inputRotationMode != null && inputRotationMode.getSingle(e) != null){
			return inputRotationMode.getSingle(e).booleanValue();
		}
		return false;
    }
	
	/**
	 * For manually rotating the whole effect around its axis prior to any other math.
	 */
	public static double inputEffectRotation(@Nullable Event e, @Nullable Expression<Number> inputEffectRotation) {
		if(inputEffectRotation != null){
			return inputEffectRotation.getSingle(e).doubleValue();
		}
		return 0;
    }
	
	public static Vector3d inputEffectRotation(@Nullable Event e, @Nullable Expression<Number> inputEffectRotationX, @Nullable Expression<Number> inputEffectRotationY, @Nullable Expression<Number> inputEffectRotationZ) {
		Vector3d v = new Vector3d();
		if (!hasNull(e, inputEffectRotationX, inputEffectRotationY, inputEffectRotationZ)) {
			v = v.add(inputEffectRotationX.getSingle(e).floatValue(), inputEffectRotationY.getSingle(e).floatValue(), inputEffectRotationZ.getSingle(e).floatValue());
			try {
				v = v.normalize();
			} catch (ArithmeticException ex) {
				v = Vector3d.UNIT_Y;
			}
		} else v = Vector3d.UNIT_Y;
		return v;
    }
	
	/**
	 * The spiral effect has a height variable
	 */
	public static float inputHeight(@Nullable Event e, @Nullable Expression<Number> inputHeight) {
		if(inputHeight != null){
			return inputHeight.getSingle(e).floatValue();
		}
		return 2;
    }
	
	/**
	 * This changes the 'angle' of the spiral effect(increases its height at a certain speed)
	 */
	public static float inputHeightMod(@Nullable Event e, @Nullable Expression<Number> inputHeightMod) {
		if(inputHeightMod != null){
			return inputHeightMod.getSingle(e).floatValue();
		}
		return (float) 0.5;
    }

	/**
	 * Changes the length of time between 'loops' for each effect
	 */
	public static int inputPulseTick(@Nullable Event e, @Nullable Expression<Number> inputPulseTick) {
		if(inputPulseTick != null){
			return inputPulseTick.getSingle(e).intValue();
		}
		return 0;
    }
	/**
	 * Some effects turn themselves off on their own after an amount of time, default 5 ticks
	 */
	public static int inputKeepDelay(@Nullable Event e, @Nullable Expression<Timespan> inputKeepDelay) {
		if(inputKeepDelay != null){
			return inputKeepDelay.getSingle(e).getTicks();
		}
		return 5;
    }

}
