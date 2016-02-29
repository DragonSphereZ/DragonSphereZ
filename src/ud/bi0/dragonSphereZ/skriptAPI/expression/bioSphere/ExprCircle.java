package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprCircle extends SimpleExpression<Location> {
	private Expression<Location> loc;
	private Expression<Number> r;
	private Expression<Number> d;
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		loc = (Expression<Location>) expr[0];
		r = (Expression<Number>) expr[1];
		d = (Expression<Number>) expr[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		int n = (int) (r.getSingle(e).doubleValue() * 2 * Math.PI * d.getSingle(e).doubleValue());
		return new BiosphereTrigLib().getPoly(loc.getArray(e), n, r.getSingle(e).doubleValue());
	}

}
