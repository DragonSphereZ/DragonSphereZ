package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexDot extends ParticleEffect {
	
	//protected int stopDelay;
	//int stopDelayCounter = 0;
	
	public ComplexDot(
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
		//int stopDelay
		)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
	//	init(stopDelay);
	}
	public ComplexDot(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
	//	init(5);
	}
	//public void init(int stopDelay) {
	//	this.stopDelay = stopDelay;	
	//}

	@Override
	public void onRun() {
		center.update();
		if (rainbowMode == true)
			offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
		ParticleEffectUtils.valueOf(particle).display(idName, dataMat, dataID, players, center, visibleRange, rainbowMode, offset, speed, particleCount);
		
		//Limited to the pulseTick
		//if (stopDelayCounter >= stopDelay){
		//	effectManager.stopEffect(idName);
		//}
		//stopDelayCounter += 1;
			
	}
}