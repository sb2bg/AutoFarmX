package org.spartandevs.autofarm.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.spartandevs.autofarm.AutoFarm;

public class DispenserListener implements Listener {
    private final AutoFarm plugin;

    public DispenserListener(AutoFarm plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onDispense(BlockDispenseEvent event) {

    }
}
