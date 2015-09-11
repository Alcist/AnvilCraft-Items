package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import com.alcist.firehelper.BukkitFireListener;
import com.alcist.firehelper.Callback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import static com.alcist.anvilcraft.items.models.CustomItemMeta.*;

/**
 * Created by Adrián on 04/09/2015.
 */
public class FirebaseItemAdapter implements ItemAdapter {

    private Firebase itemsRef;
    private Firebase stackRef;
    private BukkitFireListener bukkitFireListener;

    public FirebaseItemAdapter(Firebase firebase) {
        this.itemsRef = firebase.child("/items");
        this.stackRef = firebase.child("/stacks");
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
    public void saveItem(String id, CustomItemMeta item) {

    }

    @Override
    public void getItemByName(String name, Callback<CustomItemResponse> callback) {
        itemsRef.orderByChild(NAME)
                .equalTo(name)
                .addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemResponse.class, callback));
    }

    @Override
    public void removeItem(String itemUuid) {
        itemsRef.child(itemUuid).removeValue();
    }

    @Override
    public void getItemStack(String itemId, Callback<CustomItemStack> callback) {
        stackRef.child(itemId).addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemStack.class, item -> {
            CustomItemStack custom = (CustomItemStack) item;
            custom.setId(itemId);
            callback.onCallBack(custom);
        }));
    }

    @Override
    public String saveItemStack(CustomItemStack item) {
        Firebase newItem = stackRef.push();
        newItem.setValue(item);
        return newItem.getKey();
    }

    @Override
    public void saveItemStack(String id, CustomItemStack item) {
        stackRef.child(id).setValue(item);
    }

    @Override
    public void removeItemStack(String id) {
        stackRef.child(id).removeValue();
    }

}
