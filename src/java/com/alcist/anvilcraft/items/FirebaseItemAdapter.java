package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.firehelper.BukkitFireListener;
import com.alcist.firehelper.Callback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.HashMap;
import java.util.Map;

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
    public void getItem(String itemUuid, Callback<CustomItemMeta> callback) {
        Query query = itemsRef.child(itemUuid);
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemMeta.class, callback));
    }

    @Override
    public void getAllItems(Callback<CustomItemResponse> callback) {
        Query query = itemsRef;
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemResponse.class, callback));
    }

    @Override
    public String saveItem(CustomItemMeta itemStack) {
        Firebase newItem = itemsRef.push();
        newItem.setValue(itemStack);
        return newItem.getKey();
    }

    @Override
    public void getItemByName(String name, Callback<CustomItemResponse> callback) {
        itemsRef.orderByChild("name")
                .equalTo(name)
                .addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemResponse.class, callback));
    }

    @Override
    public void removeItem(String itemUuid) {
        itemsRef.child(itemUuid).removeValue();
    }

}
