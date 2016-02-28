package ud.bi0.dragonSphereZ.skriptAPI.registers;

import ch.njol.skript.Skript;
import ud.bi0.dragonSphereZ.skriptAPI.effects.*;

public class RegisterEffects {

	public static void DragonSphereZ() {
		Skript.registerEffect(EffStopEffect.class, new String[] {"stop effect with id %string%"});
		Skript.registerEffect(EffStopEffect.class, new String[] {"stopEffect[ id] %string%"});
		Skript.registerEffect(EffStopAllEffects.class, "stop all[ particle] effects[ targeting %entities%]");
		Skript.registerEffect(EffSimpleDot.class, new String[] { "drawDot [count %-number%], particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %locations/entities%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%" } );
		Skript.registerEffect(EffSimpleEffect.class, new String[] {"draw simple effect %string% with particle %string% and id %string% at %entity/location%[ for %players%]"});
		Skript.registerEffect(EffComplexCircle.class, new String[] { "drawComplexCircle particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, radius %number%, density %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, [pulse]Delay %-number%]" } );
		Skript.registerEffect(EffComplexSpiral.class, new String[] { "drawComplexSpiral particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%][, clockwise %-boolean%][, scan %-boolean%], radius %number%, density %number%, height %number%, effectMod %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]" } );

	}
	public static void BetaTests() {
		
	}
}