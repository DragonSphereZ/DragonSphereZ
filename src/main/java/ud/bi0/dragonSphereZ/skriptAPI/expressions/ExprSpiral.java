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
import ud.bi0.dragonSphereZ.maths.shape.Cylinder;
import ud.bi0.dragonSphereZ.maths.vector.Vector3;

public class ExprSpiral extends SimpleExpression<Location> {
	private Expression<Location> origin;
	private Expression<Number> radiusU;
	private Expression<Number> radiusV;
	private Expression<Number> startRadiusU;
	private Expression<Number> endRadiusU;
	private Expression<Number> startRadiusV;
	private Expression<Number> endRadiusV;
	private Expression<Number> startAngle;
	private Expression<Number> endAngle;
	private Expression<Number> startHeight;
	private Expression<Number> endHeight;
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
		origin = (Expression<Location>) expr[0];
		radiusU = (Expression<Number>) expr[1];
		radiusV = (Expression<Number>) expr[2];
		startRadiusU = (Expression<Number>) expr[3];
		endRadiusU = (Expression<Number>) expr[4];
		startRadiusV = (Expression<Number>) expr[5];
		endRadiusV = (Expression<Number>) expr[6];
		startAngle = (Expression<Number>) expr[7];
		endAngle = (Expression<Number>) expr[8];
		startHeight = (Expression<Number>) expr[9];
		endHeight = (Expression<Number>) expr[10];
		density = (Expression<Number>) expr[11];
		
		return true;
	}
	
	/**
	* testspiral at %location% with radius %number% and density %number%
	*/
	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {	
		Vector3 loc = new Vector3(origin.getSingle(e));
		World world = origin.getSingle(e).getWorld();
		List<Vector3> vectors = new Cylinder(
				loc,
				radiusU.getSingle(e).doubleValue(),
				radiusV.getSingle(e).doubleValue())
			.renderSpiral(
				startRadiusU.getSingle(e).doubleValue(),
				endRadiusU.getSingle(e).doubleValue(),
				startRadiusV.getSingle(e).doubleValue(),
				endRadiusV.getSingle(e).doubleValue(),
				startAngle.getSingle(e).doubleValue(),
				endAngle.getSingle(e).doubleValue(),
				startHeight.getSingle(e).doubleValue(),
				endHeight.getSingle(e).doubleValue(),
				density.getSingle(e).doubleValue());
		Location[] points = new Location[vectors.size()];
		int i = 0;
		for (Vector3 vector : vectors) {
			points[i] = vector.toLocation(world);
			i++;
		}
		return points;
	}

}

