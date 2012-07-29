package com.latibro.bukkit.plugin.groovytrigger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Bukkit Plugin
 * http://wiki.bukkit.org/Plugin_Tutorial#Starting_a_Plugin_Project
 * http://jd.bukkit.org/apidocs/
 * 
 * Groovy
 * http://groovy.codehaus.org/
 * http://groovy.codehaus.org/Embedding+Groovy
 * 
 * Simular project (Home made scripting language) (Closed source)
 * http://dev.bukkit.org/server-mods/variabletriggers
 */

public class GroovyTriggerPlugin extends JavaPlugin {
	
	private final List<Script> scripts = new ArrayList<Script>();

	public void onEnable() {
		getLogger().info("Enabling your plugin...");

		getConfig().options().copyDefaults(true);
		saveConfig();

		// Look for defaults in the jar
		InputStream defaultConfigStream = this.getResource("default-config.yml");
		if (defaultConfigStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
			getLogger().info("default-config: " + defaultConfig.saveToString());
			getConfig().setDefaults(defaultConfig);
		}
		
		getLogger().info("config: " + getConfig().saveToString());

		ConfigurationSection scriptsConfig = getConfig().getConfigurationSection("scripts");

		for (String scriptName : scriptsConfig.getKeys(false)) {
			getLogger().info("Script: " + scriptName);

			ConfigurationSection scriptConfig = scriptsConfig.getConfigurationSection(scriptName);

			getLogger().info("Script settings: " + scriptConfig);

			Script script = new Script(this, scriptConfig);

			scripts.add(script);
		}

		getLogger().info("Your plugin has been enabled!");
	}

	public void onDisable() {
		getLogger().info("Your plugin has been disabled.");
	}

	public Class resolveTriggerClass(String triggerName) {
		if (!getConfig().contains("triggers." + triggerName)) {
			throw new RuntimeException("Unknown trigger: " + triggerName);
		}

		String className;
		if (getConfig().contains("triggers." + triggerName + ".trigger")) {
			className = getConfig().getString("triggers." + triggerName + ".trigger");
		} else {
			className = getConfig().getString("triggers.default.trigger");
		}
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to load trigger class: " + triggerName, e);
		}
	}

	public Class resolveEventClass(String triggerName) {
		if (!getConfig().contains("triggers." + triggerName)) {
			throw new RuntimeException("Unknown trigger: " + triggerName);
		}
		String className = getConfig().getString("triggers." + triggerName + ".event");
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to load trigger class: " + triggerName, e);
		}
	}

}
