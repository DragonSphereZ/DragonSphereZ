package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprReflection extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Location> point;
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
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		loc = (Expression<Location>) expr[0];
		point = (Expression<Location>) expr[1];
		x = (Expression<Number>) expr[2];
		y = (Expression<Number>) expr[3];
		z = (Expression<Number>) expr[4];
		
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		if(x.getSingle(e) == null) return new TrigLib().ptrefl(loc.getArray(e), point.getSingle(e));
		else return new TrigLib().refl(loc.getArray(e), point.getSingle(e), new Location(point.getSingle(e).getWorld(),x.getSingle(e).doubleValue(),y.getSingle(e).doubleValue(),z.getSingle(e).doubleValue()));
	}

}
