package ud.bi0.dragonSphereZ.skriptAPI.expression.bioSphere;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprChunkCenter extends SimpleExpression<Location>{
	private Expression<Chunk> chunk;
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		chunk = (Expression<Chunk>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "center of %chunk%";
	}

	@Override
	@Nullable
	protected Location[] get(Event e) {
		return new Location[]{new BiosphereTrigLib().getChunkCenter(chunk.getSingle(e))};
	}

}
