package ud.bi0.dragonSphereZ.skriptAPI.effect;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.effect.SimpleEffectHelper;
import ud.bi0.dragonSphereZ.skriptAPI.SkriptHandler;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class EffSimpleEffect extends Effect {
	
	Expression<String> effectName;
	Expression<String> effectParticle;
	Expression<String> effectID;
	Expression<Object> effectTarget;
	Expression<Player> effectPlayers;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		effectName = (Expression<String>) expr[0];
		effectParticle = (Expression<String>) expr[1];
		effectID = (Expression<String>) expr[2];
		effectTarget = (Expression<Object>) expr[3];
		effectPlayers = (Expression<Player>) expr[4];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "create simple effect %string% with particle %string%[,| and] id %string% at %entity/location%[ for %players%]";
	}

	@Override
	protected void execute(Event e) {
		String name = effectName.getSingle(e);
		String particle;
		String id; 
		DynamicLocation target;
		List<Player> players;
		ParticleEffect effect;
		try {
			particle = SkriptHandler.inputParticleString(e, effectParticle);
			id = SkriptHandler.inputID(e, effectID);
			target = SkriptHandler.inputCenter(e, effectTarget);
			players = SkriptHandler.inputPlayers(e, effectPlayers);
			effect = new SimpleEffectHelper().createEffect(name, id, particle, target, players);
		} catch (IllegalArgumentException ex) {
			return;
		}
		effect.start();
	}

}
