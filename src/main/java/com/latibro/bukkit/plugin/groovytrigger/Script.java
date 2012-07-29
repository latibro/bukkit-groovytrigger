package com.latibro.bukkit.plugin.groovytrigger;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.latibro.bukkit.plugin.groovytrigger.trigger.Trigger;


public class Script {
	
	private final GroovyTriggerPlugin plugin;

	private final String name;
	
	private String scriptContent;
	private final List<Trigger> triggers = new ArrayList<Trigger>();

	private YamlConfiguration config;

	public Script(GroovyTriggerPlugin plugin, ConfigurationSection config) {
		this.plugin = plugin;
		
		name = config.getName();
		
		File configFile = new File(getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);	
		
		try {
			File scriptFile = new File(getDataFolder(), "main.groovy");
			Scanner scan = new Scanner(scriptFile);
			scan.useDelimiter("\\Z");
			scriptContent = scan.next();

			getLogger().info(name + " - scriptContent: " + scriptContent);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Unable to load script: " + name, e);
		}
		
		ConfigurationSection triggersConfig = config.getConfigurationSection("triggers");
		for (String triggerName : triggersConfig.getKeys(false)) {
			ConfigurationSection triggerConfig = triggersConfig.getConfigurationSection(triggerName);
			
			Trigger trigger = newTriggerInstance(triggerName, triggerConfig);

			triggers.add(trigger);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public GroovyTriggerPlugin getPlugin() {
		return plugin;
	}
	
	public Logger getLogger() {
		return plugin.getLogger();
	}
	
	public File getDataFolder() {
		return new File(plugin.getDataFolder(), "scripts/" + name);
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public void execute(Map<String, Object> variables) {
		execute(variables, null);
	}

	public void execute(String triggerScript) {
		execute(null, triggerScript);
	}
	
	public void execute(Map<String, Object> variables, String command) {
		getLogger().info("Executing script: " + name);

		if (variables == null) {
			variables = new HashMap<String, Object>();
		}

		Binding binding = new Binding(variables);
		binding.setVariable("plugin", getPlugin());
		binding.setVariable("logger", getLogger());
		binding.setVariable("script", this);
		binding.setVariable("config", getConfig());
		
		GroovyShell groovyShell = new GroovyShell(binding);

		String scriptContent = this.scriptContent;
		if (command != null) {
			scriptContent += "\n\n" + command;
		}
		
		Object groovyResult = groovyShell.evaluate(scriptContent);
	}
	

	public Trigger newTriggerInstance(String triggerName, ConfigurationSection triggerConfig) {
		try {
			return (Trigger) plugin.resolveTriggerClass(triggerName).getConstructor(Script.class, ConfigurationSection.class).newInstance(this, triggerConfig);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create instance of trigger: " + triggerName, e);
		}
	}

}
