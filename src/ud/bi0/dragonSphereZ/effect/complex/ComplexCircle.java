package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.shape.Ellipse;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexCircle extends ParticleEffect {
	
	protected double radius;
	protected double particleDensity;
	protected boolean enableRotation;
	protected Vector3d axis;
	
	Vector3d v = new Vector3d();
	Ellipse circle;				//Sets the radius of the circle.
	double angle = 0;
	double stepAngle = TrigMath.TWO_PI / particleDensity;
	float xRot = 0;			//Holds the current rotation angle for the random rotation.
	float yRot = 0;
	float zRot = 0;
	final float stepXRot = 1.5F; 	//Holds the step to the next rotation angle.
	final float stepYRot = 0.3F;	
	final float stepZRot = 0.9F;
	
	public ComplexCircle(
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
		double radius,
		double particleDensity,
		boolean rainbowMode,
		boolean enableRotation,
		Vector3d axis)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
		init(radius, particleDensity, enableRotation, axis);

	}
	public ComplexCircle(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		//this.offset = new Vector3d(0,1,1);
		//this.displacement = new Vector3d();
		init(1, 1, false, new Vector3d(0,0,1));
	}
	
	public void init(double radius, double particleDensity, boolean enableRotation, Vector3d axis) {
		this.radius = radius;
		this.particleDensity = particleDensity;
		this.enableRotation = enableRotation;
		this.axis = axis.normalize();
		
		circle.adjustBase(Vector3d.UNIT_Y, axis);
		circle.setRadius(radius);
	}
	
	@Override
	public void onRun() {
		if (!center.hasMoved(pulseTick)) {
			center.update();
			v = circle.getPoint(angle); //Gets the next point on the circle.
			if (enableRotation)
				v = Quaterniond.fromAxesAnglesDeg(xRot, yRot, zRot).rotate(v); //Rotates the vector.
				xRot = 	GenericMath.wrapAngleDeg(xRot + stepXRot);	//Calculates the next rotation angle.
				yRot = GenericMath.wrapAngleDeg(yRot + stepYRot);
				zRot = GenericMath.wrapAngleDeg(zRot + stepZRot);
			v = v.add(displacement).add(0,1,0);	//Adds final translation to the vector.
			if (rainbowMode)
				offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
			display(v);
			angle = GenericMath.wrapAngleRad(angle + stepAngle);						
		} else center.update();
	}
}