package ud.bi0.dragonSphereZ.skriptAPI.effects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import ud.bi0.dragonSphereZ.particles.EffectsLib;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;


public class EffComplexCircle extends Effect {
	private Expression<ItemStack> inputParticleData;
	private Expression<String> inputParticleString;
	private Expression<Number> inputParticleSpeed;
	
	private Expression<?> entLoc;
	private Expression<String> idName;
	private Expression<Number> radius;
	private Expression<Player> inputPlayers;
	private Expression<Number> xRot;
	private Expression<Number> yRot;
	private Expression<Number> zRot;
	private Expression<Number> offX;
	private Expression<Number> offY;
	private Expression<Number> offZ;
	private Expression<Number> displaceX;
	private Expression<Number> displaceY;
	private Expression<Number> displaceZ;
	private Expression<Number> inputParticleDensity;
	private Expression<Number> step;
	private Expression<Number> range;
	private Expression<Boolean> rotation;
	private Expression<Boolean> isRainbowTrue;
	private Expression<Long> inputTickDelay;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {

		inputParticleString = (Expression<String>) exprs[0];
		inputParticleData = (Expression<ItemStack>) exprs[1];
		inputParticleSpeed = (Expression<Number>) exprs[2];
		offX = (Expression<Number>) exprs[3];
		offY = (Expression<Number>) exprs[4];
		offZ = (Expression<Number>) exprs[5];
		this.entLoc = (Expression<?>) exprs[6];
		this.idName = (Expression<String>) exprs[7];
		inputPlayers = (Expression<Player>) exprs[9];
		isRainbowTrue = (Expression<Boolean>) exprs[10];
		rotation = (Expression<Boolean>) exprs[11];
		this.radius = (Expression<Number>) exprs[12];
		inputParticleDensity = (Expression<Number>) exprs[13];
		step = (Expression<Number>) exprs[14];
		range = (Expression<Number>) exprs[15];
		xRot = (Expression<Number>) exprs[16];
		yRot = (Expression<Number>) exprs[17];
		zRot = (Expression<Number>) exprs[18];
		displaceX = (Expression<Number>) exprs[19];
		displaceY = (Expression<Number>) exprs[20];
		displaceZ = (Expression<Number>) exprs[21];
		inputTickDelay = (Expression<Long>) exprs[22];
		return true;
	}

	/**
	 * drawComplexCircle 
	 * particle %string%[, material %-itemstack%]
	 * [, speed %-number%]
	 * [, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %locations/entitys%, 
	 * id %string%, 
	 * [, onlyFor %-player%]
	 * [, r[ainbow]M[ode] %-boolean%], 
	 * randomRotation %boolean%, 
	 * radius %number%, 
	 * density %number%, 
	 * start %number%, 
	 * visibleRange %number%, 
	 * [, Rot[ation]XYZ %-number%, %-number%, %-number%]
	 * [, dis[placement]XYZ %-number%, %-number%, %-number%]
	 * [, pulseDelay %-number%]
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawComplexCircle particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %locations/entitys%, id %string%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, radius %number%, density %number%, start %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, [pulse]Delay %-number%]";
	}

	@Override
	protected void execute(@Nullable Event e) {

		

		Long finalTickDelay = (long) 0;
		
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		float offsetX = SkriptHandler.inputParticleOffset(e, offX);
		float offsetY = SkriptHandler.inputParticleOffset(e, offY);
		float offsetZ = SkriptHandler.inputParticleOffset(e, offZ);
		List<Player> p = SkriptHandler.inputPlayers(e, inputPlayers);
		
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, isRainbowTrue);
		double disX = SkriptHandler.inputLocDisplacement(e, displaceX);
		double disY = SkriptHandler.inputLocDisplacement(e, displaceY);
		double disZ = SkriptHandler.inputLocDisplacement(e, displaceZ);
		double xRotation = SkriptHandler.inputEffectRotation(e, xRot);
		double yRotation = SkriptHandler.inputEffectRotation(e, yRot);
		double zRotation = SkriptHandler.inputEffectRotation(e, zRot);
		int finalParticleDensity = SkriptHandler.inputParticleDensity(e, inputParticleDensity);
		
		Object center = entLoc.getSingle(e);
		String idName = (String)this.idName.getSingle(e);
		
		
		float finalStep = step.getSingle(e).floatValue();
		double visibleRange = range.getSingle(e).doubleValue();

		boolean enableRotation = rotation.getSingle(e).booleanValue();
		Number radiusInt = (Number)this.radius.getSingle(e);
		float radius = ((Number)radiusInt).floatValue();
		

		if (inputTickDelay != null){
			finalTickDelay = inputTickDelay.getSingle(e);
		}
		
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
		EffectsLib.drawComplexCircle(particle, dataMat, dataID, center, idName, p, rainbowMode, enableRotation, radius, finalSpeed, finalParticleDensity, finalStep, visibleRange, xRotation, yRotation, zRotation, offsetX, offsetY, offsetZ, disX, disY, disZ, finalTickDelay);

	}
}