package ud.bi0.dragonSphereZ.skriptAPI.expressions;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.maths.shape.Box;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class ExprCubeFace extends SimpleExpression<Location> {
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
	
	/**
	* testcubeface at %location% with radius %number% and density %number%
	*/
	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {	
		Vector3 origin = new Vector3(loc.getSingle(e));
		World world = loc.getSingle(e).getWorld();
		double radius = r.getSingle(e).doubleValue();
		double density = d.getSingle(e).doubleValue();
		List<Vector3> vectors = new Box(origin, radius, radius, radius).renderFaces(density);
		Location[] points = new Location[vectors.size()];
		int i = 0;
		for (Vector3 vector : vectors) {
			points[i] = vector.toLocation(world);
			i++;
		}
		return points;
	}

}
