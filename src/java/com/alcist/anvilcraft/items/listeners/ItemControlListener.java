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
        if(id == null ) {
            String metaId = item.getType().name() + ":" + item.getDurability();
            plugin.getItemMetaAdapter().getItem(metaId, meta -> {
                CustomItemMeta pMeta = (CustomItemMeta) meta;
                if(pMeta == null) {
                    CustomItemMeta cMeta = new CustomItemMeta();
                    cMeta.put(MATERIAL, item.getType().name());
                    cMeta.put(TYPE, (int) item.getDurability());
                    plugin.getItemMetaAdapter().saveItem(metaId, meta);
                    pMeta = cMeta;
                }

                CustomItemStack stack = new CustomItemStack(pMeta);
                stack.put(AMOUNT, item.getAmount());
                plugin.getItemStackAdapter().saveItem(stack);
                if(player.getInventory().contains(item.getType())) {
                    player.getInventory().getContents();
                }

                player.getInventory().addItem(stack.toItemStack());
                
            });
        }
        else {
            plugin.getItemStackAdapter().getItem(id, custom -> {
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
            plugin.getItemStackAdapter().getItem(id, custom -> {
                inventory.addItem(custom.toItemStack());
            });
        }
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();
        String id = CustomItemStack.getCustomIdFromStack(item);
        if(id != null) {
            JavaPlugin.getPlugin(Plugin.class).getItemMetaAdapter().removeItem(id);
        }
    }


    @EventHandler
    public void onItemEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Item) {
            ItemStack item = ((Item) event.getEntity()).getItemStack();
            String id = CustomItemStack.getCustomIdFromStack(item);
            if(id != null) {
                plugin.getItemStackAdapter().removeItem(id);
            }
        }
    }

}
