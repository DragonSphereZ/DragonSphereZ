package ud.bi0.dragonSphereZ.skriptAPI.effects;

import java.util.Arrays;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.particles.effects.LineTest;

public class EffLineTest extends Effect {
	
	private Expression<String> id;
	private Expression<Location> loc;
	private Expression<Player> players;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		
		id = (Expression<String>) expr[0];
		loc = (Expression<Location>) expr[1];
		players = (Expression<Player>) expr[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void execute(Event e) {
		
		new LineTest(id.getSingle(e), loc.getSingle(e), Arrays.asList(players.getAll(e))).start();
		
	}

}
