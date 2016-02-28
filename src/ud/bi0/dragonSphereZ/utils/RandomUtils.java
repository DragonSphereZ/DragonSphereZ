package ud.bi0.dragonSphereZ.utils;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.util.Vector;

public final class RandomUtils {

	public static final Random random = new Random(System.nanoTime());
	
	private RandomUtils() {
		// No instance allowed
	}
	
	public static Vector getRandomVector() {
		double x, y, z;
		x = random.nextDouble() * 2 - 1;
		y = random.nextDouble() * 2 - 1;
		z = random.nextDouble() * 2 - 1;

		return new Vector(x, y, z).normalize();
	}
	
	public static Vector getRandomCircleVector() {
		double rnd, x, z;
		rnd = random.nextDouble() * 2 * Math.PI;
		x = Math.cos(rnd);
		z = Math.sin(rnd);
		
		return new Vector(x, 0, z);
	}

	public static Material getRandomMaterial(Material[] materials) {
		return materials[random.nextInt(materials.length)];
	}
	
	public static double getRandomAngle() {
		return random.nextDouble() * 2 * Math.PI;
	}
	
    public static double randomDouble(final double min, final double max) {
        return (Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min);
    }
    
    public static float randomRangeFloat(final float min, final float max) {
        return (float)((Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }
    
    public static int randomRangeInt(final int min, final int max) {
        return (int)((Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }
}
