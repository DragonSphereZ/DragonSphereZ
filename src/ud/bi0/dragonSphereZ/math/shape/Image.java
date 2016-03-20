package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.Collection;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.ColoredPoint;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Image 
	extends BaseShape
	implements Cloneable {

	private ArrayList<ColoredPoint> points = new ArrayList<>();
	
	public Image() {
	}
	
	public Image(Image image) {
		this(image.getOrigin(), image.getBase(), image.points);
	}
	
	public Image(Vector3d origin, Base3d base, Collection<ColoredPoint> points) {
		super(origin, base);
		addAll(points);
	}
	
	public ColoredPoint get(int index) {
		ColoredPoint point = points.get(index);
		Vector3d vector = Coordinate.Cartesian3d.getPoint(getBase(), point.getPoint()).add(getOrigin());
		return new ColoredPoint(vector, point.getColor());
	}
	
	public ArrayList<ColoredPoint> getAll() {
		int size = this.points.size();
		ArrayList<ColoredPoint> points = new ArrayList<ColoredPoint>(size);
		for (int i = 0; i < size; i++) {
			points.add(get(i));
		}
		return points;
	}
	
	public void add(ColoredPoint point) {
		this.points.add(point);
	}
	
	public void addAll(Collection<ColoredPoint> points) {
		this.points.addAll(points);
	}
	
	@Override
	public Image clone() {
		return new Image(this);
	}

}
