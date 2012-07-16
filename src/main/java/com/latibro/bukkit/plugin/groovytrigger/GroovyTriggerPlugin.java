package com.latibro.bukkit.plugin.groovytrigger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Bukkit Plugin
 * http://wiki.bukkit.org/Plugin_Tutorial#Starting_a_Plugin_Project
 * 
 * Groovy
 * http://groovy.codehaus.org/
 * http://groovy.codehaus.org/Embedding+Groovy
 * 
 * Simular project (Closed source)
 * http://dev.bukkit.org/server-mods/variabletriggers
 */

public class GroovyTriggerPlugin extends JavaPlugin {

	public void onEnable(){
		getLogger().info("Your plugin has been enabled!");
	}
 
	public void onDisable(){
		getLogger().info("Your plugin has been disabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("basic")){ // If the player typed /basic then do the following...
			// doSomething
			return true;
		} //If this has happened the function will break and return true. if this hasn't happened the a value of false will be returned.
		return false; 
	}
	
}
