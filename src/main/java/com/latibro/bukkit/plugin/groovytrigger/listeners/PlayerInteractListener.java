package com.latibro.bukkit.plugin.groovytrigger.listeners;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.latibro.bukkit.plugin.groovytrigger.GroovyTriggerPlugin;

public class PlayerInteractListener implements Listener {

	private final GroovyTriggerPlugin plugin;
	
	public PlayerInteractListener(GroovyTriggerPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		//event.getPlayer().sendMessage("Interaction with " + event.getClickedBlock().getType() + " [" + event.getClickedBlock().getLocation() + "]");
		
		Binding groovyBinding = new Binding();
		groovyBinding.setVariable("event", event);
		GroovyShell groovyShell = new GroovyShell(groovyBinding);

		Object groovyResult = groovyShell.evaluate("event.getPlayer().sendMessage(\"Interaction with \" + event.getClickedBlock().getType() + \" [\" + event.getClickedBlock().getLocation() + \"]\");");
	}
	
}
