package listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.CarryOn;

public class CarryListener implements Listener {
	static Map<UUID,ItemStack[]> carryInventory = new HashMap<UUID, ItemStack[]>();
	
	void ejectPassenger(Player player, Entity entity) {

		CarryOn.instance.getLogger().log(Level.INFO, "Eject");
		player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 10, 29);

		if (entity instanceof FallingBlock) {
			Block playerFoot = player.getWorld().getBlockAt(player.getLocation());
			
			if (playerFoot.getType() == Material.AIR) {
				FallingBlock blockEntity = (FallingBlock) entity;
				playerFoot.setType(blockEntity.getMaterial());
				playerFoot.setBlockData(blockEntity.getBlockData());

				BlockState state = playerFoot.getState();
				Container container = (Container)state;
				container.getInventory().setContents(carryInventory.get(player.getUniqueId()));
				
				entity.remove();

			}
		}
	}
	

	@EventHandler
	public void onEjectRightClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();

		if (!player.isSneaking()) {
			if (player.eject()) {
				event.setCancelled(true);
				Entity entity = event.getRightClicked();
			
				ejectPassenger(player,entity);
			}
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		CarryOn.instance.getLogger().log(Level.INFO, "Leave");
		
		Player player = event.getPlayer();
		List<Entity> passengers = player.getPassengers();
		
		if(!passengers.isEmpty()) {
			Entity entity = passengers.get(0);
			
			ejectPassenger(player,entity);//TODO ÄÝ¾ÈµÊ
		}		
	}
	
	
	@EventHandler
	public void onCarryAnimal(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();

		if (player.isSneaking()) {
			if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
				Entity entity = event.getRightClicked();

				if (entity instanceof LivingEntity || entity instanceof Vehicle) {
					List<Entity> passengers = player.getPassengers();

					if (passengers.isEmpty()) {
						event.setCancelled(true);
						player.addPassenger(entity);
					}
				}
			}
		} 
	}

	@EventHandler
	public void onCarryBlock(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();

			if (player.isSneaking()) {
				if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
					List<Entity> passengers = player.getPassengers();

					if (passengers.isEmpty()) {
						Block block = event.getClickedBlock();
						BlockState state = block.getState();
						
						if (state instanceof Container) {
							event.setCancelled(true);
							
							World world = player.getWorld();
							FallingBlock blockEntity = world.spawnFallingBlock(player.getLocation(), block.getBlockData());
							blockEntity.setGravity(false);
							player.addPassenger(blockEntity);
							
							Container container = (Container) state;
							Inventory inventory = container.getInventory();
							carryInventory.put(player.getUniqueId(), inventory.getContents());
							inventory.clear();

							block.setType(Material.AIR);
						}
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
