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
import ud.bi0.dragonSphereZ.oldmath.shape.Ellipsoid;
import ud.bi0.dragonSphereZ.oldmath.vector.Vector3;

public class ExprTestEllipsoid extends SimpleExpression<Location> {
	private Expression<Location> origin;
	private Expression<Number> radiusU;
	private Expression<Number> radiusV;
	private Expression<Number> radiusW;
	private Expression<Number> startRadiusU;
	private Expression<Number> endRadiusU;
	private Expression<Number> startRadiusV;
	private Expression<Number> endRadiusV;
	private Expression<Number> startRadiusW;
	private Expression<Number> endRadiusW;
	private Expression<Number> startAngleThetha;
	private Expression<Number> endAngleThetha;
	private Expression<Number> startAnglePhi;
	private Expression<Number> endAnglePhi;
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
		radiusW = (Expression<Number>) expr[3];
		startRadiusU = (Expression<Number>) expr[4];
		endRadiusU = (Expression<Number>) expr[5];
		startRadiusV = (Expression<Number>) expr[6];
		endRadiusV = (Expression<Number>) expr[7];
		startRadiusW = (Expression<Number>) expr[8];
		endRadiusW = (Expression<Number>) expr[9];
		startAngleThetha = (Expression<Number>) expr[10];
		endAngleThetha = (Expression<Number>) expr[11];
		startAnglePhi = (Expression<Number>) expr[12];
		endAnglePhi = (Expression<Number>) expr[13];
		density = (Expression<Number>) expr[14];
		return true;
	}
	
	/**
	* testell at %location% with radius %number% and density %number%
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
		List<Vector3> vectors = new Ellipsoid(
				loc,
				radiusU.getSingle(e).doubleValue(),
				radiusV.getSingle(e).doubleValue(),
				radiusW.getSingle(e).doubleValue())
			.render(
				startRadiusU.getSingle(e).doubleValue(),
				endRadiusU.getSingle(e).doubleValue(),
				startRadiusV.getSingle(e).doubleValue(),
				endRadiusV.getSingle(e).doubleValue(),
				startRadiusW.getSingle(e).doubleValue(),
				endRadiusW.getSingle(e).doubleValue(),
				startAngleThetha.getSingle(e).doubleValue(),
				endAngleThetha.getSingle(e).doubleValue(),
				startAnglePhi.getSingle(e).doubleValue(),
				endAnglePhi.getSingle(e).doubleValue(),
				density.getSingle(e).doubleValue());
		Location[] points = new Location[vectors.size()];
		points = new Vector3().locationArray(world, vectors);
		return points;
	}

}

