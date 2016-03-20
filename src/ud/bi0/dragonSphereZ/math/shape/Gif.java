package ud.bi0.dragonSphereZ.math.shape;

import java.util.ArrayList;
import java.util.Collection;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.ColoredPoint;

public class Gif 
	extends BaseShape
	implements Cloneable {
	
	private ArrayList<Image> frames = new ArrayList<Image>();

	public Gif() {
	}
	
	public Gif(Gif gif) {
		this(gif.getOrigin(), gif.getBase(), gif.frames);
	}
	
	public Gif(Collection<Image> frames) {
		addAllFrames(frames);
	}
	
	public Gif(Vector3d origin, Base3d base, Collection<Image> frames) {
		super(origin, base);
		addAllFrames(frames);
	}
	
	public void addFrame(Image image) {
		frames.add(image.clone());
	}
	
	public void addAllFrames(Collection<Image> images) {
		for (Image image : images) {
			addFrame(image);
		}
	}
	
	public void removeFrame(int index) {
		frames.remove(index);
	}
	
	public void removeFrame(Image image) {
		frames.remove(image);
	}
	
	public Image getFrame(int index) {
		return frames.get(index).clone();
	}
	
	public ArrayList<ColoredPoint> get(int index) {
		return frames.get(index).getAll();
	}
	
	@Override 
	public Gif clone() {
		return new Gif(this);
	}
	
	
}
