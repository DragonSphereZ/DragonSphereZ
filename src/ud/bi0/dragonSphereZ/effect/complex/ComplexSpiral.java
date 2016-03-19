package ud.bi0.dragonSphereZ.effect.complex;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.shape.Cylinder;
import ud.bi0.dragonSphereZ.util.DynamicLocation;
import ud.bi0.dragonSphereZ.util.ParticleEffectUtils;

public class ComplexSpiral extends ParticleEffect {
	
	protected double radius;
	protected double circleDensity;
	protected float height; 
	protected float effectMod;	
	protected boolean clockwise;
	protected boolean scan;
	protected Vector3d axis;
	
	
	Cylinder spiral = new Cylinder();
	Vector3d v = new Vector3d();
	double angle = 0;
	double stepAngle = clockwise ? TrigMath.TWO_PI / circleDensity : -TrigMath.TWO_PI / circleDensity; //Sets the angle difference and rotation direction.
	double heightCounter = 0;
	double stepHeight = Math.signum(height) * effectMod / circleDensity; //Sets the height difference and its direction.

	
	public ComplexSpiral(
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
		boolean rainbowMode,
		//this
		boolean scan,
		Vector3d offset,
		Vector3d displacement,		
		double radius,
		double circleDensity,
		float height,
		float effectMod,
		boolean clockwise,
		Vector3d axis)
	{
		super(idName, particle, center, players, delayTick, pulseTick, particleCount, dataMat, dataID, speed, visibleRange, rainbowMode, offset, displacement);
		init(radius, circleDensity, height, effectMod, scan, clockwise, axis);

	}
	public ComplexSpiral(String idName, DynamicLocation center, List<Player> players) {
		super(idName, center, players);
		init(1, 1, (float) 2, (float) 0.1, false, false, new Vector3d(0,0,1));
	}
	
	public void init(double radius, double circleDensity, float height, float effectMod, boolean scan, boolean clockwise, Vector3d axis) {
	
		this.radius = radius;
		this.circleDensity = circleDensity;
		this.height = height;
		this.effectMod = effectMod > 0 ? effectMod : 1; //Makes effectMod default to 1 if a value <= 0 is entered.
		this.scan = scan;
		this.clockwise = clockwise;
		this.axis = axis;
		
		spiral.adjustBase(Vector3d.UNIT_Y, axis);
		spiral.setRadius(radius);
	}
	
	@Override
	public void onRun() {
		if (!center.hasMoved(pulseTick)) {
			center.update();
			v = spiral.getPoint(angle, heightCounter);
			v = v.add(center.getVector3d()).add(displacement);
			if (rainbowMode)
				offset = ParticleEffectUtils.simpleRainbowHelper(offset, particle);
			ComplexSpiral.this.display(v);
			angle = GenericMath.wrapAngleRad(angle + stepAngle);
			heightCounter += stepHeight;
			if (Math.abs(heightCounter) > Math.abs(height)) { //Triggers when it reaches the top of the spiral.
				if (scan) stepHeight = -stepHeight;
				else heightCounter = 0;
			}
			if (scan) {
				if ((height > 0 && heightCounter < 0) || (height < 0 && heightCounter > 0)) stepHeight = -stepHeight; //Triggers when it reaches the start of the spiral.
			}
		} else center.update();
	}
}