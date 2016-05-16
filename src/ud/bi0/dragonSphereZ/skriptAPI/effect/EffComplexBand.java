package ud.bi0.dragonSphereZ.skriptAPI.effect;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.flowpowered.math.vector.Vector3d;

import java.util.List;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.effect.complex.ComplexBand;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class EffComplexBand extends Effect {
	private Expression<String> inputParticleString;
	private Expression<ItemStack> inputParticleData;
	private Expression<Number> inputParticleSpeed;
	private Expression<Number> offX;
	private Expression<Number> offY;
	private Expression<Number> offZ;
	private Expression<String> inputIdName;
	private Expression<?> entLoc;
	private Expression<Player> inputPlayers;
	private Expression<Boolean> isRainbowTrue;
	private Expression<Number> range;
	private Expression<Number> displaceX;
	private Expression<Number> displaceY;
	private Expression<Number> displaceZ;
	private Expression<Number> inputPulseDelay;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		inputParticleString = (Expression<String>) exprs[0];
		inputParticleData = (Expression<ItemStack>) exprs[1];
		inputParticleSpeed = (Expression<Number>) exprs[2];
		offX = (Expression<Number>) exprs[3];
		offY = (Expression<Number>) exprs[4];
		offZ = (Expression<Number>) exprs[5];
		entLoc = (Expression<Object>) exprs[6];
		inputIdName = (Expression<String>) exprs[7];
		inputPlayers = (Expression<Player>) exprs[8];
		isRainbowTrue = (Expression<Boolean>) exprs[9];
		range = (Expression<Number>) exprs[10];
		displaceX = (Expression<Number>) exprs[11];
		displaceY = (Expression<Number>) exprs[12];
		displaceZ = (Expression<Number>) exprs[13];
		inputPulseDelay = (Expression<Number>) exprs[14];
		return true;
	}
	
	/**
	 * drawBand 
	 * particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %objects%, 
	 * id %string%
	 * [, onlyFor %-players%]
	 * [, r[ainbow]M[ode] %-boolean%], 
	 * visibleRange %number%
	 * [, dis[placement]XYZ %-number%, %-number%, %-number%]
	 * [, pulseDelay %-number%]
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawBand particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %objects%, id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
	}

	@Override
	protected void execute(@Nullable Event e) {
		DynamicLocation center;
		try {
			center = DynamicLocation.init(entLoc.getSingle(e));
		} catch (IllegalArgumentException ex) {
			return;
		}
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, isRainbowTrue);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		Vector3d offset = SkriptHandler.inputParticleOffset(e, offX, offY, offZ);
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
		Vector3d displacement = SkriptHandler.inputLocDisplacement(e, displaceX, displaceY, displaceZ);
		int finalPulseTick = SkriptHandler.inputPulseTick(e, inputPulseDelay);

	    double visibleRange = range.getSingle(e).doubleValue();

	    String idName = inputIdName.getSingle(e);
	    
	    ComplexBand band = new ComplexBand(1, particle, dataMat, dataID, finalSpeed, offset, idName, center, players, rainbowMode, visibleRange, displacement, 0L, finalPulseTick);
	    band.start(band);

	}
	
}