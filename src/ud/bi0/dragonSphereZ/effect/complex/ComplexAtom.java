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
import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.shape.Ellipse;
import ud.bi0.dragonSphereZ.math.shape.Ellipsoid;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexAtom extends ParticleEffect {
	
	protected boolean enableRotation;
	
	protected double innerRadius;
	protected int innerParticleDensity;
	protected int orbitParticles;
	protected int orbitalCount;
	protected Vector3d axis;
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
		boolean rainbowMode,
		//this
		boolean enableRotation,
		float innerRadius, 
		int innerParticleDensity, 
		int orbitParticles, 
		int orbitalCount, 
		Vector3d axis, 
		String particle2,
		Material dataMat2, 
		byte dataID2, 
		float speed2, 
		Vector3d offset2
			)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
		init(innerRadius, enableRotation, innerParticleDensity, orbitParticles, orbitalCount, axis, particle2, dataMat2, dataID2, speed2, offset2);

	}
	public ComplexAtom(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		init(1, false, 20, 20, 3, new Vector3d(0,0,1), "redstone", Material.DIRT, (byte)0, 0, new Vector3d());
	}
	
	public void init(double innerRadius, boolean enableRotation, int innerParticleDensity, int orbitParticles, int orbitalCount, Vector3d axis, String particle2, Material dataMat2, byte dataID2, float speed2, Vector3d offset2) {
		this.innerRadius = innerRadius;
		this.enableRotation = enableRotation;
		this.innerParticleDensity = innerParticleDensity; 
		this.orbitParticles = orbitParticles; 
		this.orbitalCount = orbitalCount; 
		this.axis = axis.normalize();
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
				
				Vector3d v = new Vector3d();
				Vector3d v2 = new Vector3d();
				Ellipsoid sphere = new Ellipsoid().setBase(Base3d.MINECRAFT).setRadius(innerRadius);
				Ellipse circle = new Ellipse()
										.setBase(Base3d.MINECRAFT) 			//Changes from default cartesian to Minecraft's coordinate system.
										.adjust(Vector3d.UNIT_Y, axis)//Vector3d.UNIT_Y)//axis) 		//Adjusts the circle axis.
										.setRadius(innerRadius + 0.5); 				//Sets the radius of the circle.
				double angle = 0;
				double stepAngle = TrigMath.TWO_PI / orbitParticles;//particleDensity;//default 40
				double angle2 = 0;
				double stepAngle2 = TrigMath.TWO_PI / 20;
				//double angularVelocity = TrigMath.PI / 40d;
				float xRot = 0;			//Holds the current rotation angle for the random rotation.
				float yRot = 0;
				float zRot = 0;
				float stepXRot = 1.5F; 	//Holds the step to the next rotation angle.
				float stepYRot = 0.3F;	
				float stepZRot = 0.9F;
				//float step;
				@Override
				public void run() {
					
					if (!center.hasMoved(pulseTick)) {
						center.update();

						
						v = sphere.getPoint(angle, angle);	//I want to see how this works but might prefer a more randomized sphere :3
						v2 = circle.getPoint(angle2);
						
						if (enableRotation)		//the atom didn't originally have this, I want to see how it looks :3
							v2 = Quaterniond.fromAxesAnglesDeg(xRot, yRot, zRot).rotate(v2); //Rotates the vector.
							xRot = 	GenericMath.wrapAngleDeg(xRot + stepXRot);	//Calculates the next rotation angle.
							yRot = GenericMath.wrapAngleDeg(yRot + stepYRot);
							zRot = GenericMath.wrapAngleDeg(zRot + stepZRot);
						v = v.add(center.getVector3d());
						v = v.add(displacement).add(0,3,0);
						v2 = v2.add(center.getVector3d()); 	//Translates the vector to the center position.
						v2 = v2.add(displacement).add(0,3,0);	//Adds final translation to the vector.
						if (rainbowMode)
							offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
							offset2 = ParticleEffectUtils.simpleRainbowHelper(offset2, particle2);
						ComplexAtom.this.display(v);	
						//ParticleEffectUtils.valueOf(particle).display(dataMat, dataID, players, center, visibleRange, rainbowMode, offset, speed, 1);
						ComplexAtom.this.display(particle2, offset2, speed2, dataMat2, dataID2, v2);
						//ParticleEffectUtils.valueOf(particle2).display(dataMat2, dataID2, players, center, visibleRange, rainbowMode, offset2, speed2, 1);
						angle = GenericMath.wrapAngleRad(angle + stepAngle);
						angle2 = GenericMath.wrapAngleRad(angle2 + stepAngle2);
						
						//TODO Closer to done now :3 
						//for (int i = 0; i < innerParticleDensity; i++) {	//this was used to add an amount of random vectors to the sphere
				
						//v = RandomUtils.getRandomVector().mul(0.5 * innerRadius);
						
						//ComplexAtom.this.display(v);
						//}
						//for (int i = 0; i < orbitParticles; i++) {	
							//double angle = step * angularVelocity;
							//angle = GenericMath.wrapAngleRad(angle);
						
						
						//for (int j = 0; j < orbitalCount; j++) { 	//Need to make the circle axis change based on how many there are so that they aren't overlapping
						//double xRotation = (TrigMath.PI / orbitalCount) * j;
						//v2 = new Vector3d(TrigMath.sin(angle) * ( 0.5 + innerRadius ), 0, TrigMath.cos(angle) * ( 0.5 + innerRadius ));
						
						//VectorUtils.rotateAroundAxisX(v2, xRotation);
						//VectorUtils.rotateAroundAxisY(v2, manualYRotation);

						
							
							
							
						
						
						//}
						
						
							//step++;
						//}
						
						
						//center.update();
						//v = circle.getPoint(angle); //Gets the next point on the circle.
						//if (enableRotation)
						//	v = Quaterniond.fromAxesAnglesDeg(xRot, yRot, zRot).rotate(v); //Rotates the vector.
						//	xRot = 	GenericMath.wrapAngleDeg(xRot + stepXRot);	//Calculates the next rotation angle.
						//	yRot = GenericMath.wrapAngleDeg(yRot + stepYRot);
						//	zRot = GenericMath.wrapAngleDeg(zRot + stepZRot);
						//v = v.add(center.getVector3d()); 	//Translates the vector to the center position.
						//v = v.add(displacement).add(0,1,0);	//Adds final translation to the vector.
						//if (rainbowMode)
						//	offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
						//ComplexAtom.this.display(v);
						//angle = GenericMath.wrapAngleRad(angle + stepAngle);
						
						Bukkit.getServer().broadcastMessage("[v] --> " + v);
						Bukkit.getServer().broadcastMessage("[v2] --> " + v2);
						Bukkit.getServer().broadcastMessage("[angle] --> " + angle);
						Bukkit.getServer().broadcastMessage("[angle2] --> " + angle2);
						Bukkit.getServer().broadcastMessage("[stepAngle] --> " + stepAngle);
						Bukkit.getServer().broadcastMessage("[stepAngle2] --> " + stepAngle2);
						Bukkit.getServer().broadcastMessage("[orbitParticles] --> " + orbitParticles);
						Bukkit.getServer().broadcastMessage("[rainbowMode] --> " + rainbowMode);
						Bukkit.getServer().broadcastMessage("[offset] --> " + offset);
						Bukkit.getServer().broadcastMessage("[offset2] --> " + offset2);
					} else center.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}