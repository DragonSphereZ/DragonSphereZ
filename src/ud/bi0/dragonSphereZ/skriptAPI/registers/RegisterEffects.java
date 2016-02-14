package ud.bi0.dragonSphereZ.skriptAPI.registers;

import ch.njol.skript.Skript;
import ud.bi0.dragonSphereZ.skriptAPI.effects.*;

public class RegisterEffects {

	public static void DragonSphereZ() {
		Skript.registerEffect(EffSimpleDot.class, new String[] { "drawDot [count %-number%], particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %locations/entities%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%" } );
		Skript.registerEffect(EffComplexCircle.class, new String[] { "drawComplexCircle particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %locations/entitys%, id %string%[, onlyFor %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, radius %number%, density %number%, start %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, [pulse]Delay %-number%]" } );
	}
}