package ud.bi0.dragonSphereZ.math.shape;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collection;

import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.ColoredVector;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Gif 
	extends BaseShape
	implements Cloneable {
	
	private ArrayList<BufferedImage> frames = new ArrayList<>();

	public Gif(ArrayList<BufferedImage> frames) {
		setFrames(frames);
	}
	
	public Gif(Gif gif) {
		this(gif.getOrigin(), gif.getBase(), gif.frames());
	}
	
	public Gif(Vector3d origin, Base3d base, ArrayList<BufferedImage> frames) {
		super(origin, base);
		setFrames(frames);
	}
	
	public ArrayList<BufferedImage> getFrames() {
		ArrayList<BufferedImage> copyFrames = new ArrayList<>(frames().size());
		for (BufferedImage frame : frames) {
			copyFrames.add(cloneImage(frame));
		}
		return copyFrames;
	}
	
	public ArrayList<BufferedImage> frames() {
		return frames;
	}
	
	public void frames(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}
	
	public void setFrames(Collection<BufferedImage> frames) {
		this.frames = new ArrayList<BufferedImage>(frames.size());
		for (BufferedImage frame : frames) {
			this.frames.add(cloneImage(frame));
		}
	}
	
	public ArrayList<ColoredVector> getFrame(int index) {
		int height = frames().get(index).getHeight();
		int width = frames().get(index).getWidth();
		ArrayList<ColoredVector> points = new ArrayList<>(height * width);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				points.add(new ColoredVector(getVector(x, y), new Color(frames().get(index).getRGB(x, y))));
			}
		}
		return points;
	}
	
	@Override
	public Gif clone() {
		return new Gif(this);
	}
	
	private Vector3d getVector(int x, int y) {
		return Coordinate.Cartesian3d.getPoint(getBase(), x, y, 0);
	}
	
	private static BufferedImage cloneImage(BufferedImage image) {
	    ColorModel cm = image.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
