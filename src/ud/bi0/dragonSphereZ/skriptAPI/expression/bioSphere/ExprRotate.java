package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprRotate extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Location> center;
	private Expression<Number> x;
	private Expression<Number> y;
	private Expression<Number> z;
	private Expression<Number> phi;
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
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, SkriptParser.ParseResult parseResult) {
		loc = (Expression<Location>) expr[0];
		center = (Expression<Location>) expr[1];
		x = (Expression<Number>) expr[2];
		y = (Expression<Number>) expr[3];
		z = (Expression<Number>) expr[4];
		phi = (Expression<Number>) expr[5];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		return new TrigLib().rot(loc.getArray(e), center.getSingle(e), new Location(center.getSingle(e).getWorld(),y.getSingle(e).doubleValue(),z.getSingle(e).doubleValue(),x.getSingle(e).doubleValue()), phi.getSingle(e).doubleValue()); 
	}

}
