package ud.bi0.dragonSphereZ.util;

import java.util.Random;

import org.bukkit.Material;

import com.flowpowered.math.vector.Vector3d;

public final class RandomUtils {

	public static final Random random = new Random(System.nanoTime());
	
	private RandomUtils() {
		// No instance allowed
	}
	
	public static Vector3d getRandomVector() {
		return new Vector3d(Vector3d.createRandomDirection(random));
	}
	
	public static Vector3d getRandomCircleVector() {
		double rnd, x, z;
		rnd = random.nextDouble() * 2 * Math.PI;
		x = Math.cos(rnd);
		z = Math.sin(rnd);
		
		return new Vector3d(x, 0, z);
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
