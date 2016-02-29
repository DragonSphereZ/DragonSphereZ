package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprRotXYZ extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Location> center;
	private Expression<Number> phi;
	private int axis;
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
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, SkriptParser.ParseResult parseResult) {
		loc = (Expression<Location>) expr[0];
		center = (Expression<Location>) expr[1];
		phi = (Expression<Number>) expr[2];
		axis = parseResult.mark;
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		if (axis == 1) return new BiosphereTrigLib().xrot(loc.getArray(e), center.getSingle(e), phi.getSingle(e).doubleValue());
		else if(axis == 2) return new BiosphereTrigLib().yrot(loc.getArray(e), center.getSingle(e), phi.getSingle(e).doubleValue());
		else return new BiosphereTrigLib().zrot(loc.getArray(e), center.getSingle(e), phi.getSingle(e).doubleValue());
	}

}
