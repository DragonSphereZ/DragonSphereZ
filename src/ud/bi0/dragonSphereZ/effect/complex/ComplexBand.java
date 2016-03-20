package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexBand extends ParticleEffect {
	
	//Vector3d v = new Vector3d();
	
	public ComplexBand(
		//super
		String idName,
		String particle,
		DynamicLocation center,
		List<Player> players,
		long delayTick,
		long pulseTick,
		int particleCount,
		Material dataMat,
		byte dataID,
		float speed,
		double visibleRange,
		Vector3d offset,
		Vector3d displacement,
		boolean rainbowMode
		//this

		)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
	}
	public ComplexBand(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
	}

	@Override
	public void onRun() {
		if (!center.isDynamic() || center.hasMoved(pulseTick)) {
			center.update();
			for (int i = 0; i < 15; i++) {
				//v = v.add(0,0.1,0);
				//center.setY(center.getY() + 0.1D);
				center.add(0, 0.1, 0);
				if (rainbowMode == true)
					offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
				//ComplexBand.this.display(v);
				ParticleEffectUtils.valueOf(particle).display(idName, dataMat, dataID, players, center, visibleRange, rainbowMode, offset, speed, particleCount);
			}
		} else center.update();
	}
	
}