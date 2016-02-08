package com.gmail.bi0qaw.dragonSphereZ;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class dragonSphereZ extends JavaPlugin{
	
	public void onEnable(){
		Skript.registerExpression(ExprCircle.class, Location.class, ExpressionType.SIMPLE, "testcircle at %location% with radius %number% and density %number%");
	
	}
	
	public void onDisable() {
		
	}
}
