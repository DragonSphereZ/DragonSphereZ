package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprChunkSquare extends SimpleExpression<Chunk>{
	private Expression<Location> center;
	private Expression<Number> radius;
	@Override
	public Class<? extends Chunk> getReturnType() {
		return Chunk.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		center = (Expression<Location>) expr[0];
		radius = (Expression<Number>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "chunks in square around %locatin% with radius %number%";
	}

	@Override
	@Nullable
	protected Chunk[] get(Event e) {
		return new BiosphereTrigLib().getChunksSquare(center.getSingle(e),(int) radius.getSingle(e).doubleValue());
	}

}
