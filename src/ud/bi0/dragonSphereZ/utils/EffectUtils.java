package ud.bi0.dragonSphereZ.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EffectUtils {
	
	public static Location getLocation(Object location) {
    	Location finalLocation = null;
    	if (location instanceof Entity) {
    		finalLocation = ((Entity) location).getLocation();
		}else if (location instanceof Location){
			finalLocation = new Location(((Location) location).getWorld(), ((Location) location).getX(), ((Location) location).getY(), ((Location) location).getZ());
		}
        return finalLocation;
    }
	
	
	
	
}