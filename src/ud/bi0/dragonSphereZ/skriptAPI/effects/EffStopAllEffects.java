package ud.bi0.dragonSphereZ.skriptAPI.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.DragonSphereCore;

public class EffStopAllEffects extends Effect {

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "stop all effects";
	}

	@Override
	protected void execute(Event arg0) {
		DragonSphereCore.effectManager.stopAll();
	}

}
