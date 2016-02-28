package ud.bi0.dragonSphereZ.skriptAPI.effects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.particles.effects.complex.ComplexSpiral;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;


public class EffComplexSpiral extends Effect {
	private Expression<String> inputParticleString;
	private Expression<ItemStack> inputParticleData;
	private Expression<Number> inputParticleSpeed;
	private Expression<Number> offX;
	private Expression<Number> offY;
	private Expression<Number> offZ;
	private Expression<?> entLoc;
	private Expression<String> inputIdName;
	private Expression<Player> inputPlayers;
	private Expression<Boolean> inputRainbowMode;
	
	private Expression<Boolean> clockwise;
	private Expression<Boolean> scan;
	private Expression<Number> radius;
	private Expression<Number> inputHeight;
	private Expression<Number> inputEffectMod;
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
		entLoc = (Expression<?>) exprs[6];
		inputIdName = (Expression<String>) exprs[7];
		inputPlayers = (Expression<Player>) exprs[8];
		inputRainbowMode = (Expression<Boolean>) exprs[9];

		clockwise = (Expression<Boolean>) exprs[10];
		scan = (Expression<Boolean>) exprs[11];
		radius = (Expression<Number>) exprs[12];
		inputParticleDensity = (Expression<Number>) exprs[13];
		inputHeight = (Expression<Number>) exprs[14];
		inputEffectMod = (Expression<Number>) exprs[15];

		range = (Expression<Number>) exprs[16];
		xRot = (Expression<Number>) exprs[17];
		yRot = (Expression<Number>) exprs[18];
		zRot = (Expression<Number>) exprs[19];
		displaceX = (Expression<Number>) exprs[20];
		displaceY = (Expression<Number>) exprs[21];
		displaceZ = (Expression<Number>) exprs[22];
		inputPulseTick = (Expression<Number>) exprs[23];
		//TODO Test if this actually prevents wrong locations or entities (it doesn't yet)
		//Class<?> type = entLoc.getReturnType();
        //if (type != Entity.class || type != Location.class) {
        //    Skript.error(entLoc.toString() + " is neither an entity nor a location.", ErrorQuality.SEMANTIC_ERROR);
        //    return false;
        //}
		
		return true;
	}

	/**
	 * drawComplexSpiral 
	 * particle %string%[, material %-itemstack%]
	 * [, speed %-number%]
	 * [, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %object%, 
	 * id %string%, 
	 * [, onlyFor %-player%] 
	 * [, r[ainbow]M[ode] %-boolean%]
	 * [, clockwise %boolean%]
	 * [, scan %boolean%], 
	 * radius %number%, 
	 * density %number%, 
	 * height %number%, 
	 * effectMod %number%, 
	 * visibleRange %number%
	 * [, Rot[ation]XYZ %-number%, %-number%, %-number%]
	 * [, dis[placement]XYZ %-number%, %-number%, %-number%]
	 * [, pulseDelay %-number%]
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawComplexSpiral particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%][, clockwise %-boolean%][, scan %-boolean%], radius %number%, density %number%, height %number%, effectMod %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
	}

	@Override
	protected void execute(@Nullable Event e) {
		Class<?> type = entLoc.getReturnType();
		Bukkit.getServer().broadcastMessage("[skDragon] --->" + type);
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		float offsetX = SkriptHandler.inputParticleOffset(e, offX);
		float offsetY = SkriptHandler.inputParticleOffset(e, offY);
		float offsetZ = SkriptHandler.inputParticleOffset(e, offZ);
		Vector offset = new Vector(offsetX, offsetY, offsetZ);
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, inputRainbowMode);
		double disX = SkriptHandler.inputLocDisplacement(e, displaceX);
		double disY = SkriptHandler.inputLocDisplacement(e, displaceY);
		double disZ = SkriptHandler.inputLocDisplacement(e, displaceZ);
		Vector displacement = new Vector(disX, disY, disZ);	
		double xRotation = SkriptHandler.inputEffectRotation(e, xRot);
		double yRotation = SkriptHandler.inputEffectRotation(e, yRot);
		double zRotation = SkriptHandler.inputEffectRotation(e, zRot);
		Vector axis = new Vector(xRotation, yRotation, zRotation);
		int finalParticleDensity = SkriptHandler.inputParticleDensity(e, inputParticleDensity);
		
		
		boolean finalClockwise = clockwise.getSingle(e).booleanValue();
		boolean finalScan = scan.getSingle(e).booleanValue();
		float finalEffectMod = SkriptHandler.inputEffectMod(e, inputEffectMod);
		float finalHeight = SkriptHandler.inputHeight(e, inputHeight);
		Object center = entLoc.getSingle(e);
		
		String idName = inputIdName.getSingle(e);
		double visibleRange = range.getSingle(e).doubleValue();
		
		float finalRadius = radius.getSingle(e).floatValue();
		Long finalPulseTick = SkriptHandler.inputPulseTick(e, inputPulseTick);
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
		//(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed,  visibleRange,  rainbowMode, scan, offset, displacement, radius, circleDensity, height, effectMod, clockwise, axis)
		new ComplexSpiral(idName, particle, center, players, 0L, finalPulseTick, 1, dataMat, dataID, finalSpeed, visibleRange, rainbowMode, finalScan, offset, displacement, finalRadius, finalParticleDensity, finalHeight, finalEffectMod, finalClockwise, axis).start();
	}
}