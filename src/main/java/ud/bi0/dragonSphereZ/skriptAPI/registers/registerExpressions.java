package ud.bi0.dragonSphereZ.skriptAPI.registers;

import org.bukkit.Location;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ud.bi0.dragonSphereZ.skriptAPI.expressions.*;



public class registerExpressions {

	public static void DragonSphereZ() {
		Skript.registerExpression(ExprCircle.class, Location.class, ExpressionType.SIMPLE, "testcircle at %location% with radius %number% and density %number%");

	}
}
