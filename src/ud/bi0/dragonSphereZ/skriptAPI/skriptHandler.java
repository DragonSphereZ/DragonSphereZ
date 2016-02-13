package ud.bi0.dragonSphereZ.skriptAPI;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;

public class skriptHandler {
	
	/**
	 * Checks if all expressions are non-null.
	 * 
	 */
	public static boolean hasNull(@Nullable Event e, @Nullable Expression<?>...args) {
		for (Expression<?> arg : args) {
			if (arg == null) return true;
			if (arg.isSingle()) {
				if (arg.getSingle(e) == null) return true;
			} else if (arg.getAll(e).length == 0 || arg.getAll(e) == null) return true; 
		}
		return false;
	}
}
