package com.norulesmc.dupe;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DupePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DupePlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DupePlugin disabled!");
    }

    @EventHandler
    public void onFurnaceBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.FURNACE) return;
        if (!(block.getState() instanceof TileState state)) return;
        if (!(state instanceof Furnace furnace)) return;

        ItemStack item = furnace.getInventory().getSmelting();

        if (item != null && item.getType().toString().endsWith("_SHULKER_BOX")) {
            furnace.getInventory().setSmelting(null);

            ItemStack duped = item.clone();
            duped.setAmount(2);

            block.getWorld().dropItemNaturally(block.getLocation(), duped);
        }
    }
}