package listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class FishingListener implements Listener {

	@EventHandler
	public void onFishing(PlayerFishEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		ItemStack offHand = inventory.getItemInOffHand();

		if (offHand.getType() == Material.WATER_BUCKET) {
			State state = event.getState();

			if (state == State.CAUGHT_FISH) {
				Entity entity = event.getCaught();
				if (entity != null) {
					Item item = (Item) entity;
					ItemStack itemStack = item.getItemStack();
					Material type = itemStack.getType();

					if (type == Material.COD || type == Material.SALMON || type == Material.PUFFERFISH
							|| type == Material.TROPICAL_FISH) {
						ItemStack bucket = null;
						switch (type) {
						case COD:
							bucket = new ItemStack(Material.COD_BUCKET);
							break;
						case SALMON:
							bucket = new ItemStack(Material.SALMON_BUCKET);
							break;
						case PUFFERFISH:
							bucket = new ItemStack(Material.PUFFERFISH_BUCKET);
							break;
						case TROPICAL_FISH:
							bucket = new ItemStack(Material.TROPICAL_FISH_BUCKET);
							break;
						default:
							break;
						}

						item.setItemStack(bucket);
						inventory.setItemInOffHand(new ItemStack(Material.AIR));
					}

				}
			}

		}
	}

}
