package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    public CustomItemFactory withLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public CustomItemFactory withUUID(String uuid) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getLore() != null) {
            List<String> currentLore = meta.getLore();
            List<String> loreWithId = new ArrayList<>();
            loreWithId.add(HiddenStringUtils.encodeString(uuid));
            loreWithId.addAll(currentLore);
            meta.setLore(loreWithId);
        } else {
            List<String> lore = new ArrayList<>();
            lore.add(HiddenStringUtils.encodeString(uuid));
            meta.setLore(lore);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }

    public static ItemStack toItemStack(CustomItem customItem) {
        return new CustomItemFactory(Material.getMaterial(customItem.material))
                .withName(customItem.name)
                .withLore(customItem.lore)
                .withUUID(customItem.uuid)
                .build();
    }

    public static CustomItem toCustomItem(ItemStack itemStack) {
        CustomItem customItem = new CustomItem();
        customItem.material = itemStack.getType().getId();
        customItem.name = itemStack.getItemMeta().getDisplayName();
        customItem.lore = itemStack.getItemMeta().getLore();
        return customItem;
    }

    public static String getUuid(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta.getLore() != null) {
            if(HiddenStringUtils.hasHiddenString(meta.getLore().get(0))) {
                return HiddenStringUtils.extractHiddenString(meta.getLore().get(0));
            }
        }
        return null;
    }

}
