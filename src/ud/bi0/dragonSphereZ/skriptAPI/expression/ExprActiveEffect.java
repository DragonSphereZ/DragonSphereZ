package ud.bi0.dragonSphereZ.skriptAPI.expression;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.DragonSphereCore;

public class ExprActiveEffect extends SimpleExpression<Boolean>{

	Expression<String> idName;
	
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		idName = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[particle ]effect %string% is active";
	}

	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		return new Boolean[]{DragonSphereCore.effectManager.isActive(idName.getSingle(e))};
	}

}
