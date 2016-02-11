package ud.bi0.dragonSphereZ.skriptAPI.expressions.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSphereLoc extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Number> phi;
	private Expression<Number> the;
	private Expression<Number> r;
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
		phi = (Expression<Number>) expr[1];
		the = (Expression<Number>) expr[2];
		r = (Expression<Number>) expr[3];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		return new TrigLib().sphloc(loc.getArray(e), phi.getSingle(e).doubleValue(), the.getSingle(e).doubleValue(), r.getSingle(e).doubleValue());
	}

}
