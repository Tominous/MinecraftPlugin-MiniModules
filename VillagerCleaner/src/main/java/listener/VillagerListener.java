package listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class VillagerListener implements Listener {
	@EventHandler
	public void onVillagerInteract(PlayerInteractEntityEvent event) {
		Entity entity = event.getRightClicked();
		EntityType entityType = entity.getType();

		if (entityType == EntityType.VILLAGER) {
			Player player = event.getPlayer();
			Villager beforeVillager = (Villager) entity;

			if (beforeVillager.getProfession() != Profession.NONE) {
				if (!beforeVillager.isTrading()) {
					if (player.isSneaking()) {
						event.setCancelled(true);
						
						PlayerInventory playerInventory = player.getInventory();
						ItemStack heldItem = playerInventory.getItemInMainHand();
						Material heldItemType = heldItem.getType();

						if (heldItemType == Material.EMERALD) {
							Villager afterVillager = (Villager) entity.getWorld().spawnEntity(entity.getLocation(),
									EntityType.VILLAGER);
							afterVillager = beforeVillager;

							afterVillager.setProfession(Profession.NONE);
							beforeVillager.remove();
						}
					}
				}
			}
		}
	}

}
