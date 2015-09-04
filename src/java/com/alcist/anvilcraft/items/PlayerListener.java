package com.alcist.anvilcraft.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Adrián on 03/09/2015.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        ItemStack item = new CustomItemFactory(Material.STICK)
                .withId(JavaPlugin.getPlugin(Plugin.class), 10)
                .withName("Varita del Principiante")
                .withLore("Algo late muy adentro")
                .build();


        System.out.println("Id antes de añadirlo: ");

        Player player = event.getPlayer();
        player.getInventory().clear();
        player.getInventory().addItem(item);

        ItemStack customItem = (ItemStack) player.getInventory().getItemInHand();

        System.out.println("Id del objeto dentro del inventario: " + customItem.getItemMeta().serialize());
    }

}
