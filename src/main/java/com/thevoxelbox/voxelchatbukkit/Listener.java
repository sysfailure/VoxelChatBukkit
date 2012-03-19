package com.thevoxelbox.voxelchatbukkit;

import net.minecraft.server.Packet3Chat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {
    private VoxelChatBukkit plugin;
    
    public Listener(VoxelChatBukkit instance) {
        plugin = instance;
    }
    

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        // pull command out and reprocess with String.toLowerCase()
        String m = event.getMessage();
        String[] words = m.split("\\s", 2);
        String origcom;
        String newcom;

        origcom = words[0];
        newcom = origcom.toLowerCase();

        m = m.replace(origcom, newcom);
        
        if (m.startsWith("/@$&#_voxelplayer")) {
            vPlayer v = VoxelChatBukkit.getVP(event.getPlayer());
            
            v.setVoxelPlayerAvailability(!v.isVoxelPlayerInstalled());
            v.p.sendMessage(ChatColor.BLUE + "xTiming\'s VoxelPlayer server features have been " + ChatColor.GREEN + ((v.isVoxelPlayerInstalled()) ? "enabled" : "disabled"));
            
            event.setCancelled(true);
            return;
        }
        
        if (m.startsWith("/vpreset")) {
            vPlayer v = VoxelChatBukkit.getVP(event.getPlayer());
            v.setVoxelPlayerAvailability(!v.isVoxelPlayerInstalled());
            
            v.p.sendMessage(ChatColor.BLUE + "xTiming\'s VoxelPlayer server features have been " + ChatColor.GREEN + ((v.isVoxelPlayerInstalled()) ? "enabled" : "disabled"));
            
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(PlayerChatEvent event) {
        Player p = event.getPlayer();
        
        for (Player pl: VoxelChatBukkit.s.getOnlinePlayers()) {
            if (VoxelChatBukkit.getVP(pl).isVoxelPlayerInstalled()) {
                ((CraftPlayer) pl).getHandle().netServerHandler.networkManager.queue(new Packet3Chat("\u00A7b\u00A7d\u00A7c\u00A7b\u00A7d\u00A7cq?=$name=" + p.getName()));
                ((CraftPlayer) pl).getHandle().netServerHandler.networkManager.queue(new Packet3Chat("\u00A7b\u00A7d\u00A7c\u00A7b\u00A7d\u00A7cq?=$message=" + event.getMessage()));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        vPlayer v = new vPlayer(event.getPlayer());
        
        if (!VoxelChatBukkit.vPlayers.contains(v)) VoxelChatBukkit.vPlayers.add(v);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        vPlayer v = VoxelChatBukkit.getVP(event.getPlayer());
        
        if (VoxelChatBukkit.vPlayers.contains(v)) VoxelChatBukkit.vPlayers.remove(v);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event) {
        vPlayer v = VoxelChatBukkit.getVP(event.getPlayer());
        
        if (VoxelChatBukkit.vPlayers.contains(v)) VoxelChatBukkit.vPlayers.remove(v);
    }
}
