package ud.bi0.dragonSphereZ.skriptAPI.effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.DragonSphereCore;

public class EffStopAllEffects extends Effect {
	
	
	Expression<Entity> ent;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		ent = (Expression<Entity>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "stop all[ particle] effects[ targeting %entities%]";
	}

	
	@Override
	protected void execute(Event e) {
		if (ent != null) {
			Entity[] entityArray = ent.getArray(e);
			if (entityArray != null) 
				for (Entity entity : entityArray) {
					DragonSphereCore.effectManager.stopEffect(entity);
				}
		}
		else DragonSphereCore.effectManager.stopAll();
	}
}
