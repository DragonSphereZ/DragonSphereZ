package ud.bi0.dragonSphereZ.maths.vector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.BlockVector;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import ud.bi0.dragonSphereZ.maths.shape.Plane;

@SerializableAs("Vector3")
public class Vector3
	implements Cloneable, Serializable
{

	/**
	 * Class for 3D vectors in the Cartesian coordinate system.
	 * Each vector has a x, y and z value.
	 * Note that the x, y and z coordinates are different from
	 * the minecraft x, y and z coordinates!
	 * The conversion is as follows:
	 * 	Vector3		Minecraft
	 * 		x	=	z
	 * 		y 	=	x
	 * 		z 	= 	y
	 * 
	 */
	private static final long serialVersionUID = -7163599704997417903L;

	private static final double epsilon = 1e-6;
	
	private static Random random = new Random();
	
	private double x;
	private double y;
	private double z;
	
	
	/**
	 * Get a new zero vector.
	 * 
	 */
	public Vector3() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	/**
	 * Get a new vector with coordinates x, y, z.
	 * 
	 */
	public Vector3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Get a new vector with coordinates x, y, z.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Get a new vector from a yaw and pitch.
	 * 
	 */
	public Vector3(double yaw, double pitch) {
		double radYaw = Math.toRadians(yaw + 90F);
		double radPitch = Math.toRadians(pitch + 90F);
		double sinYaw = Math.sin(radYaw);
		double cosYaw = Math.cos(radYaw);
		double sinPitch = Math.sin(radPitch);
		double cosPitch = Math.cos(radPitch);
		x = cosPitch * sinYaw;
		y = sinPitch * sinYaw;
		z = cosYaw;
	}
	
	/**
	 * Gets a copy of another vector.
	 * 
	 */
	public Vector3(Vector vec) {
		this.x = vec.getZ();
		this.y = vec.getX();
		this.z = vec.getY();
	}
	
	/**
	 * Gets a vector from a location.
	 * 
	 */
	public Vector3(Location loc) {
		this.x = loc.getZ();
		this.y = loc.getX();
		this.z = loc.getY();
	}
	
	/**
	 * Gets a list of vectors from a list of 
	 * locations.
	 * 
	 */
	public List<Vector3> getList(Location[] locs) {
		int size = locs.length;
		List<Vector3> vectors = new ArrayList<Vector3>(size);
		for (int i = 0; i < size; i++) {
			vectors.add(new Vector3(locs[i]));
		}
		return vectors;
	}
	
	/**
	 * Adds another vector to this one.
	 * 
	 */
	public Vector3 add(Vector3 vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}
	
	/**
	 * Adds another vector to this one.
	 * 
	 */
	public Vector3 add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Adds another vector to this one.
	 * 
	 */
	public Vector3 add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Adds a multiple of another vector to this one.
	 * 
	 */
	public Vector3 add(int factor, Vector3 vec) {
		x += factor * vec.x;
		y += factor * vec.y;
		z += factor * vec.z;
		return this;		
	}
	
	/**
	 * Adds a mutiple of another vector to this one.
	 * 
	 */
	public Vector3 add(double factor, Vector3 vec) {
		x += factor * vec.x;
		y += factor * vec.y;
		z += factor * vec.z;
		return this;
	}
	
	/**
	 * Subtracts another vector from this one.
	 * 
	 */
	public Vector3 subtract(Vector3 vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}
	
	/**
	 * Subtracts a multiple of another vector from
	 * this one.
	 * 
	 */
	public Vector3 subtract(int factor, Vector3 vec) {
		x -= factor * vec.x;
		y -= factor * vec.y;
		z -= factor * vec.z;
		return this;
	}
	
	/**
	 * Subtracts a multiple of another vector from this one.
	 * 
	 */
	public Vector3 subtract(double factor, Vector3 vec) {
		x -= factor * vec.x;
		y -= factor * vec.y;
		z -= factor * vec.z;
		return this;
	}
	
	/**
	 * Multiplies this vector by another.
	 * 
	 */
	
	public Vector3 multiply(Vector3 vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}
	
	/**
	 * Divides this vector by another.
	 * 
	 */
	public Vector3 divide(Vector3 vec) {
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}
	
	/**
	 * Changes the position of the vector with respect 
	 * to the position change of another vector.
	 * 
	 */
	public Vector3 move(Vector3 from, Vector3 to) {
		return this.subtract(from).add(to);
	}
	
	/**
	 * Changes the positions of a list of vectors
	 * with respect to the position change of another
	 * vector.
	 * 
	 */
	public List<Vector3> move(Vector3 from, Vector3 to, List<Vector3> vectors) {
		Vector3 change = to.clone().subtract(from);
		for (Vector3 vector : vectors) {
			vector.add(change);
		}
		return vectors;
	}
	
	/**
	 * Copies another vector to this one.
	 * 
	 */
	public Vector3 copy(Vector3 vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}
	
	/**
	 * Copies the coordinates of a location to a vector.
	 */
	public Vector3 copy(Location location) {
		this.x = location.getZ();
		this.y = location.getX();
		this.z = location.getY();
		return this;
	}

	/**
	 * Copies the coordinates of a bukkit vector to a vector.
	 */
	public Vector3 copy(Vector vector) {
		this.x = vector.getZ();
		this.y = vector.getX();
		this.z = vector.getY();
		return this;
	}
	
	/**
	 * Gets the length of this vector.
	 * Note: Use lengthSquared() if possible as it is a lot faster.
	 * 
	 */
	public double length() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public double lengthSquared() {
        return x*x + y*y + z*z;
    }
	
	/**
	 * Gets the distance between this vector and another.
	 * 
	 */
	public double distance(Vector3 other) {
        return Math.sqrt(NumberConversions.square(x - other.x) + NumberConversions.square(y - other.y) + NumberConversions.square(z - other.z));
    }
	
	/**
	 * Gets the squared distance between this vector and another.
	 * 
	 */
	public double distanceSquared(Vector3 other) {
        return NumberConversions.square(x - other.x) + NumberConversions.square(y - other.y) + NumberConversions.square(z - other.z);
    }
	
	/**
	 * Gets the cosine of the angle between this vector and another.
	 * 
	 */
	public float angle(Vector3 other) {
		return (float) (dot(other) * Math.abs(dot(other)) / (lengthSquared() * other.lengthSquared()));
    }
	
	/**
	 * Gets the midpoint between this vector and another.
	 * 
	 */
	public Vector3 midpoint(Vector3 other) {
        x = (x + other.x) / 2;
        y = (y + other.y) / 2;
        z = (z + other.z) / 2;
        return this;
    }
	
	/**
	 * Returns the normal vector of this vector and another.
	 * 
	 */
	public Vector3 getNormal(Vector3 other) {
		return this.crossProduct(other).normalize();
	}
	
	/** 
	 * Returns a new vector with the midpoint of this vector and another.
	 * 
	 */
	public Vector3 getMidpoint(Vector3 other) {
        double x = (this.x + other.x) / 2;
        double y = (this.y + other.y) / 2;
        double z = (this.z + other.z) / 2;
        return new Vector3(x, y, z);
    }
	
	/**
	 * Returns the midpoint of a list of vectors.
	 * 
	 */
	public Vector3 getMidpoint(List<Vector3> vectors) {
		int size = vectors.size();
		Vector3 midpoint = new Vector3();
		for (Vector3 vector : vectors) {
			midpoint.add(vector);
		}
		midpoint.multiply(1/size);
		return midpoint;
	}
	
	/**
	 * Returns a new point from a base.
	 * 
	 */
	public Vector3 getCoordinate(
			Vector3	u,
			Vector3 v,
			Vector3 w,
			double factorU,
			double factorV,
			double factorW)
	{
		return this.clone()
				.add(factorU, u)
				.add(factorV, v)
				.add(factorW, w);
	}
	
	/**
	 * Returns a new point on a cylinder.
	 * 
	 */
	public Vector3 getCylinderCoordinate(
			Vector3 normal,
			Vector3 u,
			Vector3 v,
			double radiusU,
			double radiusV,
			double angle,
			double height) 
	{
		return this.clone()
				.add(radiusU*Math.cos(angle), u)
				.add(radiusV*Math.sin(angle), v)
				.add(height, normal);
	}
	
	/**
	 * Returns a new point on a sphere.
	 * 
	 */
	public Vector3 getSphereCoordinate(
			Vector3 u, 
			Vector3 v, 
			Vector3 w, 
			double radiusU, 
			double radiusV, 
			double radiusW, 
			double angleThetha, 
			double anglePhi) 
	{
		return this.clone()
				.add(radiusU*Math.cos(anglePhi)*Math.sin(angleThetha), u)
				.add(radiusV*Math.sin(anglePhi)*Math.sin(angleThetha), v)
				.add(radiusW*Math.cos(angleThetha), w);
	}
	
	/**
	 * Multiplies this vector by a scalar.
	 *
	 */
	public Vector3 multiply(int m) {
        x *= m;
        y *= m;
        z *= m;
        return this;
    }
	
	/**
	 * Multiplies this vector by a scalar.
	 * 
	 */
	public Vector3 multiply(double m) {
        x *= m;
        y *= m;
        z *= m;
        return this;
    }
	
	/**
	 * Returns the dot product of this vector and another.
	 *
	 */
	public double dot(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }
	
	/**
	 * Calculates the cross product of this vector and another.
	 * 
	 */
	public Vector3 crossProduct(Vector3 o) {
        double newX = y * o.z - o.y * z;
        double newY = z * o.x - o.z * x;
        double newZ = x * o.y - o.x * y;

        x = newX;
        y = newY;
        z = newZ;
        return this;
    }
	
	/**
	 * Sets the length of the vector to 1.
	 * 
	 */
	public Vector3 normalize() {
        double invLength = 1 / length();

        x *= invLength;
        y *= invLength;
        z *= invLength;

        return this;
    }
	
	/**
	 * Nullifies all components of the vector.
	 * 
	 */
	public Vector3 zero() {
        x = 0;
        y = 0;
        z = 0;
        return this;
    }
	
	/**
	 * Projects this vector onto another.
	 * 
	 */
	public Vector3 project(Vector3 other) {
		Vector3 direction = other.clone().normalize();
		return direction.multiply(this.dot(direction));
	}
	
	/**
	 * Projects this vector onto a plane.
	 * 
	 */
	public Vector3 projectPlane(Vector3 normal) {
		return this.subtract(this.dot(normal), normal);
	}
	
	/**
	 * Projects this vector onto a plane.
	 * 
	 */
	public Vector3 projectPlane(Plane plane) {
		return projectPlane(plane.getBase().getNormal());
	}
	
	/**
	 * Checks whether the vector is parallel to another.
	 * Uses the property that the dot product of two
	 * parallel vectors is equal to the product of the
	 * two lengths of these vectors.
	 * 
	 */
	public boolean isParallel(Vector3 other) {
		double dot = Math.abs(this.dot(other));
		double lengthSquared = this.lengthSquared()*other.lengthSquared();
		if (dot * dot > lengthSquared - epsilon) return true;
		return false;
	}
	
	/**
	 * Checks whether the vector points in the same
	 * direction as another. Uses the property that 
	 * the dot product of two parallel vectors with
	 * the same direction is positive.
	 * 
	 */
	public boolean hasSameDirection(Vector3 other) {
		if (this.dot(other) > 0 && this.isParallel(other)) return true;
		return false;
	}
	
	/**
	 * Checks whether the vector is orthogonal to another
	 * Uses the property that the dot product of two
	 * orthogonal vectors is equal to 0. This returns
	 * even true if the two vectors point in the 
	 * opposite directions!
	 * 
	 */
	public boolean isOrthogonal(Vector3 other) {
		if (Math.abs(this.dot(other)) < epsilon) return true;
		return false;
	}
	
	/**
	 * Checks whether the vector is within an axis aligned box.
	 *
	 */
	public boolean isInAABB(Vector3 min, Vector3 max) {
        return x >= min.x && x <= max.x && y >= min.y && y <= max.y && z >= min.z && z <= max.z;
    }
	
	/**
	 * Checks whether the vector is within a sphere.
	 * 
	 */
	public boolean isInSphere(Vector3 origin, double radius) {
        return (NumberConversions.square(origin.x - x) + NumberConversions.square(origin.y - y) + NumberConversions.square(origin.z - z)) <= NumberConversions.square(radius);
    }
	
	/**
	 * Get the x-coordinate of the vector.
	 * 
	 */
	public double getX() {
        return x;
    }
	
	/**
	 * Get the y-coordinate of the vector.
	 * 
	 */
	public double getY() {
        return y;
    }
	
	/**
	 * Get the z-coordinate of the vector.
	 * 
	 */
	public double getZ() {
        return z;
    }
	
	/**
	 * Set the x-coordinate of the vector.
	 * 
	 */
	public Vector3 setX(int x) {
        this.x = x;
        return this;
    }

	/**
	 * Set the x-coordinate of the vector.
	 * 
	 */
    public Vector3 setX(double x) {
        this.x = x;
        return this;
    }
	
    /**
     * Set the y-coordinate of the vector.
     * 
     */
    public Vector3 setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Set the y-coordinate of the vector.
     * 
     */
    public Vector3 setY(double y) {
        this.y = y;
        return this;
    }
    
    /**
     * Set the z-coordinate of the vector.
     * 
     */
    public Vector3 setZ(int z) {
        this.z = z;
        return this;
    }

    /**
     * Set the z-coordinate of the vector.
     */
    public Vector3 setZ(double z) {
        this.z = z;
        return this;
    }
    
    /**
     * Set the x, y and z coordinates of the vector.
     * 
     */
    public Vector3 setXYZ(int x, int y, int z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    	return this;
    }
    
    /**
     * Set the x, y and z coordinates of the vector.
     * 
     */
    public Vector3 setXYZ(double x, double y, double z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    	return this;
    }
    
    
    /**
     * Rotates the vector around the x-axis.
     * 
     */
    public Vector3 rotX(double a) {
    	double y = this.y;
    	double z = this.z;
    	double sina = Math.sin(a);
    	double cosa = Math.cos(a);
    	
    	this.y = cosa*y - sina*z;
    	this.z = sina*y + cosa*z;
    	return this;
    }
    
    /**
     * Rotates the vector around the y-axis
     * 
     */
    public Vector3 rotY(double a) {
    	double x = this.x;
    	double z = this.z;
    	double sina = Math.sin(a);
    	double cosa = Math.cos(a);
    	
    	this.x = cosa*x + sina*z;
    	this.z = -sina*x + cosa*z;
    	return this;
    }
    
    /**
     * Rotates the vector around the z-axis
     * 
     */
    public Vector3 rotZ(double a) {
    	double x = this.x;
    	double y = this.y;
    	double sina = Math.sin(a);
    	double cosa = Math.cos(a);
    	
    	this.x = cosa*x - sina*y;
    	this.y = sina*x + cosa*y;
    	return this;
    }
    
    /**
     * Rotates the vector around the 
     * x-axis by a, the y-axis by b and 
     * the z-axis by c.
     *
     */
    
    public Vector3 rotXYZ(double a, double b, double c) {
    	return this.rotX(a).rotY(b).rotZ(c);
    }
    
    /**
     * Rotates the vector around the x-/y- and z-axis by 
     * the values of the rotation vector.
     * 
     */
    public Vector3 rotXYZ(Vector3 rotation) {
    	return this.rotXYZ(rotation.x, rotation.y, rotation.z);
    }
    /**
     * Rotates the vector around an axis described by (x,y,z).
     * 
     */
    public Vector3 rot(double x, double y, double z, double a) {
    	return rot(new Vector3(x,y,z), a);
    }
    
    /**
     * Rotates the vector around an axis described by vec.
     * 
     */   
    public Vector3 rot(Vector3 vec, double a) {
    	Vector3 n = vec.clone().normalize();
    	double n1 = n.getX();
    	double n2 = n.getY();
    	double n3 = n.getZ();
    	double x = this.x;
    	double y = this.y;
    	double z = this.z;
    	double cosa = Math.cos(a);
    	double sina = Math.sin(a);
    	
    	this.x = (n1*n1*(1-cosa)+cosa)*x + (n1*n2*(1-cosa)-n3*sina)*y + (n1*n3*(1-cosa)+n2*sina)*z;
    	this.y = (n2*n1*(1-cosa)+n3*sina)*x + (n2*n2*(1-cosa)+cosa)*y + (n2*n3*(1-cosa)-n1*sina)*z;
    	this.z = (n3*n1*(1-cosa)-n2*sina)*x + (n3*n2*(1-cosa)+n1*sina)*y + (n3*n3*(1-cosa)+cosa)*z;
    	return this;
    	
    }
    
    /**
     * Rotates a vector with a Rotator3
     * 
     */
    public Vector3 rot(Rotator3 rotator) {
    	return this.setXYZ(rotator.getRotatorX().dot(this), rotator.getRotatorY().dot(this), rotator.getRotatorZ().dot(this));
    }
    
    /**
     * Adjusts the vector to be orthogonal to direction.
     * Used for base rotations.
     * 
     */
    public void adjust(Vector3 direction) {
    	if (direction.isParallel(direction)) return;
    	Vector3 normal = direction.clone().normalize();
		this.copy(normal.clone().crossProduct(this).crossProduct(normal)); 
    }
    
    /**
     * Adjusts the vector with respect to the
     * change of another vector. 
     * Used for base rotations.
     * 
     */
    public void adjust(Vector3 from, Vector3 to) {
    	if (from.isParallel(to)) return;
    	if (this.isOrthogonal(from)) adjust(to); // Uses the faster algorithm for orthogonal vectors.
    	Vector3 axis = to.clone().crossProduct(from).normalize();
    	double angle = to.angle(from);
    	this.rot(axis, angle);
    }
    
    /**
     * Flips the vector at (0,0,0) meaning that it 
     * switches all the signs of the components.
     * Example:
     *  (1,2,-3) -> (-1,-2,3)
     */
    public Vector3 flip() {
    	this.x *= -1;
    	this.y *= -1;
    	this.z *= -1;
    	return this;
    }
    
    /**
     * Flips the vector at the point at (x,y,z).
     * Example:
     *  (1,2,-3) flip (0,1,0) -> (-1,0,-3)
     */
    public Vector3 flip(int x, int y, int z) {
    	return this.flip(new Vector3(x,y,z));
    }
 
    /**
     * Flips the vector at the point at (x,y,z).
     * Example:
     *  (1,2,-3) flip (0,1,0) -> (-1,0,-3)
     */
    public Vector3 flip(double x, double y, double z) {
    	return this.flip(new Vector3(x,y,z));
    }

    /**
     * Flips the vector at point.
     * Example:
     *  point = (0,1,0)
     *  (1,2,-3) flip (point) -> (-1,0,-3)
     */
    public Vector3 flip(Vector3 point) {
    	return this.add(point.clone().subtract(this).multiply(2));
    }
    
    /**
     * Flips the x-coordinate of the vector.
     * Example:
     * 	(1,2,-3) -> (-1,2,-3)
     * 	
     */
    public Vector3 flipX() {
    	this.x *= -1;
    	return this;
    }

    /**
     * Flips the x-coordinate of the vector at x.
     * Example:
     * 	(1,2,-3) flipX (3) -> (5,2,-3)
     * 	
     */
    public Vector3 flipX(int x) {
    	this.x += 2 * (x-this.x);
    	return this;
    }

    /**
     * Flips the x-coordinate of the vector at x.
     * Example:
     * 	(1,2,-3) flipX (3) -> (5,2,-3)
     * 	
     */
    public Vector3 flipX(double x) {
    	this.x += 2 * (x-this.x);
    	return this;
    }
   
    /**
     * Flips the x-coordinate of the vector at the x-coordinate of point.
     * Example:
     * 	(1,2,-3) flipX (3,y,z) -> (5,2,-3)
     * 	
     */
    public Vector3 flipX(Vector3 point) {
    	return this.flipX(point.x);
    }  
    
    /**
     * Flips the y-coordinate of the vector.
     * Example:
     *  (1,2,-3) -> (1,-2,-3)
     */
    public Vector3 flipY() {
    	this.y *= -1;
    	return this;
    }
    
    /**
     * Flips the y-coordinate of the vector at y.
     * Example:
     *  (1,2,-3) flip (-1) -> (1,-6,-3)
     */
    public Vector3 flipY(int y) {
    	this.y += 2 * (y-this.y);
    	return this;
    }

    /**
     * Flips the y-coordinate of the vector at y.
     * Example:
     *  (1,2,-3) flip (-1) -> (1,-6,-3)
     */
    public Vector3 flipY(double y) {
    	this.y += 2 * (y-this.y);
    	return this;
    }
    
    /**
     * Flips the y-coordinate of the vector at the y-coordinate of point.
     * Example:
     *  (1,2,-3) flip (x,-1,z) -> (1,-6,-3)
     */
    public Vector3 flipY(Vector3 point) {
    	return this.flipY(point.y);
    }
    
    /**
     * Flips the z-coordinate of the vector at z.
     * Example:
     *  (1,2,-3) -> (1,2,3)
     */
    public Vector3 flipZ() {
    	this.z *= -1;
    	return this;
    }

    /**
     * Flips the z-coordinate of the vector at z.
     * Example:
     *  (1,2,-3) flip (-1) -> (1,2,1)
     */
    public Vector3 flipZ(int z) {
    	this.z += 2 * (z-this.z);
    	return this;
    }
    
    /**
     * Flips the z-coordinate of the vector at z.
     * Example:
     *  (1,2,-3) flip (-1) -> (1,2,1)
     */
    public Vector3 flipZ(double z) {
    	this.z += 2 * (z-this.z);
    	return this;
    }

    /**
     * Flips the z-coordinate of the vector at the z-coordinate of point.
     * Example:
     *  (1,2,-3) flip (x,y,-1) -> (1,2,1)
     */
    public Vector3 flipZ(Vector3 point) {
    	return this.flipZ(point.z);
    }
    
    /**
     * Flips the vector on the XY-Plane.
     * Example:
     * 	(1,2,-3) -> (-1,-2,-3)
     */
    public Vector3 flipXY() {
    	this.flipX();
    	this.flipY();
    	return this;
    }
    
    /**
     * Flips the vector on the XY-Plane at point.
     * Example:
     *  point = (0,1,z)
     * 	(1,2,-3) flip (point) -> (-1,0,-3)
     */
    public Vector3 flipXY(Vector3 point) {
    	this.flipX(point);
    	this.flipY(point);
    	return this;
    }   
 
    /**
     * Flips the vector on the XZ-Plane.
     * Example:
     * 	(1,2,-3) -> (-1,2,3)
     */
    public Vector3 flipXZ() {
    	this.flipX();
    	this.flipZ();
    	return this;
    }
    
    /**
     * Flips the vector on the XZ-Plane at point.
     * Example:
     *  point = (0,y,1)
     * 	(1,2,-3) flip (point) -> (-1,2,5)
     */
    public Vector3 flipXZ(Vector3 point) {
    	this.flipX(point);
    	this.flipZ(point);
    	return this;
    }   
    
    /**
     * Flips the vector on the YZ-Plane.
     * Example:
     * 	(1,2,-3) -> (1,-2,3)
     */
    public Vector3 flipYZ() {
    	this.flipY();
    	this.flipZ();
    	return this;
    }
    
    /**
     * Flips the vector on the YZ-Plane at point.
     * Example:
     *  point = (x,1,0)
     * 	(1,2,-3) flip (point) -> (1,0,3)
     */
    public Vector3 flipYZ(Vector3 point) {
    	this.flipY(point);
    	this.flipZ(point);
    	return this;
    }   
    
    /** Flips the vector in a plane defined by a position and a direction.
     * Example:
     * 	position = (1,0,0)
     * 	direction = (-1,1,0)
     * (1,2,-3) flip (position, direction) -> (-1,0,-3)
     */
    public Vector3 flip(Vector3 position, Vector3 direction) {
    	double factor = 2 * this.dot(direction) / direction.dot(direction);
    	this.subtract(position).subtract(direction.clone().multiply(factor));
    	this.add(position);
    	return this;
    }
    
    
    
    /**
     * Scales the vector by factor.
     * This is identical to multiply(factor).
     */
    public Vector3 scale(int factor) {
    	return this.multiply(factor);
    }
    
    /**
     * Scales the vector by factor.
     * This is identical to multiply(factor).
     */
    public Vector3 scale(double factor) {
    	return this.multiply(factor);
    }
    
    /**
     * Scales the vector by factor with the center at point.
     * Example:
     * 	point = (0,1,0)
     *  (1,2,-3) scale (point, 2) -> (2,3,-6)
     */
    public Vector3 scale(Vector3 point, int factor) {
    	return this.scale(point,(double) factor);
    }

    /**
     * Scales the vector by factor with the center at point.
     * Example:
     * 	point = (0,1,0)
     *  (1,2,-3) scale (point, 2) -> (2,3,-6)
     */
    public Vector3 scale(Vector3 point, double factor) {
    	return this.add(this.clone().subtract(point));
    }
    
    /**
     * Compares whether the vector is equal to an object.
     * The maximal error epsilon can be obtained with getEpsilon().
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector3)) {
            return false;
        }

        Vector3 other = (Vector3) obj;

        return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon && Math.abs(z - other.z) < epsilon && (this.getClass().equals(obj.getClass()));
    }
    
    /**
     * Clones the vector.
     * 
     */
    @Override
    public Vector3 clone() {
        try {
            return (Vector3) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    /**
     * Returns a string representation of the vector.
     * 
     */
    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
    
    /**
     * Changes a Vector3 into a Vector.
     * 
     */
    public Vector toVector() {
    	return new Vector(y, z, x);
    }

    /**
     * Returns a location in world.
     * 
     */
    public Location toLocation(World world) {
    	return new Location(world, y, z, x);
    }
    
    /**
     * Gets a location representation of the vector.
     * 
     */
    public Location toLocation(Location location) {
    	location.setX(y);
    	location.setY(z);
    	location.setZ(x);
    	return location;
    }
    
    /**
     * Returns a location in world with a yaw and pitch.
     * 
     */   
    public Location toLocation(World world, float yaw, float pitch) {
    	return new Location(world, y, z, x, yaw, pitch);
    }
    
    /**
     * Adds the vector to a location.
     * 
     */
    public Location addTo(Location location) {
    	location.add(this.y, this.z, this.x);
    	return location;
    }
    
    public Location subtractFrom(Location location) {
    	location.subtract(this.y, this.z, this.x);
    	return location;
    }

    /**
     * Returns the vector as a location array.
     * 
     */
    public Location[] locationArray(World world, Vector3 vector) {
    	return new Location[]{vector.toLocation(world)};
    }

    /**
     * Returns the vector as a location array with a yaw and pitch
     * 
     */
    public Location[] locationArray(World world, Vector3 vector, double yaw, double pitch) {
    	return new Location[]{vector.toLocation(world)};
    }
    
    /**
     * Returns a list of vectors as a location array.
     * 
     */
    public Location[] locationArray(World world, List<Vector3> vectors) {
    	return locationArray(world, vectors, 0, 0);
    }
      
    /**
     * Returns a list of vectors as a location array with a yaw and pitch.
     * 
     */
    public Location[] locationArray(World world, List<Vector3> vectors, double yaw, double pitch) {
    	Location[] locs = new Location[vectors.size()];
    	int i = 0;
    	for (Vector3 vec : vectors) {
    		locs[i] = vec.toLocation(world);
    		i++;
    	}
    	return locs;
    }   
    
    /**
     * Converts the vector to a block vector.
     * 
     */
    public BlockVector toBlockVector(BlockVector blockVector) {
    	blockVector.setX(y);
    	blockVector.setY(z);
    	blockVector.setZ(x);
        return blockVector;
    }
    
    /**
     * Returns the maximal error of the equals() method.
     */
    public static double getEpsilon() {
        return epsilon;
    }
    
    /**
     * Returns a vector with the minimal x/y/z-coordinates of the two vectors.
     */
    public static Vector3 getMinimum(Vector3 v1, Vector3 v2) {
        return new Vector3(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
    }
    
    /**
     * Returns a vector with the maximal x/y/z-coordinates of the two vectors.
     */
    public static Vector3 getMaximum(Vector3 v1, Vector3 v2) {
        return new Vector3(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
    }
    
    /**
     * Gets a random vector with components having a random value between 0 and 1.
     * Note: This vector is not normalized!
     */
    public static Vector3 getRandom() {
        return new Vector3(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("x", getX());
        result.put("y", getY());
        result.put("z", getZ());

        return result;
    }

    public static Vector3 deserialize(Map<String, Object> args) {
        double x = 0;
        double y = 0;
        double z = 0;

        if (args.containsKey("x")) {
            x = (Double) args.get("x");
        }
        if (args.containsKey("y")) {
            y = (Double) args.get("y");
        }
        if (args.containsKey("z")) {
            z = (Double) args.get("z");
        }

        return new Vector3(x, y, z);
    }
    
}

