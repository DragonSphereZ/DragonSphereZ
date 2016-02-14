package ud.bi0.dragonSphereZ.skriptAPI.effects;


import org.bukkit.Location;
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
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.utils.EffectUtils;
import ud.bi0.dragonSphereZ.utils.ParticleEffect;

public class EffSimpleDot extends Effect {
	private Expression<Number> partCount;
	private Expression<String> inputParticleString;
	private Expression<ItemStack> data;
	private Expression<Number> partSpeed;
	private Expression<Number> offX;
	private Expression<Number> offY;
	private Expression<Number> offZ;
	private Expression<?> entLoc;
	private Expression<Player> inputPlayers;
	private Expression<Boolean> isRainbowTrue;
	private Expression<Number> range;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		partCount = (Expression<Number>) exprs[0];
		inputParticleString = (Expression<String>) exprs[1];
		data = (Expression<ItemStack>) exprs[2];
		partSpeed = (Expression<Number>) exprs[3];
		offX = (Expression<Number>) exprs[4];
		offY = (Expression<Number>) exprs[5];
		offZ = (Expression<Number>) exprs[6];
		entLoc = (Expression<?>) exprs[7];
		inputPlayers = (Expression<Player>) exprs[8];
		isRainbowTrue = (Expression<Boolean>) exprs[9];
		range = (Expression<Number>) exprs[10];
		return true;
	}
	
	/**
	 * drawDot 
	 * [count %-number%], 
	 * particle %string%[, material %-itemstack%]
	 * [, speed %-number%]
	 * [, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %locations/entities%, 
	 * [, isSingle %-boolean%, %-player%]
	 * [, r[ainbow]M[ode] %-boolean%], 
	 * visibleRange %number%, 
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawDot [count %-number%], particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %locations/entities%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%";
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void execute(@Nullable Event e) {
		float offsetX = 0;
		float offsetY = 0;
		float offsetZ = 0;
		float speed = 0;
		
		int count = SkriptHandler.inputParticleCount(e, partCount);
		List<Player> p = SkriptHandler.inputPlayers(e, inputPlayers);
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, isRainbowTrue);

		
		if(offX != null && offY != null && offZ != null){
			offsetX = offX.getSingle(e).floatValue();
			offsetY = offY.getSingle(e).floatValue();
			offsetZ = offZ.getSingle(e).floatValue();
		}
		
		if(partSpeed != null){
			speed = partSpeed.getSingle(e).floatValue();	
		}
		
		double visibleRange = range.getSingle(e).doubleValue();
		Material dataMat = Material.DIRT;
		byte dataID = 0;
		if(data != null){
			dataMat = data.getSingle(e).getType();
			dataID = data.getSingle(e).getData().getData();
		}
		
		Object[] center = (Object[])entLoc.getAll(e);
		//Location[] loc = (Location[])entLoc.getAll(e);
		//Location location = null;
		for (final Object loc : center) {
			//if (loc instanceof Entity) {
			//	location = ((Entity) loc).getLocation();
			//}else if (loc instanceof Location){
			//	location = new Location(((Location) loc).getWorld(), ((Location) loc).getX(), ((Location) loc).getY(), ((Location) loc).getZ());
			//}
			Location location = EffectUtils.getLocation(loc);
			float hue = 0;
			if (rainbowMode == true)
				
				hue += 0.01F;
				hue = (hue >= 1.0F ? 0.0F : hue);
			ParticleEffect.valueOf(particle).display(dataMat, dataID, p, location, visibleRange, rainbowMode, offsetX, offsetY, offsetZ, speed, count);
		//EffectsLib.drawSimpleDot(count, particle, dataMat, dataID, speed, offsetX, offsetY, offsetZ, center, isSinglePlayer, p, rainbowMode, visibleRange, delayTicks, delayBySecond);

        }

	}
}