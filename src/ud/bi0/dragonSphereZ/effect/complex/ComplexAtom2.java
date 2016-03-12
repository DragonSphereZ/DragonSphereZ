package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.shape.Ellipsoid;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexAtom2 extends ParticleEffect {
	
	protected boolean enableRotation;
	
	protected double innerRadius;
	protected int nucleusDensity;
	protected int orbitSpeed;
	protected int orbitDensity;
	protected int orbitalCount;
	protected Vector3d axis;
	protected String particle2;		//Atom uses a second particle
	protected Material dataMat2;
	protected byte dataID2;
	protected float speed2;
	protected Vector3d offset2;
	
	public ComplexAtom2(
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
		int nucleusDensity, 
		int orbitSpeed, 
		int orbitDensity, 
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
		init(innerRadius, enableRotation, nucleusDensity, orbitSpeed, orbitDensity, orbitalCount, axis, particle2, dataMat2, dataID2, speed2, offset2);

	}
	public ComplexAtom2(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		init(1, false, 20, 2, 20, 3, new Vector3d(0,0,1), "redstone", Material.DIRT, (byte)0, 0, new Vector3d());
	}
	
	public void init(double innerRadius, boolean enableRotation, int nucleusDensity, int orbitSpeed, int orbitDensity, int orbitalCount, Vector3d axis, String particle2, Material dataMat2, byte dataID2, float speed2, Vector3d offset2) {
		this.innerRadius = innerRadius;
		this.enableRotation = enableRotation;
		this.nucleusDensity = nucleusDensity; 
		this.orbitSpeed = orbitSpeed; 
		this.orbitDensity = orbitDensity; 
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
				Ellipsoid sphere = new Ellipsoid()
						.setBase(Base3d.MINECRAFT) 			//Changes from default cartesian to Minecraft's coordinate system.
						.adjust(Vector3d.UNIT_Y, axis)//Vector3d.UNIT_Y)//axis) 		//Adjusts the circle axis.
						.setRadius(innerRadius + 0.5); 				//Sets the radius of the circle.
				double angle = 0;
				double stepAngle = TrigMath.TWO_PI / orbitDensity;
				double orbitAngle = TrigMath.TWO_PI / orbitalCount;
				float xRot = 0;			//Holds the current rotation angle for the random rotation.
				float yRot = 0;
				float zRot = 0;
				float stepXRot = 1.5F; 	//Holds the step to the next rotation angle.
				float stepYRot = 0.3F;	
				float stepZRot = 0.9F;
				@Override
				public void run() {
					
					if (!center.isDynamic() || !center.hasMoved(pulseTick)) {
						center.update();
						//TODO Closer to done now :3 
						for (int i = 0; i < nucleusDensity; i++) {	//this was used to add an amount of random vectors to the sphere
							v = Vector3d.createRandomDirection(new Random()).mul(innerRadius);
							v = v.add(0,3,0);
							if (rainbowMode) offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
							ComplexAtom2.this.display(v);
							Bukkit.getServer().broadcastMessage("[v] --> " + v);
						}
						for (int i = 0; i < orbitSpeed; i++) {	//Loops the amount of particles that will be displayed per orbit.
							
							for (int j = 0; j < orbitalCount; j++) { 	//Loops all orbits.
								
								v2 = sphere.getPoint(angle, j * orbitAngle); //Gets the location.
								
								if (enableRotation)		//the atom didn't originally have this, I want to see how it looks :3
									v2 = Quaterniond.fromAxesAnglesDeg(xRot, yRot, zRot).rotate(v2); //Rotates the vector.
									
								v2 = v2.add(0,3,0);	//Adds final translation to the vector.
								if (rainbowMode) offset2 = ParticleEffectUtils.simpleRainbowHelper(offset2, particle2);
								ComplexAtom2.this.display(particle2, offset2, speed2, dataMat2, dataID2, v2);
							}
							
							xRot = GenericMath.wrapAngleDeg(xRot + stepXRot);	//Calculates the next rotation angle.
							yRot = GenericMath.wrapAngleDeg(yRot + stepYRot);
							zRot = GenericMath.wrapAngleDeg(zRot + stepZRot);
							angle = GenericMath.wrapAngleRad(angle + stepAngle); //Calculates the next angle
							
							Bukkit.getServer().broadcastMessage("[angle2] --> " + angle);
							Bukkit.getServer().broadcastMessage("[stepAngle2] --> " + stepAngle);
						}
					
						
						Bukkit.getServer().broadcastMessage("[v2] --> " + v2);
						Bukkit.getServer().broadcastMessage("[innerRadius] --> " + innerRadius);
						Bukkit.getServer().broadcastMessage("[orbitParticles] --> " + orbitDensity);
						Bukkit.getServer().broadcastMessage("[rainbowMode] --> " + rainbowMode);
						Bukkit.getServer().broadcastMessage("[offset] --> " + offset);
						Bukkit.getServer().broadcastMessage("[offset2] --> " + offset2);
						Bukkit.getServer().broadcastMessage("[particle2] --> " + particle2);
						Bukkit.getServer().broadcastMessage("[speed2] --> " + speed2);
						
					} else center.update();
				}
			}, delayTick, pulseTick).getTaskId();
			effectManager.startEffect(this);
		}
	}
}