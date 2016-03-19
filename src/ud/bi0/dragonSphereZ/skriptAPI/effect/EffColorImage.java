package ud.bi0.dragonSphereZ.skriptAPI.effect;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.flowpowered.math.vector.Vector3d;

import java.io.File;
import java.util.List;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.DragonSphereCore;
import ud.bi0.dragonSphereZ.effect.other.ColorImage;
import ud.bi0.dragonSphereZ.effect.other.ColorImage.Plane;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.util.DynamicLocation;


public class EffColorImage extends Effect {
	private Expression<String> fileName;
	private Expression<Object> entLoc;
	private Expression<String> inputIdName;
	private Expression<Player> inputPlayers;
	private Expression<Boolean> inputRotationMode;
	private Expression<String> inputPlaneString;
	private Expression<Number> pixelX;
	private Expression<Number> pixelY;
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
		fileName = (Expression<String>) exprs[0];
		entLoc = (Expression<Object>) exprs[1];
		inputIdName = (Expression<String>) exprs[2];
		inputPlayers = (Expression<Player>) exprs[3];
		inputRotationMode = (Expression<Boolean>) exprs[4];
		inputPlaneString = (Expression<String>) exprs[5];
		pixelX = (Expression<Number>) exprs[6];
		pixelY = (Expression<Number>) exprs[7];
		scale = (Expression<Long>) exprs[8];
		range = (Expression<Number>) exprs[9];
		xRot = (Expression<Number>) exprs[10];
		yRot = (Expression<Number>) exprs[11];
		zRot = (Expression<Number>) exprs[12];
		displaceX = (Expression<Number>) exprs[13];
		displaceY = (Expression<Number>) exprs[14];
		displaceZ = (Expression<Number>) exprs[15];
		inputPulseTick = (Expression<Number>) exprs[16];
		return true;
	}

	/**
	 * drawImage 
	 * file %string%, 
	 * center %entity/location%, 
	 * center %object%, 
	 * id %string%
	 * [, onlyFor %-players%]
	 * [, randomRotation %-boolean%,[ plane] %-string%], 
	 * pixelStepX %number%, 
	 * pixelStepY %number%, 
	 * scale %number%, 
	 * visibleRange %number%
	 * [, Rot[ation]XYZ %-number%, %-number%, %-number%]
	 * [, dis[placement]XYZ %-number%, %-number%, %-number%]
	 * [, pulseDelay %-number%]
	*/
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawImage file %string%, center %object%, id %string%[, onlyFor %-players%][, randomRotation %-boolean%,[ plane] %-string%], pixelStepX %number%, pixelStepY %number%, scale %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]" ;
	}

	@Override
	protected void execute(@Nullable Event e) {
		DynamicLocation center;
		try {
			center = DynamicLocation.init(entLoc.getSingle(e));
		} catch (IllegalArgumentException ex) {
			return;
		}
		Vector3d displacement = SkriptHandler.inputLocDisplacement(e, displaceX, displaceY, displaceZ);
		String idName = inputIdName.getSingle(e);
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		double visibleRange = range.getSingle(e).doubleValue();
		Vector3d axis = SkriptHandler.inputEffectRotation(e, xRot, yRot, zRot);
		boolean enableRotation = SkriptHandler.inputRotationMode(e, inputRotationMode);

		Plane plane = ColorImage.Plane.XYZ;
		if (inputPlaneString != null){
			String planes = inputPlaneString.getSingle(e).toUpperCase();
			if (planes.equalsIgnoreCase("X") ||planes.equalsIgnoreCase("Y") || planes.equalsIgnoreCase("Z") || planes.equalsIgnoreCase("XY") || planes.equalsIgnoreCase("XZ") || planes.equalsIgnoreCase("XYZ") || planes.equalsIgnoreCase("YZ"))
				plane = ColorImage.Plane.valueOf(planes);
		}
		
		
		
		Long scaleSize = (long) 40;
		int pixelStepX = 10;
		int pixelStepY = 10;
		String finalFileName = null;
		File file = null;
		if (fileName != null){
			finalFileName = (String)fileName.getSingle(e);
	        if (!finalFileName.startsWith(File.pathSeparator)) {
	            file = new File(DragonSphereCore.dragonSphereCore.getDataFolder().getAbsolutePath(), finalFileName);
	        } else {
	            file = new File(finalFileName);
	        }
		}
		
		if (pixelX != null){
			pixelStepX = pixelX.getSingle(e).intValue();
		}
		if (pixelY != null){
			pixelStepY = pixelY.getSingle(e).intValue();
		}
		if (scale != null){
			scaleSize = scale.getSingle(e).longValue();
		}
		
		
		
		int finalPulseTick = SkriptHandler.inputPulseTick(e, inputPulseTick);
		
		
		
		//(String idName, String particle, DynamicLocation center, List<Player> players, long delayTick, long pulseTick, int particleCount, Material dataMat, byte dataID, float speed, double visibleRange, boolean rainbowMode, Vector3d offset, Vector3d displacement, Vector3d axis, boolean enableRotation, Plane plane, File file, int pixelStepX, int pixelStepY, float scaleSize)
		ColorImage image = new ColorImage(idName, "redstone", center, players, 0, finalPulseTick, 1, Material.DIRT, (byte)0, 0, visibleRange, false, new Vector3d(), displacement, axis, enableRotation, plane, file, pixelStepX, pixelStepY, scaleSize);
		image.start(image);
	}
}