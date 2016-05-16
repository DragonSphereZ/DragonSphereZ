package ud.bi0.dragonSphereZ.skriptAPI.effect;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.flowpowered.math.vector.Vector3d;

import java.awt.Font;
import java.util.List;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.effect.other.Text;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class EffText extends Effect {
	private Expression<String> inputText;
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
	private Expression<Boolean> inputAutoFace;
	private Expression<Boolean> inputInvert;
	private Expression<String> inputFontName;
	private Expression<String> inputFontStyle;
	private Expression<Number> inputFontSize;
//	private Expression<Number> pixelX;
//	private Expression<Number> pixelY;
	private Expression<Long> scale;
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
		inputText = (Expression<String>) exprs[0];
		inputParticleString = (Expression<String>) exprs[1];
		inputParticleData = (Expression<ItemStack>) exprs[2];
		inputParticleSpeed = (Expression<Number>) exprs[3];
		offX = (Expression<Number>) exprs[4];
		offY = (Expression<Number>) exprs[5];
		offZ = (Expression<Number>) exprs[6];
		entLoc = (Expression<Object>) exprs[7];
		inputIdName = (Expression<String>) exprs[8];
		inputPlayers = (Expression<Player>) exprs[9];
		inputRainbowMode = (Expression<Boolean>) exprs[10];
		inputAutoFace = (Expression<Boolean>) exprs[11];
		inputInvert = (Expression<Boolean>) exprs[12];
		inputFontName = (Expression<String>) exprs[13];
		inputFontStyle = (Expression<String>) exprs[14];
		inputFontSize = (Expression<Number>) exprs[15];
//		pixelX = (Expression<Number>) exprs[16];
//		pixelY = (Expression<Number>) exprs[17];
		scale = (Expression<Long>) exprs[16];
		range = (Expression<Number>) exprs[17];
		xRot = (Expression<Number>) exprs[18];
		yRot = (Expression<Number>) exprs[19];
		zRot = (Expression<Number>) exprs[20];
		displaceX = (Expression<Number>) exprs[21];
		displaceY = (Expression<Number>) exprs[22];
		displaceZ = (Expression<Number>) exprs[23];
		inputPulseTick = (Expression<Number>) exprs[24];
		
		return true;
	}

	/**
	 * drawText %string%, 
	 * particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], 
	 * center %entity/location%, 
	 * center %object%, 
	 * id %string%
	 * [, onlyFor %-players%]
	 * [, r[ainbow]M[ode] %-boolean%]
	 * [, autoFace %-boolean%]
	 * [, invert %-boolean%]
	 * [, f[ont]Name %-string%, f[ont]Style %-string%, f[ont]Size %-number%], 
//	 * pixelStepX %number%, 
//	 * pixelStepY %number%, 
	 * scale %number%, 
	 * visibleRange %number%
	 * [, Rot[ation]XYZ %-number%, %-number%, %-number%]
	 * [, dis[placement]XYZ %-number%, %-number%, %-number%]
	 * [, pulseDelay %-number%]
	*/
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawText %string%, particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%][, autoFace %-boolean%][, invert %-boolean%][, f[ont]Name %-string%, f[ont]Style %-string%, f[ont]Size %-number%], scale %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]" ;
	}

	@Override
	protected void execute(@Nullable Event e) {
		DynamicLocation center;
		try {
			center = DynamicLocation.init(entLoc.getSingle(e));
		} catch (IllegalArgumentException ex) {
			return;
		}
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		Vector3d offset = SkriptHandler.inputParticleOffset(e, offX, offY, offZ);
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, inputRainbowMode);
		boolean autoFace = SkriptHandler.inputRainbowMode(e, inputAutoFace);
		boolean invert = SkriptHandler.inputRainbowMode(e, inputInvert);
		Vector3d displacement = SkriptHandler.inputLocDisplacement(e, displaceX, displaceY, displaceZ);
		String idName = inputIdName.getSingle(e);
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		double visibleRange = range.getSingle(e).doubleValue();
		Vector3d axis = SkriptHandler.inputEffectRotationOld(e, xRot, yRot, zRot);
		
		Long scaleSize = (long) 5;
		//int pixelStepX = 1;
		//int pixelStepY = 1;
		//if (pixelX != null){
		//	pixelStepX = pixelX.getSingle(e).intValue();
		//}
		//if (pixelY != null){
		//	pixelStepY = pixelY.getSingle(e).intValue();
		//}
		if (scale != null){
			scaleSize = scale.getSingle(e).longValue();
		}
		
		Font font = SkriptHandler.inputFont(e, inputFontName, inputFontStyle, inputFontSize);
		String text = inputText.getSingle(e);
		
		int finalPulseTick = SkriptHandler.inputPulseTick(e, inputPulseTick);
		
		Text string = new Text(1, particle, dataMat, dataID, finalSpeed, offset, idName, center, players, rainbowMode, visibleRange, displacement, 0, finalPulseTick, axis, 1, 1, scaleSize, font, text, invert, autoFace);
		string.start(string);
	}
}