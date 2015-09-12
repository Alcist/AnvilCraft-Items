package com.alcist.anvilcraft.items.models;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by ruben on 9/12/15.
 */
public interface ICustomItemStack extends Map<Object, Object> {
    ItemStack toItemStack();
    void setId(String key);
    String getId();
}
