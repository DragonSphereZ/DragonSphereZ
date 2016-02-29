package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprToRadian extends SimpleExpression<Number>{
	private Expression<Number> deg;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		deg = (Expression<Number>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "radian";
	}

	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Double[]{ deg.getSingle(e).doubleValue() * Math.PI / 180.0};
	}
	
}
