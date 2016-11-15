package de.ghac.blockversion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerLoginStartEvent;

public class BlockVersion extends JavaPlugin implements Listener {
	
String mode = "whitelist";
String kickmessage = "&4The version you used is not supported on this server.";
List<String> versions = new ArrayList<String>();
String fallbackserver = "";

@Override
public void onEnable(){
	getServer().getPluginManager().registerEvents(this, this);
	
	versions.add(ProtocolVersion.MINECRAFT_1_8.toString());
	getConfig().addDefault("mode", mode);
	getConfig().addDefault("kickmessage", kickmessage);
	getConfig().addDefault("versions", versions);
	
    getConfig().options().copyDefaults(true);
    this.saveConfig();
    
    mode = getConfig().getString("mode");
    kickmessage = getConfig().getString("kickmessage");
    versions = getConfig().getStringList("versions");   
    
}

@EventHandler
public void onPlayerJoin(final PlayerLoginStartEvent e){
    if(Bukkit.getPlayer(e.getForcedUUID()).hasPermission("blockversion.bypass"))
    	return;
    
    
	if(mode.equalsIgnoreCase("whitelist")){
		if(!versions.contains(ProtocolSupportAPI.getProtocolVersion(e.getAddress()).toString())){
			e.denyLogin(ChatColor.translateAlternateColorCodes('&', kickmessage));
			return;
		}
		
	}else{
		if(versions.contains(ProtocolSupportAPI.getProtocolVersion(e.getAddress()).toString())){
			e.denyLogin(ChatColor.translateAlternateColorCodes('&', kickmessage));
			return;
		}
	}
}

}
