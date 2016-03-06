package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;
import ud.bi0.dragonSphereZ.util.RandomUtils;
import ud.bi0.dragonSphereZ.util.VectorUtils;

public class ComplexAtom extends ParticleEffect {
	
	protected double particleDensity;
	protected boolean enableRotation;
	
	protected double innerRadius;
	protected int innerParticleDensity;
	protected int orbitParticles;
	protected int orbitalCount;
	protected double manualYRotation;
	String particle2;		//Atom uses a second particle
	Material dataMat2;
	byte dataID2;
	float speed2;
	Vector3d offset2;
	
	public ComplexAtom(
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
		
		//this
		double particleDensity,
		boolean rainbowMode,
		boolean enableRotation,
		
		float innerRadius, 
		int innerParticleDensity, 
		int orbitParticles, 
		int orbitalCount, 
		double manualYRotation, 
		String particle2,
		Material dataMat2, 
		byte dataID2, 
		float speed2, 
		Vector3d offset2
			)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
		init(innerRadius, particleDensity, enableRotation, innerParticleDensity, orbitParticles, orbitalCount, manualYRotation, particle2, dataMat2, dataID2, speed2, offset2);

	}
	public ComplexAtom(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		init(1, 1, false, 20, 20, 3, 1, "redstone", Material.DIRT, (byte)0, 0, new Vector3d());
	}
	
	public void init(double innerRadius, double particleDensity, boolean enableRotation, int innerParticleDensity, int orbitParticles, int orbitalCount, double manualYRotation, String particle2, Material dataMat2, byte dataID2, float speed2, Vector3d offset2) {
		this.innerRadius = innerRadius;
		this.particleDensity = particleDensity;
		this.enableRotation = enableRotation;
		this.innerParticleDensity = innerParticleDensity; 
		this.orbitParticles = orbitParticles; 
		this.orbitalCount = orbitalCount; 
		this.manualYRotation = manualYRotation; 
		this.particle2 = particle2;
		this.dataMat2 = dataMat2;
		this.dataID2 = dataID2;
		this.speed2 = speed2;
		this.offset2 = offset2;
		
		
	}
	
	@Override
	public void start() {
		if (!effectManager.isActive(idName))  {
			idTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
				
				//Vector3d v = new Vector3d();
				//Ellipse circle = new Ellipse()
				//						.setBase(Base3d.MINECRAFT) 			//Changes from default cartesian to Minecraft's coordinate system.
				//						.adjust(Vector3d.UNIT_Y, axis) 		//Adjusts the circle axis.
				//						.setRadius(radius); 				//Sets the radius of the circle.
				double angle = 0;
				double stepAngle = TrigMath.TWO_PI / particleDensity;//default 40
				float xRot = 0;			//Holds the current rotation angle for the random rotation.
				float yRot = 0;
				float zRot = 0;
				float stepXRot = 1.5F; 	//Holds the step to the next rotation angle.
				float stepYRot = 0.3F;	
				float stepZRot = 0.9F;
				@Override
				public void run() {
					
					if (!center.hasMoved(pulseTick)) {
						center.update();

						if (rainbowMode)
							offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);

						//TODO Closer to done now :3 
						for (int i = 0; i < innerParticleDensity; i++) {
							Vector3d v = RandomUtils.getRandomVector().mul(0.5 * innerRadius);
							v = v.add(v);
							v = v.add(displacement).add(0,3,0);
							ParticleEffectUtils.valueOf(particle2).display(dataMat2, dataID2, players, center, visibleRange, rainbowMode, offset2, speed2, 1);
							//ComplexAtom.this.display(v);
						}
						for (int i = 0; i < orbitParticles; i++) {	
							angle = GenericMath.wrapAngleRad(angle + stepAngle);
							for (int j = 0; j < orbitalCount; j++) {
								double xRotation = (TrigMath.PI / orbitalCount) * j;
								Vector3d v = new Vector3d(TrigMath.sin(angle) * ( 0.5 + innerRadius ), 0, TrigMath.cos(angle) * ( 0.5 + innerRadius ));
								//Vector3d v = circle.getPoint(angle);
								VectorUtils.rotateAroundAxisX(v, xRotation);
								VectorUtils.rotateAroundAxisY(v, manualYRotation);

								if (enableRotation)		//the atom didn't originally have this, I want to see how it looks :3
									v = Quaterniond.fromAxesAnglesDeg(xRot, yRot, zRot).rotate(v); //Rotates the vector.
									xRot = 	GenericMath.wrapAngleDeg(xRot + stepXRot);	//Calculates the next rotation angle.
									yRot = GenericMath.wrapAngleDeg(yRot + stepYRot);
									zRot = GenericMath.wrapAngleDeg(zRot + stepZRot);
									
								v = v.add(center.getVector3d()); 	//Translates the vector to the center position.
								v = v.add(displacement).add(0,3,0);	//Adds final translation to the vector.
								ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, center, visibleRange, rainbowMode, offset, speed, 1);
								//ComplexAtom.this.display(v);
							}
							
						}
						
					} else center.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}