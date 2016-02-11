package ud.bi0.dragonSphereZ.skriptAPI.registers;

import ch.njol.skript.Skript;
import ud.bi0.dragonSphereZ.skriptAPI.effects.*;

public class registerEffects {

	public static void DragonSphereZ() {
		Skript.registerEffect(EffSimpleDot.class, new String[] { "drawDot [count %-number%], particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], visibleRange %number%[, tps %-number%, second %-number%]" } );
	}
}