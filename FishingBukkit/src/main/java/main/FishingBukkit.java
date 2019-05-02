package main;

import org.bukkit.plugin.java.JavaPlugin;

import listener.FishingListener;

public class FishingBukkit extends JavaPlugin{

	public static FishingBukkit instance;
	@Override
	public void onEnable() {
		instance = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

		
		try {
			getServer().getPluginManager().registerEvents(new FishingListener(), this);
		} catch (Exception e) {
			getLogger().info("Fishing Listen Failed");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		getLogger().info("onEnable has been invoked!");
			
	}

	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
	}

}
