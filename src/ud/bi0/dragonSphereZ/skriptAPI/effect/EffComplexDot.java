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
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.effect.complex.ComplexDot;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class EffComplexDot extends Effect {
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
	private Expression<Number> inputPulseDelay;
	private Expression<Timespan> inputKeepDelay;
	
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
		inputPulseDelay = (Expression<Number>) exprs[11];
		inputKeepDelay = (Expression<Timespan>) exprs[12];
		return true;
	}
	
	/**
	 * drawDot 
	 * [ count %-number%,] 
	 * particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %objects%, 
	 * [, onlyFor %-players%]
	 * [, r[ainbow]M[ode] %-boolean%], 
	 * visibleRange %number%, 
	 * [, pulseDelay %-number%]
	 * [, keepFor %-timespan%]
	*/
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawDot[[ count] %-number%,] particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %objects%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%[, pulseDelay %-number%][, keepFor %-timespan%]";
	}

	@Override
	protected void execute(@Nullable Event e) {
		DynamicLocation center;
		try {
			center = DynamicLocation.init(entLoc.getSingle(e));	//TODO change this to match the simple dot locations
		} catch (IllegalArgumentException ex) {
			return;
		}
		int count = SkriptHandler.inputParticleCount(e, partCount);
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, isRainbowTrue);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		Vector3d offset = SkriptHandler.inputParticleOffset(e, offX, offY, offZ);
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
	    int finalPulseTick = SkriptHandler.inputPulseTick(e, inputPulseDelay);
	    int finalKeepDelay = SkriptHandler.inputKeepDelay(e, inputKeepDelay);
	    double visibleRange = range.getSingle(e).doubleValue();
	    String idName = "&dot-" + Math.random() + "-&dot"; //TODO Change this maybe
	    if (finalPulseTick > finalKeepDelay){
	    	finalPulseTick = finalKeepDelay;
	    }
	    //(String idName, String particle, DynamicLocation center, List<Player> players, long delayTick, long pulseTick, int particleCount, Material dataMat, byte dataID, float speed, double visibleRange, Vector3d offset, Vector3d displacement, boolean rainbowMode)
	    ComplexDot dot = new ComplexDot(count, particle, dataMat, dataID, finalSpeed, offset, idName, center, players, rainbowMode, visibleRange, new Vector3d(0,0,0), finalKeepDelay, finalPulseTick);
	    //dot.start(dot);
	    dot.startUndelayed(dot);
	    dot.stopDelayed(dot);
	}
	
}