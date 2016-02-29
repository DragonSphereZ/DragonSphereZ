package ud.bi0.dragonSphereZ.skriptAPI.expression;

import java.util.Set;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.DragonSphereCore;

public class ExprAllEffects extends SimpleExpression<String> {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[all ][active ]particle effects";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		Set<String> keySet = DragonSphereCore.effectManager.getKeys();
		String[] keys = new String[keySet.size()];
		keySet.toArray(keys);
		return keys;
	}

}
