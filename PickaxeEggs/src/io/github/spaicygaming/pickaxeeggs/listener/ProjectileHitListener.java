package io.github.spaicygaming.pickaxeeggs.listener;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

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
				Block hittenblock = getHittenBlock(projectile);

					for (Block blocks : getRadius(getHittenBlock(projectile).getLocation(), main.getConfigRadius())){
						// se è bedrock
						if (blocks.getType() == Material.BEDROCK)
							return;
						
						//remove blocks
						blocks.setType(Material.AIR);
						
						//registra evento per autostack, autopickup ecc. di prisonutils
						Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(hittenblock, shooter));
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
	
	  private static ArrayList<Block> getRadius(Location center, int radius)
	  {
	    Vector vec = new BlockVector(center.getBlockX(), center.getY(), center.getZ());
	    int x2 = center.getBlockX();
	    int y2 = center.getBlockY();
	    int z2 = center.getBlockZ();
	    
	    World world = center.getWorld();
	    
	    ArrayList<Block> blocks = new ArrayList<Block>();
	    for (int x = -radius; x <= radius; x++) {
	      for (int y = -radius; y <= radius; y++) {
	        for (int z = -radius; z <= radius; z++) {
	          if ((y + y2 >= 0) && (y + y2 <= 256))
	          {
	            Vector position = vec.clone().add(new Vector(x, y, z));
	            if (vec.distanceSquared(position) <= (radius + 0.5D) * (radius + 0.5D))
	            {
	              Block block = world.getBlockAt(x + x2, y + y2, z + z2);
	              blocks.add(block);
	            }
	          }
	        }
	      }
	    }
	    return blocks;
	  }
	
}
