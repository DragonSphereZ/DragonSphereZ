package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprHelix extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Number> r;
	private Expression<Number> h;
	private Expression<Number> k;
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
		h = (Expression<Number>) expr[2];
		k = (Expression<Number>) expr[3];
		d = (Expression<Number>) expr[4];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		return new BiosphereTrigLib().getHelix(loc.getArray(e), r.getSingle(e).doubleValue(), h.getSingle(e).doubleValue(), k.getSingle(e).doubleValue()	, d.getSingle(e).doubleValue());
	}

}
