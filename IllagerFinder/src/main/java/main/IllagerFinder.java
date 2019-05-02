package main;

import org.bukkit.plugin.java.JavaPlugin;

import listener.IllagerListener;


public class IllagerFinder extends JavaPlugin{
	
	public static IllagerFinder instance;
	@Override
	public void onEnable() {
		instance = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

		
		try {
			getServer().getPluginManager().registerEvents(new IllagerListener(), this);
		} catch (Exception e) {
			getLogger().info("Illager Listen Failed");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		getLogger().info("onEnable has been invoked!");
			
	}

	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
	}

}
