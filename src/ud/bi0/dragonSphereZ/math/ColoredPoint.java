package ud.bi0.dragonSphereZ.math;

import java.awt.Color;

import com.flowpowered.math.vector.Vector3d;

public class ColoredPoint
	implements Cloneable {
	
	private Vector3d point;
	private Color color;
	
	public ColoredPoint(Vector3d point, Color color) {
		setPoint(point);
		setColor(color);
	}
	
	public ColoredPoint(ColoredPoint coloredPoint) {
		this(coloredPoint.getPoint(), coloredPoint.getColor());
	}
	
	public static ColoredPoint createFromRGB(Vector3d point, Vector3d color) {
		int r = (int) color.getX();
		int g = (int) color.getY();
		int b = (int) color.getZ();
		return new ColoredPoint(point, new Color(r,g,b));
	}
	
	public static ColoredPoint createFromHSB(Vector3d point, Vector3d color) {
		float h = (float) color.getX();
		float s = (float) color.getY();
		float b = (float) color.getZ();
		return new ColoredPoint(point, Color.getHSBColor(h, s, b));
	}
	
	public Vector3d getPoint() {
		return point.clone();
	}
	
	public Color getColor() {
		return new Color(color.getRGB());
	}
	
	public Vector3d getColorAsVectorRGB() {
		return new Vector3d(color.getRed(), color.getGreen() ,color.getBlue());
	}
	
	public void setPoint(Vector3d point) {
		this.point = point.clone();
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
	public ColoredPoint clone() {
		return new ColoredPoint(this);
	}
	
}
