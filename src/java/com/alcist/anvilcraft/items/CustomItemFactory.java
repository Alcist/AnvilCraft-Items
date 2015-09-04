package com.alcist.anvilcraft.items;

import com.firebase.client.Firebase;
import org.apache.logging.log4j.core.helpers.UUIDUtil;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Adrián on 03/09/2015.
 */
public class CustomItemFactory {

    private ItemStack item;

    public CustomItemFactory(Material material) {
        this.item = new ItemStack(material);
    }

    public CustomItemFactory withId(int id) {
        ItemMeta meta = this.item.getItemMeta();
        return this;
    }

    public CustomItemFactory withName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public CustomItemFactory withLore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }



}
