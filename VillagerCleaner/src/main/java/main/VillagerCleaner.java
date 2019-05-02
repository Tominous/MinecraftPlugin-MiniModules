package main;

import org.bukkit.plugin.java.JavaPlugin;

import listener.VillagerListener;

public class VillagerCleaner extends JavaPlugin{
	
	public static VillagerCleaner instance;
	@Override
	public void onEnable() {
		instance = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

		
		try {
			getServer().getPluginManager().registerEvents(new VillagerListener(), this);
		} catch (Exception e) {
			getLogger().info("Villager Listen Failed");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		getLogger().info("onEnable has been invoked!");
			
	}

	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
	}

}
