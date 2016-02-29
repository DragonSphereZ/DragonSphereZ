package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.oldmath.shape.Cylinder;
import ud.bi0.dragonSphereZ.oldmath.vector.Vector3;

public class ExprCylinderLoc extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Number> r;
	private Expression<Number> phi;
	private Expression<Number> h;
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
		r = (Expression<Number>) expr[2];
		phi = (Expression<Number>) expr[1];
		h = (Expression<Number>) expr[3];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "cylinder coord[inate][s][ at] %locations%[ with] coordinates %number%(,| and) %number%(,| and) %number%";
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		Vector3 vec = new Vector3(loc.getSingle(e));
		double radius = r.getSingle(e).doubleValue();
		double angle = phi.getSingle(e).doubleValue();
		double height = h.getSingle(e).doubleValue();
		Cylinder cyl = new Cylinder(vec, 1, 1);
		Location[] location = new Vector3().locationArray(loc.getSingle(e).getWorld(), cyl.getPoint(radius, angle, height));
		return location;
	}

}
