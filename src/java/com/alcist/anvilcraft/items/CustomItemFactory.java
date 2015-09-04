package com.alcist.anvilcraft.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by Adrián on 03/09/2015.
 */
public class CustomItemFactory {

    private ItemStack item;

    public CustomItemFactory(Material material) {
        this.item = new ItemStack(material);
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
