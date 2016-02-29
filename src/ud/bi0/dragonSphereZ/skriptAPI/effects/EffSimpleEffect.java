package ud.bi0.dragonSphereZ.skriptAPI.effects;

import java.util.Arrays;
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
import ud.bi0.dragonSphereZ.utils.DynamicLocation;

public class EffSimpleEffect extends Effect {
	
	Expression<String> effectName;
	Expression<String> effectParticle;
	Expression<String> effectID;
	Expression<?> effectTarget;
	Expression<Player> effectPlayers;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		effectName = (Expression<String>) expr[0];
		effectParticle = (Expression<String>) expr[1];
		effectID = (Expression<String>) expr[2];
		effectTarget = expr[3];
		effectPlayers = (Expression<Player>) expr[4];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "create simple effect %string% with particle %string%[,| and] id %string% at %entity/location%[ for %players%]";
	}

	@Override
	protected void execute(Event e) {
		if (SkriptHandler.hasNull(e, effectName, effectID, effectTarget)) return;
		String name = effectName.getSingle(e);
		String id = effectID.getSingle(e);
		String particle = effectParticle.getSingle(e);
		DynamicLocation target = DynamicLocation.init(effectTarget.getSingle(e));
		List<Player> players = Arrays.asList(effectPlayers.getAll(e));
		ParticleEffect effect = new SimpleEffectHelper().getEffect(name, id, particle, target, players);
		if (effect == null) return;
		effect.start();
	}

}
