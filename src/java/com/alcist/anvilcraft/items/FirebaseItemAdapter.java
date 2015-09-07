package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItem;
import com.alcist.firehelper.BukkitFireListener;
import com.alcist.firehelper.Callback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Adrián on 04/09/2015.
 */
public class FirebaseItemAdapter implements ItemAdapter {

    private Firebase firebase;
    private Firebase itemsRef;
    private BukkitFireListener bukkitFireListener;

    private org.bukkit.plugin.Plugin plugin;
    private HashMap<org.bukkit.plugin.Plugin, HashMap<String, Callback>> pluginPool = new HashMap<>();

    public FirebaseItemAdapter(Firebase firebase, org.bukkit.plugin.Plugin plugin) {
        this.firebase = firebase;
        this.itemsRef = firebase.child("/items");
        this.bukkitFireListener = new BukkitFireListener<>(Plugin.class);

        this.plugin = plugin;
        this.pluginPool.put(plugin, new HashMap<>());
    }

    @Override
    public void getItem(String itemUuid, Callback<CustomItem> callback) {
        Query query = itemsRef.child(itemUuid);
        if(pluginPool.get(plugin).get(itemUuid) == null) {
            pluginPool.get(plugin).put(itemUuid, callback);
        }
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItem.class, pluginPool.get(plugin).get(itemUuid)));
    }

    @Override
    public void getAllItems(Callback<HashMap<String, CustomItem>> callback) {
        MapType mapType = new ObjectMapper().getTypeFactory().constructMapType(HashMap.class, String.class, CustomItem.class);
        Query query = itemsRef;
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(mapType.getRawClass(), callback));
    }

    @Override
    public void saveItem(CustomItem itemStack) {
        Firebase newItem = itemsRef.push();
        newItem.setValue(itemStack);
    }

    @Override
    public void removeItem(String itemUuid) {
        itemsRef.child(itemUuid).removeValue();
    }

    @Override
    public void removeCallbacks(org.bukkit.plugin.Plugin plugin) {
        pluginPool.remove(plugin);
    }
}
