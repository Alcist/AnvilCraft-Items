package com.alcist.anvilcraft.items.models;

import com.alcist.anvilcraft.items.AnvilCraftItems;
import com.alcist.anvilcraft.items.HiddenStringUtils;
import com.alcist.firehelper.Callback;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by Adrián on 04/09/2015.
 */
public class CustomItemMeta extends HashMap<String, Object> {

    public CustomItemMeta() {}

    public CustomItemMeta(Map<String, Object> map) {
        putAll(map);
    }

    public static void getCustomItem(ItemMeta meta, Callback<CustomItemMeta> cb) {
        if(meta.hasLore() && HiddenStringUtils.hasHiddenString(meta.getLore().get(0))) {
            String id = HiddenStringUtils.extractHiddenString(meta.getLore().get(0));
            AnvilCraftItems plugin = (AnvilCraftItems)Bukkit.getPluginManager().getPlugin("AnvilCraft-Items");
            plugin.getItemData().getItem(id, cb);
        }
        else {
            throw new IllegalArgumentException("The item provided is not a custom item");
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = (Map<String, Object>) this.clone();
        if(result.get("lore") != null) {
            List<String> lore = new ArrayList((List<String>) result.get("lore"));
            lore.remove(0);
            result.put("lore", lore);

        }
        return result;
    }

    public Material getMaterial() {
        return Material.getMaterial((String)get("material"));
    }

    public boolean setMaterial(String material) {
        Material rM = Material.matchMaterial(material);
        boolean valid = rM != null;
        if(valid) {
            put("material", rM.name());
        }
        return valid;
    }

    public String getName() {
        Object name = get("name");
        return name != null ? (String) name : null ;
    }

    public void setName(String name) {
        put("name", name);
    }

    public void setMaterial(Material material) {
        put("material", material.name());
    }

    public List<String> getLore(String id) {
        String encodedId = HiddenStringUtils.encodeString(id);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(encodedId);
        // TODO Add other data to lore.
        return lore;
    }
}
