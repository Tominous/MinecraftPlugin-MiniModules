package main;

import org.bukkit.plugin.java.JavaPlugin;

import listener.HorseListener;


public class HorseIndicator extends JavaPlugin{

	public static HorseIndicator instance;
	@Override
	public void onEnable() {
		instance = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

		
		try {
			getServer().getPluginManager().registerEvents(new HorseListener(), this);
		} catch (Exception e) {
			getLogger().info("Horse Listen Failed");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		getLogger().info("onEnable has been invoked!");
			
	}

	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
	}

}
