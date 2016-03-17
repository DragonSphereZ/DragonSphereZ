package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.DragonSphereCore;
import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexDot extends ParticleEffect {

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

		//this
		Vector3d offset,
		Vector3d displacement,
		boolean rainbowMode)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
	}
	public ComplexDot(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				@Override
				public void run() {
					center.update();
					if (rainbowMode == true)
						offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
					ParticleEffectUtils.valueOf(particle).display(idName, dataMat, dataID, players, center, visibleRange, rainbowMode, offset, speed, particleCount);
				}
			}, 0, pulseTick).getTaskId();
			idTask2 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					DragonSphereCore.effectManager.stopEffect(idName);
					Bukkit.getScheduler().cancelTask(idTask);
					Bukkit.getScheduler().cancelTask(idTask2);
				    }
			}, delayTick);
			effectManager.startEffect(this);

		}
	}
}