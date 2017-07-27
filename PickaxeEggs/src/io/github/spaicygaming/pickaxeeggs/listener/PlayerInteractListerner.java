package io.github.spaicygaming.pickaxeeggs.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import io.github.spaicygaming.pickaxeeggs.PickaxeEggs;

public class PlayerInteractListerner implements Listener{

	private PickaxeEggs main = PickaxeEggs.getInstance();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();

		for (String i : main.getItems()) {
			ItemStack hand = p.getInventory().getItemInMainHand();

			if (hand.getType() == Material.valueOf(i) 
					&& hand.hasItemMeta()
					&& hand.getItemMeta().getDisplayName().contains(main.iname) 
					&& hand.getItemMeta().hasLore()
					&& hand.getItemMeta().getLore().equals(main.getLores())) {

				Action a = e.getAction();

				if (a == Action.RIGHT_CLICK_BLOCK || a == Action.RIGHT_CLICK_AIR) {
					String proj = main.getProjectileType();
					if (proj.equalsIgnoreCase("snowball")){
						p.launchProjectile(Snowball.class);
					}
					else if (proj.equalsIgnoreCase("egg")){
						p.launchProjectile(Egg.class);
					}
					else{
						main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "ERROR! Invalid projectile! Insert 'snowball' or 'egg'.");
					}
					
					// p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(),
					// p.getLocation().getYaw(), p.getLocation().getPitch());
					break;
				}

			}

		}
	}

}
