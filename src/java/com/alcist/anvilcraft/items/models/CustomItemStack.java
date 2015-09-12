package com.alcist.anvilcraft.items.models;

import com.alcist.anvilcraft.items.AnvilCraftItems;
import com.alcist.anvilcraft.items.HiddenStringUtils;
import com.alcist.anvilcraft.items.NBTUtils;
import com.alcist.anvilcraft.items.Plugin;
import com.alcist.firehelper.Callback;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import static com.alcist.anvilcraft.items.models.CustomItemMeta.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by istar on 09/09/15.
 */
public class CustomItemStack extends CustomMap implements ICustomItemStack {

    public static final String META = "meta";
    public static final String AMOUNT = "amount";
    public static final String DAMAGE = "damage";
    public static final String DEATHS = "deaths";

    public static final String NBT_ID = "anvilcraft_items";

    private String id = null;

    public CustomItemStack(){
        put(META, new HashMap<>());
        put(AMOUNT, 1);
    }
    public CustomItemStack(Map<Object, Object> map) {
        this();
        putAll(map);
    }

    public CustomItemStack(ICustomItemMeta meta) {
        this();
        put(META, meta);
    }

    @JsonIgnore
    public ItemStack toItemStack() {
        CustomItemMeta meta = new CustomItemMeta(iGet(META));
        ItemStack itemStack = new ItemStack(Material.valueOf(meta.iGet(MATERIAL)));
        itemStack.setDurability((short)(int) meta.iGet(TYPE));
        itemStack.setAmount(iGet(AMOUNT));

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(meta.iGet(NAME));
        itemMeta.setLore(getLore());

        itemStack.setItemMeta(itemMeta);
        itemStack = NBTUtils.setString(itemStack, NBT_ID, id);
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
    public static void getFromItemStack(ItemStack itemStack, Callback<ICustomItemStack> cb) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta.hasLore() && HiddenStringUtils.hasHiddenString(meta.getLore().get(0))) {
            String id = HiddenStringUtils.extractHiddenString(meta.getLore().get(0));
            Plugin plugin = JavaPlugin.getPlugin(Plugin.class);
            plugin.getItemStackAdapter().getItem(id, cb);
        }
        else {
            throw new IllegalArgumentException("The item provided is not a custom item");
        }
    }

    @JsonIgnore
    public static String getCustomIdFromStack(ItemStack itemStack) {
        String id =  NBTUtils.getString(itemStack, NBT_ID);
        id = id != null && id.isEmpty() ? null : id;
        return id;
    }

    @JsonIgnore
    private List<String> getLore() {

        List<String> lore = new ArrayList<>();

        if(iGet(DAMAGE) != null) {
            lore.add("Status: " + (getDamageStatus().doubleValue() * 100) + "%");
        }

        if(iGet(DEATHS) != null) {
            lore.add("Death counter: " + new Deaths(iGet(DEATHS)).sum());
        }

        return lore;
    }

    @JsonIgnore
    public CustomItemMeta getMeta() {
        return  iGet(META);
    }

    @JsonIgnore
    public Number getDamageStatus() {
        Number maxDamage = getMeta().iGet(MAX_DAMAGE);
        Number currDamage = iGet(DAMAGE);
        if(maxDamage != null && currDamage != null) {
            return currDamage.intValue() / maxDamage.intValue();
        }
        return null;
    }

    @JsonIgnore
    public void increaseDeaths(LivingEntity entity) {
        Deaths deaths = new Deaths(iGet(DEATHS));

        if(entity instanceof Player) {
            deaths.incPlayers();
        }
        else {
            deaths.incMobs();
        }

        put(DEATHS, deaths);
    }

    public static class Deaths extends CustomMap {

        public static final String PLAYERS_KILLED = "players";
        public static final String MOBS_KILLED = "mobs";

        public Deaths() {
            put(PLAYERS_KILLED, 0);
            put(MOBS_KILLED, 0);
        }

        public Deaths(Map<Object, Object> map) {
            this();
            if(map != null) {
                putAll(map);
            }
        }

        @JsonIgnore
        public int sum() {
            return playersKilled() + mobsKilled();
        }

        @JsonIgnore
        public int playersKilled() {
            return iGet(PLAYERS_KILLED);
        }

        @JsonIgnore
        public int mobsKilled() {
            return iGet(MOBS_KILLED);
        }

        @JsonIgnore
        public void incPlayers() {
            put(PLAYERS_KILLED, playersKilled() + 1);
        }

        @JsonIgnore
        public void incMobs() {
            put(MOBS_KILLED, mobsKilled() + 1);
        }
    }
}
