package ud.bi0.dragonSphereZ.skriptAPI.effects;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

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
		String particle = "limeglassparticle";
		float offsetX = 0;
		float offsetY = 0;
		float offsetZ = 0;
		float speed = 0;
		int count = 1;
		
		if(partCount != null){
			count = partCount.getSingle(e).intValue();
		}
		if (particleString != null){
	    	if (ParticleEffect.NAME_MAP.containsKey(particleString.getSingle(e).toLowerCase()) == true)
				particle = (String)this.particleString.getSingle(e).toLowerCase();
		}
		
		Player p = null;
		boolean isSinglePlayer = false;
		if (singlePlayer != null && singlePlayer.getSingle(e) != null && this.player != null && this.player.getSingle(e) != null){
			isSinglePlayer = singlePlayer.getSingle(e).booleanValue();
			p = (Player)this.player.getSingle(e);
		}
		boolean rainbowMode = false;
		if (rainbMode != null && rainbMode.getSingle(e) != null){
			rainbowMode = rainbMode.getSingle(e).booleanValue();
		}
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
		Location location = null;
		for (final Object loc : center) {
			if (loc instanceof Entity) {
				location = ((Entity) loc).getLocation();
			}else if (loc instanceof Location){
				location = new Location(((Location) loc).getWorld(), ((Location) loc).getX(), ((Location) loc).getY(), ((Location) loc).getZ());
			}
			float hue = 0;
			if (rainbowMode == true)
				
				hue += 0.01F;
				hue = (hue >= 1.0F ? 0.0F : hue);
			ParticleEffect.valueOf(particle).display(dataMat, dataID, p, location, visibleRange, isSinglePlayer, rainbowMode, offsetX, offsetY, offsetZ, speed, count);
		//EffectsLib.drawSimpleDot(count, particle, dataMat, dataID, speed, offsetX, offsetY, offsetZ, center, isSinglePlayer, p, rainbowMode, visibleRange, delayTicks, delayBySecond);

        }

	}
}