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
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.effect.complex.ComplexAtom;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.util.DynamicLocation;


public class EffComplexAtom extends Effect {
	private Expression<String> inputParticleString;
	private Expression<ItemStack> inputParticleData;
	private Expression<Number> inputParticleSpeed;
	private Expression<Number> offX;
	private Expression<Number> offY;
	private Expression<Number> offZ;
	private Expression<String> inputParticleString2;
	private Expression<ItemStack> inputParticleData2;
	private Expression<Number> inputParticleSpeed2;
	private Expression<Number> offX2;
	private Expression<Number> offY2;
	private Expression<Number> offZ2;
	private Expression<Object> entLoc;
	private Expression<String> inputIdName;
	private Expression<Player> inputPlayers;
	private Expression<Boolean> inputRainbowMode;
	private Expression<Boolean> inputRotationMode;
	private Expression<Number> inputRadius;
	private Expression<Number> inputOrbitParticles;
	private Expression<Number> inputOrbitalCount;
	private Expression<Number> inputNucleusDensity;
	private Expression<Number> range;
	private Expression<Number> inputManualYRotation;
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
		inputParticleString2 = (Expression<String>) exprs[6];
		inputParticleData2 = (Expression<ItemStack>) exprs[7];
		inputParticleSpeed2 = (Expression<Number>) exprs[8];
		offX2 = (Expression<Number>) exprs[9];
		offY2 = (Expression<Number>) exprs[10];
		offZ2 = (Expression<Number>) exprs[11];
		entLoc = (Expression<Object>) exprs[12];
		inputIdName = (Expression<String>) exprs[13];
		inputPlayers = (Expression<Player>) exprs[14];
		inputRainbowMode = (Expression<Boolean>) exprs[15];
		inputRotationMode = (Expression<Boolean>) exprs[16];
		inputRadius = (Expression<Number>) exprs[17];
		inputNucleusDensity = (Expression<Number>) exprs[18];
		inputOrbitalCount = (Expression<Number>) exprs[19];
		inputOrbitParticles = (Expression<Number>) exprs[20];
		range = (Expression<Number>) exprs[21];
		inputManualYRotation = (Expression<Number>) exprs[22];
		displaceX = (Expression<Number>) exprs[23];
		displaceY = (Expression<Number>) exprs[24];
		displaceZ = (Expression<Number>) exprs[25];
		inputPulseTick = (Expression<Number>) exprs[26];
		return true;
	}

   /**
	* drawComplexAtom 
	* particle1 %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%]
	* particle2 %string%[, material2 %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%]
	* center %entity/location%, 
	* center %object%,
	* id %string%, 
	* [, onlyFor %-player%]
	* [, r[ainbow]M[ode] %-boolean%], 
	* randomRotation %boolean%, 
	* nucleusCount %number%, 
	* nucleusRadius %number%,
	* outerPCount %number%, 
	* orbitals %number%, 
	* visibleRange %number%, 
	* [, yRot[ation] %-number%]
	* [, dis[placement]XYZ %-number%, %-number%, %-number%]
	* [, pulseDelay %-number%]
   */
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "drawComplexAtom particle1 %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[, material2 %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], center %object%, id %string%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, nucleusCount %number%, nucleusRadius %number%, outerPCount %number%, orbitals %number%, visibleRange %number%[, yRot[ation] %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
	}

	@Override
	protected void execute(@Nullable Event e) {
		//DynamicLocation center = DynamicLocation.init(entLoc.getSingle(e));
		DynamicLocation center;
		try {
			center = DynamicLocation.init(entLoc.getSingle(e));
		} catch (IllegalArgumentException ex) {
			return;
		}
		String particle = SkriptHandler.inputParticleString(e, inputParticleString);
		float finalSpeed = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed);
		Vector3d offset = SkriptHandler.inputParticleOffset(e, offX, offY, offZ);
		String particle2 = SkriptHandler.inputParticleString(e, inputParticleString2);
		float finalSpeed2 = SkriptHandler.inputParticleSpeed(e, inputParticleSpeed2);
		Vector3d offset2 = SkriptHandler.inputParticleOffset(e, offX2, offY2, offZ2);
		List<Player> players = SkriptHandler.inputPlayers(e, inputPlayers);
		boolean rainbowMode = SkriptHandler.inputRainbowMode(e, inputRainbowMode);
		Vector3d displacement = SkriptHandler.inputLocDisplacement(e, displaceX, displaceY, displaceZ);
		
		boolean enableRotation = SkriptHandler.inputRotationMode(e, inputRotationMode);
		float finalRadius = SkriptHandler.inputRadius(e, inputRadius);
		int finalInnerParticleDensity = SkriptHandler.inputNucleusDensity(e, inputNucleusDensity);
		int finalOrbitParticles = SkriptHandler.inputParticleDensity(e, inputOrbitParticles);
		int finalOrbitalCount = SkriptHandler.inputOrbitalCount(e, inputOrbitalCount);
		double manualYRotation = SkriptHandler.inputEffectRotation(e, inputManualYRotation);

		String idName = inputIdName.getSingle(e);
		double visibleRange = range.getSingle(e).doubleValue();
		Long finalPulseTick = SkriptHandler.inputPulseTick(e, inputPulseTick);
		Material dataMat = SkriptHandler.inputParticleDataMat(e, inputParticleData);
		byte dataID = SkriptHandler.inputParticleDataID(e, inputParticleData);
		Material dataMat2 = SkriptHandler.inputParticleDataMat(e, inputParticleData2);
		byte dataID2 = SkriptHandler.inputParticleDataID(e, inputParticleData2);

		new ComplexAtom(idName, particle, center, players, 0L, finalPulseTick, 1, dataMat, dataID, finalSpeed, visibleRange, offset, displacement, rainbowMode, enableRotation, finalRadius, finalInnerParticleDensity, finalOrbitParticles, finalOrbitalCount, manualYRotation, particle2, dataMat2, dataID2, finalSpeed2, offset2).start();
		
	}
}