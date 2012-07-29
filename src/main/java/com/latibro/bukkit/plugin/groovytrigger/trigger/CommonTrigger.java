package com.latibro.bukkit.plugin.groovytrigger.trigger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.EventExecutor;

import com.latibro.bukkit.plugin.groovytrigger.Script;


public class CommonTrigger extends Trigger {

	public CommonTrigger(Script script, ConfigurationSection config) {
		super(script, config);
	}

}
