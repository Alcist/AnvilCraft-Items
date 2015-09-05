package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItem;
import com.alcist.firehelper.Callback;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Adrián on 03/09/2015.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(final PlayerJoinEvent event) {
        ItemStack item = new CustomItemFactory(Material.STICK)
                .withName("Varita del Principiante")
                .withLore("Algo late muy adentro")
                .build();

        System.out.println("Id antes de añadirlo: " + CustomItemFactory.getUuid(item));

        Plugin plugin = JavaPlugin.getPlugin(Plugin.class);
        plugin.getItemData().saveItem(CustomItemFactory.toCustomItem(item));

        ItemStack item2 = new CustomItemFactory(Material.STICK)
                .withName("Varita Avanzada")
                .withLore("Su poder aumenta con la noche")
                .build();
        plugin.getItemData().saveItem(CustomItemFactory.toCustomItem(item2));

        plugin.getItemData().getAllItems(response -> {
            Player player = event.getPlayer();
            player.getInventory().clear();
            Set keys = response.keySet();
            ObjectMapper objectMapper = new ObjectMapper();
            for(Object key : keys) {
                System.out.println(key.toString());
                System.out.println(response.get(key));
                CustomItem customItem = objectMapper.convertValue(response.get(key), new TypeReference<CustomItem>(){});
                customItem.uuid = key.toString();
                player.getInventory().addItem(CustomItemFactory.toItemStack(customItem));
            }
        });

    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        System.out.println(CustomItemFactory.getUuid(event.getItem().getItemStack()));
    }

}
