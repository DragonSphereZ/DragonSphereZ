package ud.bi0.dragonSphereZ.skriptAPI.effects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import ud.bi0.dragonSphereZ.particles.EffectsLib;
import ud.bi0.dragonSphereZ.utils.ParticleEffect;

public class EffSimpleDot extends Effect {
	private Expression<Number> partCount;
	private Expression<String> particleString;
	private Expression<ItemStack> data;
	private Expression<Number> partSpeed;
	private Expression<Number> offX;
	private Expression<Number> offY;
	private Expression<Number> offZ;
	private Expression<?> entLoc;
	private Expression<Boolean> singlePlayer;
	private Expression<Player> player;
	private Expression<Boolean> rainbMode;
	private Expression<Number> range;
	private Expression<Long> ticks;
	private Expression<Long> seconds;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		partCount = (Expression<Number>) exprs[0];
		particleString = (Expression<String>) exprs[1];
		data = (Expression<ItemStack>) exprs[2];
		partSpeed = (Expression<Number>) exprs[3];
		offX = (Expression<Number>) exprs[4];
		offY = (Expression<Number>) exprs[5];
		offZ = (Expression<Number>) exprs[6];
		entLoc = (Expression<?>) exprs[7];
		singlePlayer = (Expression<Boolean>) exprs[8];
		player = (Expression<Player>) exprs[9];
		rainbMode = (Expression<Boolean>) exprs[10];
		range = (Expression<Number>) exprs[11];
		ticks = (Expression<Long>) exprs[12];
		seconds = (Expression<Long>) exprs[13];
		return true;
	}
	
	/**
	 * drawDot 
	 * [count %-number%], 
	 * particle %string%[, material %-itemstack%]
	 * [, speed %-number%]
	 * [, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %entity/location%, 
	 * [, isSingle %-boolean%, %-player%]
	 * [, r[ainbow]M[ode] %-boolean%], 
	 * visibleRange %number%, 
	 * [, tps %-number%, second %-number%]
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawDot [count %-number%], particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%[, tps %-number%, second %-number%]";
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void execute(@Nullable Event e) {
		String particle = "limeglassparticle";
		float offsetX = 0;
		float offsetY = 0;
		float offsetZ = 0;
		float speed = 0;
		Long delayTicks = (long) 0;
		Long delayBySecond = (long) 0;
		int count = 1;
		if(partCount.getSingle(e) != null){
			count = partCount.getSingle(e).intValue();
		}
		if (particleString != null){
	    	if (ParticleEffect.NAME_MAP.containsKey(particleString.getSingle(e).toLowerCase()) == true)
				particle = (String)this.particleString.getSingle(e).toLowerCase();
		}
		Object center = entLoc.getSingle(e);
		Player p = null;
		boolean isSinglePlayer = false;
		if (singlePlayer != null && singlePlayer.getSingle(e) != null && this.player != null && this.player.getSingle(e) != null){
			isSinglePlayer = singlePlayer.getSingle(e).booleanValue();
			p = (Player)this.player.getSingle(e);
		}
		boolean rainbowMode = rainbMode.getSingle(e).booleanValue();
		if(offX != null && offY != null && offZ != null){
			offsetX = offX.getSingle(e).floatValue();
			offsetY = offY.getSingle(e).floatValue();
			offsetZ = offZ.getSingle(e).floatValue();
		}
		if(partSpeed.getSingle(e) != null){
			speed = partSpeed.getSingle(e).floatValue();	
		}
		double visibleRange = range.getSingle(e).doubleValue();
		if (ticks != null){
			delayTicks = ticks.getSingle(e);
		}
		if (seconds != null){
			delayBySecond = seconds.getSingle(e);
		}
		Material dataMat = Material.DIRT;
		byte dataID = 0;
		if(data != null){
			dataMat = data.getSingle(e).getType();
			dataID = data.getSingle(e).getData().getData();
		}
		
		EffectsLib.drawSimpleDot(count, particle, dataMat, dataID, speed, offsetX, offsetY, offsetZ, center, isSinglePlayer, p, rainbowMode, visibleRange, delayTicks, delayBySecond);

	}
}