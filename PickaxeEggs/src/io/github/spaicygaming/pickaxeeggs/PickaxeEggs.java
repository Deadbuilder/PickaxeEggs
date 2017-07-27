package io.github.spaicygaming.pickaxeeggs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.spaicygaming.pickaxeeggs.listener.PlayerInteractListerner;
import io.github.spaicygaming.pickaxeeggs.listener.ProjectileHitListener;

public class PickaxeEggs extends JavaPlugin{
	
	private static PickaxeEggs instance;
	
	private static List<String> items;
	private List<String> lores = new ArrayList<>();
	public String iname = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Items.item-name"));
	public String confprojectilepath = getConfig().getString("Items.projectile");
	private String projectiletype;
	
	public void onEnable(){
		//if(!new AdvancedLicense("O6GH-A539-OVI6-ABP6", "https://licensesystem.000webhostapp.com/verify.php", this).register()) return;
		
		instance = this;
		saveDefaultConfig();
		items = getConfig().getStringList("Items.type");
		
		for (String i : getConfig().getStringList("Items.lores")){
			lores.add(ChatColor.translateAlternateColorCodes('&', i));
		}
		getServer().getPluginManager().registerEvents(new PlayerInteractListerner(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
		
		//System.out.println(getSeparators(60, '='));
		getLogger().info("PickaxeEggs has been Enabled!");
		System.out.println(getSeparators(60, '='));
		
		if (getConfigRadius() > 2){
			System.out.println(getSeparators(75, '*'));
			System.out.println("[PickaxeEggs] Invalid Radius, set a value from 1 to 2! Too high values will cause server lag!");
			System.out.println(getSeparators(75, '*'));
		}
		
		// set the projectile
		if (confprojectilepath.equalsIgnoreCase("snowball") || confprojectilepath.equalsIgnoreCase("egg")){
			projectiletype = confprojectilepath.toUpperCase();
		}
		else{
			getServer().getConsoleSender().sendMessage(ChatColor.RED + "ERROR! Invalid projectile! Insert 'snowball' or 'egg'.");
		}
		
	}
	
	public void onDisable(){
		getLogger().info("PickaxeEggs has been Disabled!");
	}

	public static PickaxeEggs getInstance(){
		return instance;
	}
	
	public List<String> getItems(){
		return items;
	}
	
	public List<String> getLores(){
		  return lores;
	}
	
	public int getConfigRadius(){
		return getConfig().getInt("Items.radius");
	}
	
	public String getProjectileType(){
		return projectiletype;
	}
	
	private String getSeparators(int value, char charValue){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value; i++){
			sb.append(charValue);
		}
		return sb.toString();
	}

}
