package io.github.spaicygaming.pickaxeeggs.listener;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
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

		for (String i : PickaxeEggs.getItems()) {
			if (hand != null && hand.getType() == Material.valueOf(i) && hand.hasItemMeta()
					&& hand.getItemMeta().getDisplayName().equals(main.iname) && hand.getItemMeta().hasLore()
					&& projectile.getType() == EntityType.SNOWBALL) {
				Block block = getHittenBlock(projectile);

				if (block.getType() != Material.BEDROCK) {
					new BlockBreakEvent(block, shooter);
					block.getLocation().getBlock().setType(Material.AIR);
				}
				e.getEntity().remove();
				// breakAnimation(block, block.getType(), block.getData());

			}
		}

	}

	/*public void runAutosmelt(Player p, ItemStack hand){
		boolean silktouch = (hand != null) && (hand.getEnchantments().containsKey(Enchantment.SILK_TOUCH));
		if (!silktouch)
	}*/
	
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

	/*
	 * private void breakAnimation(Block hittenBlock, Material oldType, byte
	 * data){ ParticleEffect.BLOCK_CRACK.display(new
	 * ParticleEffect.BlockData(oldType, data), 0.0F, 0.0F, 0.0F, 1.0F, 32,
	 * hittenBlock.getLocation(), 32.0D);
	 * hittenBlock.getWorld().playEffect(hittenBlock.getLocation(),
	 * Effect.STEP_SOUND, oldType.getId());
	 * 
	 * }
	 */
}
