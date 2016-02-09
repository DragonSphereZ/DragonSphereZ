package ud.bi0.dragonSphereZ;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ud.bi0.dragonSphereZ.skriptAPI.registers.registerEffects;
import ud.bi0.dragonSphereZ.skriptAPI.registers.registerExpressions;


public class dragonSphereCore extends JavaPlugin {
	public static dragonSphereCore dragonSphereCore;
	private static dragonSphereCore instance;

    public dragonSphereCore() {
    	instance = this;    
    }
    
	public void onEnable() {
		dragonSphereCore = this;

		Plugin skript = Bukkit.getServer().getPluginManager().getPlugin("Skript");
		if (skript != null) {
			Skript.registerAddon(this);
			Bukkit.getServer().getLogger().info("[DragonSphereZ] Plugin has been Enabled");
			registerEffects.DragonSphereZ();
			registerExpressions.DragonSphereZ();
			
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
			getLogger().info( "[DragonSphereZ] Plugin is now disabled. Why you no haz Skript?" );
		}
	} 
	
	public void onDisable() {
        HandlerList.unregisterAll(this);
		getLogger().info( "[DragonSphereZ] Plugin is now disabled. Most likely you shut down or reset your server" );
		}

    public static dragonSphereCore instance() {
        return instance;
    }
}