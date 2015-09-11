package com.alcist.anvilcraft.items.listeners;

import com.alcist.anvilcraft.items.Plugin;
import com.alcist.anvilcraft.items.models.CustomItemMeta;
import static com.alcist.anvilcraft.items.models.CustomItemMeta.*;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import static com.alcist.anvilcraft.items.models.CustomItemStack.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by istar on 10/09/15.
 */
public class ItemControlListener implements Listener {

    Plugin plugin;

    public ItemControlListener() {
        plugin = JavaPlugin.getPlugin(Plugin.class);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onItemPick(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();
        String id = CustomItemStack.getCustomIdFromStack(item);
        if(id == null) {

            plugin.getItemData().getItem(item.getType().name(), meta -> {
                if (meta == null) {
                    meta = new CustomItemMeta();
                    meta.put(MATERIAL, item.getType().name());
                    if (item.getItemMeta() != null) {
                        meta.put(NAME, item.getItemMeta().getDisplayName());
                    }
                    plugin.getItemData().saveItem(item.getType().name(), meta);
                }
                CustomItemStack stack = new CustomItemStack(meta);
                stack.put(AMOUNT, item.getAmount());
                plugin.getItemData().saveItemStack(item.getType().name(), stack);
                player.getInventory().addItem(stack.toItemStack());
            });
        }
        else {
            plugin.getItemData().getItemStack(id, custom -> {
                player.getInventory().addItem(custom.toItemStack());
            });
        }

        event.getItem().remove();
        event.setCancelled(true);
    }

    @EventHandler
    public void onItem(InventoryPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Inventory inventory = event.getInventory();
        String id = CustomItemStack.getCustomIdFromStack(item);
        if(id != null) {
            event.setCancelled(true);
            event.getItem().remove();
            plugin.getItemData().getItemStack(id, custom -> {
                inventory.addItem(custom.toItemStack());
            });
        }
        //TODO Should we handle all minecraft items as custom items?
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();
        String id = CustomItemStack.getCustomIdFromStack(item);
        if(id != null) {
            JavaPlugin.getPlugin(Plugin.class).getItemData().removeItemStack(id);
        }
    }


    @EventHandler
    public void onItemEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Item) {
            ItemStack item = ((Item) event.getEntity()).getItemStack();
            String id = CustomItemStack.getCustomIdFromStack(item);
            if(id != null) {
                plugin.getItemData().removeItemStack(id);
            }
        }
    }

}
