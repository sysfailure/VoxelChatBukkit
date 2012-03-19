package com.thevoxelbox.voxelchatbukkit;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VoxelChatBukkit extends JavaPlugin {
    public static Server s;
    public static LinkedList<vPlayer> vPlayers = new LinkedList<vPlayer>();
    public static Listener listener;

    @Override
    public void onDisable() {
        for (vPlayer v : vPlayers)
            v.dump();
        
        vPlayers.clear();
    }

    @Override
    public void onEnable() {
        s = this.getServer();
        listener = new Listener(this);
        vPlayers.clear();
        
        Bukkit.getPluginManager().registerEvents(listener, this);
        
        Logger.getLogger("Minecraft").info("VoxelChatBukkit is enabled!");
    }
    
    public static vPlayer getVP(Player p) {
        ListIterator<vPlayer> it = vPlayers.listIterator();

        while (it.hasNext()) {
            vPlayer v = it.next();

            if (v != null && p != null && v.p.getName().equals(p.getName())) {
                return v;
            }
        }

        vPlayer v = new vPlayer(p);
        vPlayers.add(v);
        return v;
    }
}
