package io.github.spaicygaming.pickaxeeggs.listener;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import io.github.spaicygaming.pickaxeeggs.PickaxeEggs;

public class ProjectileHitListener implements Listener {

	private PickaxeEggs main = PickaxeEggs.getInstance();

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		Projectile projectile = e.getEntity();
		if (!(projectile.getShooter() instanceof Player) || !(projectile instanceof Snowball))
			return;

		Player shooter = (Player) projectile.getShooter();
		ItemStack hand = shooter.getInventory().getItemInMainHand();

		for (String i : main.getItems()) {
			if (hand != null && hand.getType() == Material.valueOf(i) && hand.hasItemMeta()
					&& hand.getItemMeta().getDisplayName().contains(main.iname) && hand.getItemMeta().hasLore()
					&& hand.getItemMeta().getLore().equals(main.getLores())
					&& projectile.getType() == EntityType.SNOWBALL) {
				Block block = getHittenBlock(projectile);

				if (block.getType() != Material.BEDROCK) {
					//registra evento per autostack, autopickup ecc. di prisonutils
					Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(block, shooter));
					
					//remove block
					block.getLocation().getBlock().setType(Material.AIR);
				}
				e.getEntity().remove();

			}
		}

	}

	
	private Block getHittenBlock(Entity entity) {
		World world = entity.getWorld();
		BlockIterator iterator = new BlockIterator(world, entity.getLocation().toVector(),
				entity.getVelocity().normalize(), 0.0D, 4);
		Block block = null;

		while (iterator.hasNext()) {
			block = iterator.next();
			if (block.getType().isSolid()) {
				break;
			}
		}
		return block;
	}
	
}
