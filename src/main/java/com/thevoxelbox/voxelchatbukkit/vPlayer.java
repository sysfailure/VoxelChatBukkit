package com.thevoxelbox.voxelchatbukkit;

import org.bukkit.entity.Player;


public class vPlayer { // Storage for iSay to remove need of multiple file calls
    public Player p;
    
    protected Boolean voxelplayer = false;
    
    vPlayer(Player player) {
        p = player;
        
        this.load();
    }
    
    public void load() {
        // Yay nothing
    }
    
    public void setVoxelPlayerAvailability(Boolean bool) {
        voxelplayer = bool;
    }
    
    public boolean isVoxelPlayerInstalled() {
        return voxelplayer.booleanValue();
    }
    
    public void dump() {
        // Yay nothing
    }
}
