package ud.bi0.dragonSphereZ.math;

import java.awt.Color;

import com.flowpowered.math.vector.Vector3d;

public class ColoredVector
	implements Cloneable {
	
	private Vector3d vector;
	private Color color;
	
	public ColoredVector(Vector3d vector, Color color) {
		setPoint(vector);
		setColor(color);
	}
	
	public ColoredVector(ColoredVector coloredVector) {
		this(coloredVector.getPoint(), coloredVector.getColor());
	}
	
	public static ColoredVector createFromRGB(Vector3d vector, Vector3d color) {
		int r = (int) color.getX();
		int g = (int) color.getY();
		int b = (int) color.getZ();
		return new ColoredVector(vector, new Color(r,g,b));
	}
	
	public static ColoredVector createFromHSB(Vector3d vector, Vector3d color) {
		float h = (float) color.getX();
		float s = (float) color.getY();
		float b = (float) color.getZ();
		return new ColoredVector(vector, Color.getHSBColor(h, s, b));
	}
	
	public Vector3d getPoint() {
		return vector.clone();
	}
	
	public Color getColor() {
		return new Color(color.getRGB());
	}
	
	public Vector3d getColorAsVectorRGB() {
		return new Vector3d(color.getRed(), color.getGreen() ,color.getBlue());
	}
	
	public void setPoint(Vector3d point) {
		this.vector = point.clone();
	}
	
	public void setColor(Color color) {
		this.color = new Color(color.getRGB());
	}
	
	public void setColor(Vector3d color) {
		int r = (int) color.getX();
		int g = (int) color.getY();
		int b = (int) color.getZ();
		this.color = new Color(r,g,b);
	}
	
	@Override
	public ColoredVector clone() {
		return new ColoredVector(this);
	}
	
}
