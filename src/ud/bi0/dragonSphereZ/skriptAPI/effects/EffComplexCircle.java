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
import ud.bi0.dragonSphereZ.utils.ParticleEffect;


public class EffComplexCircle extends Effect {
	private Expression<ItemStack> data;
	private Expression<String> particleString;
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
	private Expression<Number> speed;
	private Expression<Long> pDensity;
	private Expression<Number> step;
	private Expression<Number> range;
	private Expression<Boolean> rotation;
	private Expression<Boolean> rainbMode;
	private Expression<Long> ticks;
	private Expression<Long> seconds;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {

		this.particleString = (Expression<String>) exprs[0];
		this.data = (Expression<ItemStack>) exprs[1];
		speed = (Expression<Number>) exprs[2];
		offX = (Expression<Number>) exprs[3];
		offY = (Expression<Number>) exprs[4];
		offZ = (Expression<Number>) exprs[5];
		this.entLoc = (Expression<?>) exprs[6];
		this.idName = (Expression<String>) exprs[7];
		inputPlayers = (Expression<Player>) exprs[9];
		rainbMode = (Expression<Boolean>) exprs[10];
		rotation = (Expression<Boolean>) exprs[11];
		this.radius = (Expression<Number>) exprs[12];
		pDensity = (Expression<Long>) exprs[13];
		step = (Expression<Number>) exprs[14];
		range = (Expression<Number>) exprs[15];
		xRot = (Expression<Number>) exprs[16];
		yRot = (Expression<Number>) exprs[17];
		zRot = (Expression<Number>) exprs[18];
		displaceX = (Expression<Number>) exprs[19];
		displaceY = (Expression<Number>) exprs[20];
		displaceZ = (Expression<Number>) exprs[21];
		ticks = (Expression<Long>) exprs[22];
		seconds = (Expression<Long>) exprs[23];
		return true;
	}

	/**
	 * drawComplexCircle 
	 * particle %string%[, material %-itemstack%]
	 * [, speed %-number%]
	 * [, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %entity/location%, 
	 * id %string%, 
	 * [, isSingle %-boolean%, %-player%]
	 * [, r[ainbow]M[ode] %-boolean%], 
	 * randomRotation %boolean%, 
	 * radius %number%, 
	 * density %number%, 
	 * start %number%, 
	 * visibleRange %number%, 
	 * [, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%]
	 * [, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%]
	 * [, tps %-number%, second %-number%]
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawComplexCircle particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, radius %number%, density %number%, start %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]";
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void execute(@Nullable Event e) {
		String particle = "limeglassparticle";
		float finalSpeed = 0;
		float offsetX = 0;
		float offsetY = 0;
		float offsetZ = 0;
		double xRotation = 0;
		double yRotation = 0;
		double zRotation = 0;
		double disX = 0;
		double disY = 0;
		double disZ = 0;
		
		List<Player> p = SkriptHandler.inputPlayers(e, inputPlayers);
		
		
		
		
		
		
		Long finalDelayTicks = (long) 0;
		Long finalDelayBySec = (long) 0;
		//String particle = (String)this.particleString.getSingle(e);
		if (particleString != null){
	    	if (ParticleEffect.NAME_MAP.containsKey(particleString.getSingle(e).toLowerCase()) == true)
				particle = (String)this.particleString.getSingle(e).toLowerCase();
		}
		Object center = entLoc.getSingle(e);
		String idName = (String)this.idName.getSingle(e);
		Long pD = (Long)this.pDensity.getSingle(e);//change the name of this stuff, its not the right thing..
		Integer finalParticleDensity = pD != null ? pD.intValue() : null;
		float finalStep = step.getSingle(e).floatValue();
		double visibleRange = range.getSingle(e).doubleValue();
		boolean rainbowMode = false;
		if (rainbMode != null && rainbMode.getSingle(e) != null){
			rainbowMode = rainbMode.getSingle(e).booleanValue();
		}
		boolean enableRotation = rotation.getSingle(e).booleanValue();
		Number radiusInt = (Number)this.radius.getSingle(e);
		float radius = ((Number)radiusInt).floatValue();
		if(speed != null){
			finalSpeed = speed.getSingle(e).floatValue();	
		}
		if(offX != null && offY != null && offZ != null){
			offsetX = offX.getSingle(e).floatValue();
			offsetY = offY.getSingle(e).floatValue();
			offsetZ = offZ.getSingle(e).floatValue();
		}
		if(displaceX != null && displaceY != null && displaceZ != null){
			disX = displaceX.getSingle(e).doubleValue();
			disY = displaceY.getSingle(e).doubleValue();
			disZ = displaceZ.getSingle(e).doubleValue();
		}
		if(xRot != null && yRot != null && zRot != null){
			xRotation = xRot.getSingle(e).doubleValue();
			yRotation = yRot.getSingle(e).doubleValue();
			zRotation = zRot.getSingle(e).doubleValue();
		}
		if (ticks != null){
			finalDelayTicks = ticks.getSingle(e);
		}
		if (seconds != null){
			finalDelayBySec = seconds.getSingle(e);
		}
		try {
			Material dataMat = data.getSingle(e).getType();
			byte dataID = data.getSingle(e).getData().getData();
			EffectsLib.drawComplexCircle(particle, dataMat, dataID, center, idName, p, rainbowMode, enableRotation, radius, finalSpeed, finalParticleDensity, finalStep, visibleRange, xRotation, yRotation, zRotation, offsetX, offsetY, offsetZ, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
		} catch (Exception ex) {;
			Material dataMatNull = Material.DIRT;
			byte dataIDNull = 0;
			EffectsLib.drawComplexCircle(particle, dataMatNull, dataIDNull, center, idName, p, rainbowMode, enableRotation, radius, finalSpeed, finalParticleDensity, finalStep, visibleRange, xRotation, yRotation, zRotation, offsetX, offsetY, offsetZ, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
		}
	}
}