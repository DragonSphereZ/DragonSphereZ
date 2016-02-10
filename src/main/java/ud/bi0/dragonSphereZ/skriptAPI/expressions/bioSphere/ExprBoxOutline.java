package ud.bi0.dragonSphereZ.skriptAPI.expressions.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.maths.shape.Box;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class ExprBoxOutline extends SimpleExpression<Location>{
	private Expression<Location> loc1;
	private Expression<Location> loc2;
	private Expression<Number> density;
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
		loc1 = (Expression<Location>) expr[0];
		loc2 = (Expression<Location>) expr[1];
		density = (Expression<Number>) expr[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "box outline[ from] %location% to %location%[ with] density %number%";
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		Vector3 vec1 = new Vector3(loc1.getSingle(e));
		Vector3 vec2 = new Vector3(loc2.getSingle(e));
		Box box = new Box(vec1, vec2.getX()-vec1.getX(), vec2.getY()-vec1.getY(), vec2.getZ()-vec1.getZ());
		Location[] locs = new Vector3().locationArray(loc1.getSingle(e).getWorld(), box.renderOutline(0,1,0,1,0,1,density.getSingle(e).doubleValue()));
		return locs;
	}

}
