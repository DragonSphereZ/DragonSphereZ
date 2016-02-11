package ud.bi0.dragonSphereZ.maths.vector;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.BlockVector;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

@SerializableAs("Vector2")
public class Vector2 
	implements Cloneable, Serializable
{

	/**
	 * Class for 2D vectors in the Cartesian coordinate system
	 * 
	 * 
	 */
	private static final long serialVersionUID = 966688914993857611L;
	
	private static final double epsilon = 0.000001;
	
	private static Random random = new Random();
	
	private double x;
	private double y;

	
	public Vector2() {
		x = 0;
		y = 0;
	}
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector vec) {
		this.x = vec.getZ();
		this.y = vec.getX();
	}
	
	public Vector2(Location loc) {
		this.x = loc.getZ();
		this.y = loc.getX();
	}
	
	public Vector2 add(Vector2 vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}
	
	public Vector2 add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2 add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2 subtract(Vector2 vec) {
		x -= vec.x;
		y -= vec.y;
		return this;
	}
	
	public Vector2 mulitply(Vector2 vec) {
		x *= vec.x;
		y *= vec.y;
		return this;
	}
	
	public Vector2 divide(Vector2 vec) {
		x /= vec.x;
		y /= vec.y;
		return this;
	}
	
	public Vector2 copy(Vector2 vec) {
		x = vec.x;
		y = vec.y;
		return this;
	}
	
	public double length() {
		return Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y));
	}
	
	public double lengthSquared() {
        return NumberConversions.square(x) + NumberConversions.square(y);
    }
	
	public double distance(Vector2 o) {
        return Math.sqrt(NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y));
    }
	
	public double distanceSquared(Vector2 o) {
        return NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y);
    }
	
	public float angle(Vector2 other) {
        double dot = dot(other) / (length() * other.length());

        return (float) Math.acos(dot);
    }
	
	public Vector2 midpoint(Vector2 other) {
        x = (x + other.x) / 2;
        y = (y + other.y) / 2;
        return this;
    }
	
	public Vector2 getMidpoint(Vector2 other) {
        double x = (this.x + other.x) / 2;
        double y = (this.y + other.y) / 2;
        return new Vector2(x, y);
    }
	
	public Vector2 multiply(int m) {
        x *= m;
        y *= m;
        return this;
    }
	
	public Vector2 multiply(double m) {
        x *= m;
        y *= m;
        return this;
    }
	
	public double dot(Vector2 other) {
        return x * other.x + y * other.y ;
    }
	
	public Vector2 normalize() {
        double lengthSquared = lengthSquared();

        x *= Math.abs(x)/lengthSquared;
        y *= Math.abs(y)/lengthSquared;

        return this;
    }
	
	public Vector2 zero() {
        x = 0;
        y = 0;
        return this;
    }
	
	public boolean isInAABB(Vector2 min, Vector2 max) {
        return x >= min.x && x <= max.x && y >= min.y && y <= max.y;
    }
	
	public boolean isInCircle(Vector2 origin, double radius) {
        return (NumberConversions.square(origin.x - x) + NumberConversions.square(origin.y - y)) <= NumberConversions.square(radius);
    }
	
	public double getX() {
        return x;
    }
	
	public double getY() {
        return y;
    }
	
	public Vector2 setX(int x) {
        this.x = x;
        return this;
    }


    public Vector2 setX(double x) {
        this.x = x;
        return this;
    }
	
    public Vector2 setY(int y) {
        this.y = y;
        return this;
    }


    public Vector2 setY(double y) {
        this.y = y;
        return this;
    }
    
    public Vector2 setXY(int x, int y) {
    	this.x = x;
    	this.y = y;
    	return this;
    }
    
    public Vector2 setXY(double x, double y) {
    	this.x = x;
    	this.y = y;
    	return this;
    }
    
    public Vector2 rot(double a) {
    	double x = this.x;
    	double y = this.y;
    	double sina = Math.sin(a);
    	double cosa = Math.cos(a);
    	
    	this.x = cosa*x - sina*y;
    	this.y = sina*x + cosa*y;
    	return this;
    }
    
    public Vector2 flip() {
    	this.x *= -1;
    	this.y *= -1;
    	return this;
    }
    
    public Vector2 flip(int x, int y) {
    	return this.flip(new Vector2(x,y));
    }
    
    public Vector2 flip(double x, double y) {
    	return this.flip(new Vector2(x,y));
    }
    
    public Vector2 flip(Vector2 point) {
    	return this.add(point.clone().subtract(this).multiply(2));
    }
    
    public Vector2 flipX() {
    	this.x *= -1;
    	return this;
    }
    
    public Vector2 flipX(int x) {
    	this.x += 2 * (x-this.x);
    	return this;
    }
    
    public Vector2 flipX(double x) {
    	this.x += 2 * (x-this.x);
    	return this;
    }
    
    public Vector2 flipX(Vector2 plane) {
    	return this.flipX(plane.x);
    }  
    
    public Vector2 flipY() {
    	this.y *= -1;
    	return this;
    }
    
    public Vector2 flipY(int y) {
    	this.y += 2 * (y-this.y);
    	return this;
    }
    
    public Vector2 flipY(double y) {
    	this.y += 2 * (y-this.y);
    	return this;
    }
    
    public Vector2 flipY(Vector2 plane) {
    	return this.flipY(plane.y);
    }
    
    public Vector2 scale(int factor) {
    	return this.multiply(factor);
    }
    
    public Vector2 scale(double factor) {
    	return this.multiply(factor);
    }
    
    public Vector2 scale(Vector2 point, int factor) {
    	return this.scale(point,(double) factor);
    }
    
    public Vector2 scale(Vector2 point, double factor) {
    	return this.add(this.clone().subtract(point).multiply(factor-1));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2)) {
            return false;
        }

        Vector2 other = (Vector2) obj;

        return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon && (this.getClass().equals(obj.getClass()));
    }
    
    @Override
    public Vector2 clone() {
        try {
            return (Vector2) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    @Override
    public String toString() {
        return x + "," + y;
    }
    
    public Vector toVector() {
    	return new Vector(y, 0, x);
    }
    
    public Vector toVector(int y) {
    	return new Vector(this.y, y, this.x);
    }
    
    public Vector toVector(double y) {
    	return new Vector(this.y, y, this.x);
    }

    public Location toLocation(World world) {
    	return new Location(world, y, 0, x);
    }
    
    public Location toLocation(World world, int y) {
    	return new Location(world, this.y, y, this.x);
    }

    public Location toLocation(World world, double y) {
    	return new Location(world, this.y, y, this.x);
    }
    
    public Location toLocation(World world, float yaw, float pitch) {
    	return new Location(world, y, 0, x, yaw, pitch);
    }
    
    public Location toLocation(World world, int y, float yaw, float pitch) {
    	return new Location(world, this.y, y, this.x, yaw, pitch);
    }
    
    public Location toLocation(World world, double y, float yaw, float pitch) {
    	return new Location(world, this.y, y, this.x, yaw, pitch);
    }
    
    public BlockVector toBlockVector() {
        return new BlockVector(this.y, 0, this.x);
    }
    
    
    
    public static double getEpsilon() {
        return epsilon;
    }
    
    public static Vector2 getMinimum(Vector2 v1, Vector2 v2) {
        return new Vector2(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y));
    }
    
    public static Vector2 getMaximum(Vector2 v1, Vector2 v2) {
        return new Vector2(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y));
    }
    
    public static Vector2 getRandom() {
        return new Vector2(random.nextDouble(), random.nextDouble());
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("x", getX());
        result.put("y", getY());

        return result;
    }

    public static Vector2 deserialize(Map<String, Object> args) {
        double x = 0;
        double y = 0;

        if (args.containsKey("x")) {
            x = (Double) args.get("x");
        }
        if (args.containsKey("y")) {
            y = (Double) args.get("y");
        }

        return new Vector2(x, y);
    }
}

