package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItem;
import com.alcist.firehelper.BukkitFireListener;
import com.alcist.firehelper.Callback;
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

    public FirebaseItemAdapter(Firebase firebase) {
        this.firebase = firebase;
        this.itemsRef = firebase.child("/items");
        this.bukkitFireListener = new BukkitFireListener<>(Plugin.class);
    }

    @Override
    public void getItem(String itemUuid, Callback<CustomItem> callback) {
        Query query = itemsRef.child(itemUuid);
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItem.class, callback));
    }

    @Override
    public void getAllItems(Callback<HashMap> callback) {
        Query query = itemsRef;
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(HashMap.class, callback));
    }

    @Override
    public void saveItem(CustomItem itemStack) {
        Firebase newItem = itemsRef.push();
        newItem.setValue(itemStack);
    }

    @Override
    public void removeItem(String itemUuid) {

    }
}
