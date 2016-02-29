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

public class ExprHelix extends SimpleExpression<Location>{
	private Expression<Location> loc;
	private Expression<Number> r;
	private Expression<Number> h;
	private Expression<Number> rot;
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
		rot = (Expression<Number>) expr[3];
		d = (Expression<Number>) expr[4];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "heli(x|xes|ces) at %locations% with radius %number%(,| and) height %number%(,| and) rotations %number%(,| and) density %number%";
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		Vector3 vec = new Vector3(loc.getSingle(e));
		double radius = r.getSingle(e).doubleValue();
		double height = h.getSingle(e).doubleValue();
		double rotations = rot.getSingle(e).doubleValue();
		double density = d.getSingle(e).doubleValue();
		Cylinder spiral = new Cylinder(vec,1,1);
		Location[] locs = new Vector3().locationArray(loc.getSingle(e).getWorld(), spiral.renderSpiral(radius, radius, radius, radius, 0, rotations*2*Math.PI, 0, height, density));
		return locs;
	}

}
