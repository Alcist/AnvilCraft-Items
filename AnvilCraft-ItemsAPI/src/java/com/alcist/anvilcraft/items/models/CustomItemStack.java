package com.alcist.anvilcraft.items.models;

import com.alcist.anvilcraft.items.AnvilCraftItems;
import com.alcist.anvilcraft.items.HiddenStringUtils;
import com.alcist.firehelper.Callback;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.alcist.anvilcraft.items.models.CustomItemMeta.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by istar on 09/09/15.
 */
public class CustomItemStack extends CustomMap {

    public static final String META = "meta";
    public static final String AMOUNT = "amount";

    private String id = null;

    public CustomItemStack(){
        put(META, new HashMap<>());
        put(AMOUNT, 1);
    }
    public CustomItemStack(HashMap<Object, Object> map) {
        this();
        putAll(map);
    }

    public CustomItemStack(CustomItemMeta meta) {
        this();
        put(META, meta);
    }

    @JsonIgnore
    public ItemStack toItemStack() {
        CustomItemMeta meta = new CustomItemMeta(iGet(META));
        ItemStack itemStack = new ItemStack(Material.valueOf(meta.iGet(MATERIAL)));
        itemStack.setAmount(iGet(AMOUNT));

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(meta.iGet(NAME));
        itemMeta.setLore(getLore());

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @JsonIgnore
    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getId() {
        return this.id;
    }

    @JsonIgnore
    public static void getFromItemStack(ItemStack itemStack, Callback<CustomItemStack> cb) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        if(meta.hasLore() && HiddenStringUtils.hasHiddenString(meta.getLore().get(0))) {
            String id = HiddenStringUtils.extractHiddenString(meta.getLore().get(0));
            AnvilCraftItems plugin = (AnvilCraftItems) Bukkit.getPluginManager().getPlugin("AnvilCraft-Items");
            plugin.getItemData().getItemStack(id, item -> {
                item.setId(id);
                cb.onCallBack(item);
            });
        }
        else {
            throw new IllegalArgumentException("The item provided is not a custom item");
        }
    }

    @JsonIgnore
    public static String getCustomIdFromStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null && meta.hasLore() && HiddenStringUtils.hasHiddenString(meta.getLore().get(0))) {
            String id = HiddenStringUtils.extractHiddenString(meta.getLore().get(0));
            return id;
        }
        return null;
    }

    @JsonIgnore
    private List<String> getLore() {
        String encodedId = HiddenStringUtils.encodeString(id);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(encodedId);
        // TODO Add other data to lore.
        return lore;
    }
}
