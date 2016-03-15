package ud.bi0.dragonSphereZ.skriptAPI.effect;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class EffSimpleDot extends Effect {
	private Expression<Number> partCount;
	private Expression<String> inputParticleString;
	private Expression<ItemStack> inputParticleData;
	private Expression<Number> inputParticleSpeed;
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
		inputParticleData = (Expression<ItemStack>) exprs[2];
		inputParticleSpeed = (Expression<Number>) exprs[3];
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
	 * [ count %-number%,] 
	 * particle %string%[, material %-itemstack%]
	 * [, speed %-number%]
	 * [, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %locations/entities%, 
	 * [, onlyFor %-player%]
	 * [, r[ainbow]M[ode] %-boolean%], 
	 * visibleRange %number%, 
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawDot[ count %-number%,] particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %locations/entities%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%";
	}

	@Override
	protected void execute(@Nullable Event e) {
		int count = SkriptHandler.inputParticleCount(e, partCount);
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, isRainbowTrue);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		float offsetX = SkriptHandler.inputParticleOffset(e, offX);
		float offsetY = SkriptHandler.inputParticleOffset(e, offY);
		float offsetZ = SkriptHandler.inputParticleOffset(e, offZ);
		double visibleRange = range.getSingle(e).doubleValue();
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
		String idName = "&dot-" + Math.random() + "-&dot";
		Object[] center = (Object[])entLoc.getAll(e);
		for (final Object loc : center) {
			Location location = getLocation(loc);
			ParticleEffectUtils.valueOf(particle).display(idName, dataMat, dataID, players, location, visibleRange, rainbowMode, offsetX, offsetY, offsetZ, finalSpeed, count);
			//ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, location, visibleRange, rainbowMode, offsetX, offsetY, offsetZ, finalSpeed, count);
        }

	}
	public static Location getLocation(Object location) {	//QuickFix
    	
		if (location instanceof Entity) {
			return ((Entity) location).getLocation();
		}
		else if (location instanceof Location){
			return (Location) location;
		}
		return null;
    }
}