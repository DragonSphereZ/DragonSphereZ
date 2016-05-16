package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexBand extends ParticleEffect {
		
	public ComplexBand(
		//super
		int particleCount,
		String particle,
		Material dataMat,
		byte dataID,
		float speed,
		Vector3d offset,
		String idName,
		DynamicLocation center,
		List<Player> players,
		boolean rainbowMode,
		double visibleRange,
		Vector3d displacement,
		long delayTick,
		long pulseTick
		)
	{
		super(particleCount, particle, dataMat, dataID, speed, offset, idName, center, players, rainbowMode, visibleRange, displacement, delayTick, pulseTick);
	}
	public ComplexBand(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
	}

	@Override
	public void onRun() {
		if (!center.isDynamic() || !center.hasMoved()) {
			center.update();
			if (rainbowMode == true)
				offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
			for (int i = 0; i < 15; i++) {
				center.add(0, 0.1, 0);	//TODO when using 'location of player' this effect shoots straight up into the air. Need to fix this try changing to vector
				ParticleEffectUtils.valueOf(particle).display(idName, dataMat, dataID, players, center, visibleRange, rainbowMode, offset, speed, particleCount);
			}
		} else center.update();
	}
	
}