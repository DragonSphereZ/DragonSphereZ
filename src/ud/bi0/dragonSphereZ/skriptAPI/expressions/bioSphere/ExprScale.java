package ud.bi0.dragonSphereZ.skriptAPI.expressions.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprScale extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Location> center;
	private Expression<Number> p;
	private Expression<Number> x;
	private Expression<Number> y;
	private Expression<Number> z;
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
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult parseResult) {
		loc = (Expression<Location>) expr[0];
		center = (Expression<Location>) expr[1];
		p = (Expression<Number>) expr[2];
		x = (Expression<Number>) expr[3];
		y = (Expression<Number>) expr[4];
		z = (Expression<Number>) expr[5];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		double pp = p.getSingle(e).doubleValue();
		if(x.getSingle(e) == null) return new TrigLib().scale(loc.getArray(e), center.getSingle(e), p.getSingle(e).doubleValue());
		else return new TrigLib().cdirscale(loc.getArray(e), center.getSingle(e), x.getSingle(e).doubleValue() * pp, y.getSingle(e).doubleValue() * pp, z.getSingle(e).doubleValue() * pp);
	}	

}
