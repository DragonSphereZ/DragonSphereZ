package ud.bi0.dragonSphereZ.math.shape;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;


import com.flowpowered.math.vector.Vector3d;

import ud.bi0.dragonSphereZ.math.Base3d;
import ud.bi0.dragonSphereZ.math.ColoredVector;
import ud.bi0.dragonSphereZ.math.Coordinate;

public class Image 
	extends BaseShape
	implements Cloneable {

	public static final double PIXEL_DISTANCE = 0.1;
	
	private BufferedImage image;
	
	public Image(BufferedImage image) {
		setImage(image);
	}
	
	public Image(Image image) {
		this(image.getOrigin(), image.getBase(), image.image());
	}
	
	public Image(Vector3d origin, Base3d base, BufferedImage image) {
		super(origin, base);
		setImage(image);
	}
	
	public BufferedImage getImage() {
		return cloneImage(image());
	}
	
	public BufferedImage image() {
		return image;
	}
	
	public void image(BufferedImage image) {
		this.image = image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = cloneImage(image);
	}
	
	public ColoredVector get(int x, int y) {
		return new ColoredVector(getVector(x, y), new Color(image().getRGB(x, y)));
	}
	
	public ArrayList<ColoredVector> getAll() {
		int width = image().getWidth();
		int height = image().getHeight();
		ArrayList<ColoredVector> points = new ArrayList<ColoredVector>(width * height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				points.add(get(i, j));
			}
		}
		return points;
	}
		
	@Override
	public Image clone() {
		return new Image(this);
	}
	
	private Vector3d getVector(int x, int y) {
		return Coordinate.Cartesian3d.getPoint(getBase(), x * PIXEL_DISTANCE, y * PIXEL_DISTANCE, 0).add(getOrigin());
	}
	
	private static BufferedImage cloneImage(BufferedImage image) {
	    ColorModel cm = image.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}
