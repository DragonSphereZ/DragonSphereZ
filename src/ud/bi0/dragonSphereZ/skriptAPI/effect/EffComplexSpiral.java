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
import ud.bi0.dragonSphereZ.effect.complex.ComplexSpiral;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.util.DynamicLocation;


public class EffComplexSpiral extends Effect {
	private Expression<String> inputParticleString;
	private Expression<ItemStack> inputParticleData;
	private Expression<Number> inputParticleSpeed;
	private Expression<Number> offX;
	private Expression<Number> offY;
	private Expression<Number> offZ;
	private Expression<Object> entLoc;
	private Expression<String> inputIdName;
	private Expression<Player> inputPlayers;
	private Expression<Boolean> inputRainbowMode;
	
	private Expression<Boolean> clockwise;
	private Expression<Boolean> scan;
	private Expression<Number> inputRadius;
	private Expression<Number> inputHeight;
	private Expression<Number> inputHeightMod;
	private Expression<Number> inputParticleDensity;
	
	private Expression<Number> range;
	private Expression<Number> xRot;
	private Expression<Number> yRot;
	private Expression<Number> zRot;
	private Expression<Number> displaceX;
	private Expression<Number> displaceY;
	private Expression<Number> displaceZ;
	private Expression<Number> inputPulseTick;
	
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
		inputRainbowMode = (Expression<Boolean>) exprs[9];

		clockwise = (Expression<Boolean>) exprs[10];
		scan = (Expression<Boolean>) exprs[11];
		inputRadius = (Expression<Number>) exprs[12];
		inputParticleDensity = (Expression<Number>) exprs[13];
		inputHeight = (Expression<Number>) exprs[14];
		inputHeightMod = (Expression<Number>) exprs[15];

		range = (Expression<Number>) exprs[16];
		xRot = (Expression<Number>) exprs[17];
		yRot = (Expression<Number>) exprs[18];
		zRot = (Expression<Number>) exprs[19];
		displaceX = (Expression<Number>) exprs[20];
		displaceY = (Expression<Number>) exprs[21];
		displaceZ = (Expression<Number>) exprs[22];
		inputPulseTick = (Expression<Number>) exprs[23];
		return true;
	}

	/**
	 * drawComplexSpiral 
	 * particle %string%[, material %-itemstack%]
	 * [, speed %-number%]
	 * [, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %object%, 
	 * id %string%, 
	 * [, onlyFor %-players%] 
	 * [, r[ainbow]M[ode] %-boolean%]
	 * [, clockwise %boolean%]
	 * [, scan %boolean%], 
	 * radius %number%, 
	 * density %number%, 
	 * height %number%, 
	 * heightMod %number%, 
	 * visibleRange %number%
	 * [, Rot[ation]XYZ %-number%, %-number%, %-number%]
	 * [, dis[placement]XYZ %-number%, %-number%, %-number%]
	 * [, pulseDelay %-number%]
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawComplexSpiral particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%][, clockwise %-boolean%][, scan %-boolean%], radius %number%, density %number%, height %number%, heightMod %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
	}

	@Override
	protected void execute(@Nullable Event e) {
		//DynamicLocation center = DynamicLocation.init(entLoc.getSingle(e));
		DynamicLocation center;
		try {
			center = DynamicLocation.init(entLoc.getSingle(e));
		} catch (IllegalArgumentException ex) {
			return;
		}
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		Vector3d offset = SkriptHandler.inputParticleOffset(e, offX, offY, offZ);
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, inputRainbowMode);
		Vector3d displacement = SkriptHandler.inputLocDisplacement(e, displaceX, displaceY, displaceZ);
		Vector3d axis = SkriptHandler.inputEffectRotation(e, xRot, yRot, zRot);
		int finalParticleDensity = SkriptHandler.inputParticleDensity(e, inputParticleDensity);
		boolean finalClockwise = clockwise.getSingle(e).booleanValue();
		boolean finalScan = scan.getSingle(e).booleanValue();
		float finalHeightMod = SkriptHandler.inputHeightMod(e, inputHeightMod);
		float finalHeight = SkriptHandler.inputHeight(e, inputHeight);
		String idName = inputIdName.getSingle(e);
		double visibleRange = range.getSingle(e).doubleValue();
		float finalRadius = SkriptHandler.inputRadius(e, inputRadius);
		int finalPulseTick = SkriptHandler.inputPulseTick(e, inputPulseTick);
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
				    	// idName,particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, scan, offset, displacement, radius, circleDensity, height, effectMod, clockwise, axis)
		ComplexSpiral spiral = new ComplexSpiral(1, particle, dataMat, dataID, finalSpeed, offset, idName, center, players, rainbowMode, visibleRange, displacement, 0L, finalPulseTick, finalClockwise, finalScan, finalRadius, finalHeight, finalParticleDensity, finalHeightMod, axis);
		spiral.start(spiral);
	}
}