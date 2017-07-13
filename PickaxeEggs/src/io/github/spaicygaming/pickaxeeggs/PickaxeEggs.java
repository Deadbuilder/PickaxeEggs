package io.github.spaicygaming.pickaxeeggs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.spaicygaming.pickaxeeggs.listener.PlayerInteractListerner;
import io.github.spaicygaming.pickaxeeggs.listener.ProjectileHitListener;

public class PickaxeEggs extends JavaPlugin{
	
	private static PickaxeEggs instance;
	
	private static List<String> items;
	public String iname = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Items.item-name"));
	
	public void onEnable(){
		instance = this;
		saveDefaultConfig();
		items = getConfig().getStringList("Items.type");
		getServer().getPluginManager().registerEvents(new PlayerInteractListerner(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
		getLogger().info("PickaxeEggs has been Enabled!");
	}
	
	public void onDisable(){
		getLogger().info("PickaxeEggs has been Disabled!");
	}

	public static PickaxeEggs getInstance(){
		return instance;
	}
	
	public static List<String> getItems(){
	  return items;
	}
	

}
