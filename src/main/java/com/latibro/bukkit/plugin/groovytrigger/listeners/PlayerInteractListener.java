package com.latibro.bukkit.plugin.groovytrigger.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.latibro.bukkit.plugin.groovytrigger.GroovyTriggerPlugin;

public class PlayerInteractListener implements Listener {

	private final GroovyTriggerPlugin plugin;
	private final ConfigurationSection config;
	private String scriptContent;
	
	public PlayerInteractListener(GroovyTriggerPlugin plugin, ConfigurationSection config) {
		this.plugin = plugin;
		this.config = config;
		
		String fileName = config.getString("file");
		
		try {
			File scriptFile = new File(plugin.getDataFolder().getPath(), "scripts/" + fileName + ".groovy");
			Scanner scan = new Scanner(scriptFile);
			scan.useDelimiter("\\Z");
			scriptContent = scan.next();

			plugin.getLogger().info("content: " + scriptContent);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		//event.getPlayer().sendMessage("Interaction with " + event.getClickedBlock().getType() + " [" + event.getClickedBlock().getLocation() + "]");
		
		Binding groovyBinding = new Binding();
		groovyBinding.setVariable("plugin", plugin);
		groovyBinding.setVariable("config", config);
		groovyBinding.setVariable("event", event);
		GroovyShell groovyShell = new GroovyShell(groovyBinding);

		Object groovyResult = groovyShell.evaluate(scriptContent);
//		Object groovyResult = groovyShell.evaluate("event.getPlayer().sendMessage(\"Interaction with \" + event.getClickedBlock().getType() + \" [\" + event.getClickedBlock().getLocation() + \"]\");");
	}
	
}
