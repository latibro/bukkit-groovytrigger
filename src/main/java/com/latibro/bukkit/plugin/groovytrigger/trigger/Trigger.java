package com.latibro.bukkit.plugin.groovytrigger.trigger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import com.latibro.bukkit.plugin.groovytrigger.GroovyTriggerPlugin;
import com.latibro.bukkit.plugin.groovytrigger.Script;

public abstract class Trigger implements Listener, EventExecutor {

	protected final String	name;
	protected final Script	script;
	protected final String	command;

	public Trigger(Script script, ConfigurationSection config) {
		this.script = script;
		this.name = config.getName();
		this.command = config.getString("command");

		Class<? extends Event> eventClass = getPlugin().resolveEventClass(name);

		getLogger().info("Register trigger: " + name);

		getPlugin().getServer().getPluginManager().registerEvent(eventClass, this, EventPriority.HIGHEST, this, getPlugin(), false);
	}

	public GroovyTriggerPlugin getPlugin() {
		return script.getPlugin();
	}

	public Logger getLogger() {
		return script.getLogger();
	}

	public void execute(Listener listener, Event event) throws EventException {
		triggerScriptExecution(event);
	}

	protected void triggerScriptExecution(Event event) {
		triggerScriptExecution(event, null);
	}

	protected void triggerScriptExecution(Event event, Map<String, Object> variables) {

		getLogger().info("Event triggered: " + event);

		if (variables == null) {
			variables = new HashMap<String, Object>();
		}

		variables.put("trigger", this);
		variables.put("event", event);

		script.execute(variables, command);
	}

}
