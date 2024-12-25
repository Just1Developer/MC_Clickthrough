package net.justonedev.mc.clickthrough;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.BlockInventoryHolder;

public class ChestEvents implements Listener {

    Clickthrough plugin;

    public ChestEvents(Clickthrough plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(final PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType() != EntityType.ITEM_FRAME && e.getRightClicked().getType() != EntityType.GLOW_ITEM_FRAME) return;
        if (e.getPlayer().isSneaking()) return;
        ItemFrame frame = (ItemFrame) e.getRightClicked();
        Block attached = frame.getLocation().getBlock().getRelative(frame.getAttachedFace());
        if (!(attached.getState() instanceof BlockInventoryHolder holder)) return;

        // Cancelling the event does not actually cancel the interaction
        // So, we freeze the frame for one (1) tick
        if (!frame.isFixed()) {
            frame.setFixed(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> frame.setFixed(false), 1);
        }

        e.setCancelled(true);
        e.getPlayer().openInventory(holder.getInventory());
    }

}
