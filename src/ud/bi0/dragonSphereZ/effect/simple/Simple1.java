package ud.bi0.dragonSphereZ.effect.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntToDoubleFunction;

import org.bukkit.entity.Player;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.effect.ParticleEffect;
import ud.bi0.dragonSphereZ.math.MathFunction;
import ud.bi0.dragonSphereZ.math.shape.Ellipse;
import ud.bi0.dragonSphereZ.math.shape.Line;
import ud.bi0.dragonSphereZ.util.DynamicLocation;

public class Simple1 extends ParticleEffect {
	
	
	ArrayList<Vector3d> points;
	ArrayList<Vector3d> shifts;
	Vector3d point = new Vector3d();
	Iterator<Vector3d> lineIter = points.iterator();
	Iterator<Vector3d> shiftIter = shifts.iterator();
	Vector3d shift = shiftIter.next();
	
	

	public Simple1(String idName, String particle, DynamicLocation center, List<Player> players) {
		super(idName, particle, center, players);
		pulseTick = 0; 
		
		int n = 10;
		Line line = new Line(Vector3d.ZERO, Vector3d.UNIT_Y.mul(3));
		IntToDoubleFunction percent = (i) -> (  1. * i * i / (n * n));
		points = line.getPointN(n, percent);
		
		Ellipse circle = new Ellipse();
		IntToDoubleFunction angle = MathFunction.Lerp(n, 0, TrigMath.TWO_PI);
		shifts = circle.getPointN(n, angle);
		
		lineIter = points.iterator();
		shiftIter = shifts.iterator();
		shift = shiftIter.next();
	}
	

	@Override
	public void onRun() {
		if (!center.isDynamic() || !center.hasMoved()) {
			center.update();
			if (!lineIter.hasNext()) {
				lineIter = points.iterator();
				shift = shiftIter.next();
			}
			if (!shiftIter.hasNext()) {
				shiftIter = shifts.iterator();
			}
			point = lineIter.next();
			point = point.add(shift).add(displacement).add(center.getVector3d());
			Simple1.this.display(point);
		} else center.update();
	}
}
