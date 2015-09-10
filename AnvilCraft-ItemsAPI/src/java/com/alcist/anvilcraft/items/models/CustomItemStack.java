package com.alcist.anvilcraft.items.models;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by istar on 09/09/15.
 */
public class CustomItemStack extends ItemStack {

    private CustomItemMeta meta = new CustomItemMeta();

    public boolean setCustomItemMeta(String id, CustomItemMeta meta) {
        this.meta = meta;
        setType(meta.getMaterial());
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(meta.getName());
        itemMeta.setLore(meta.getLore(id));

        return super.setItemMeta(itemMeta);
    }

    @Override
    public boolean setItemMeta(ItemMeta itemMeta) {
        meta.putAll(itemMeta.serialize());
        return super.setItemMeta(itemMeta);
    }

    public CustomItemMeta getCustomItemMeta() {
        return meta;
    }


    @Override
    public void setType(Material type) {
        super.setType(type);
        meta.setMaterial(type);
    }

    @Override
    public Material getType() {
        return meta.getMaterial();
    }
}
