package com.alcist.anvilcraft.items.adapters;

import com.alcist.anvilcraft.items.ItemMetaAdapter;
import com.alcist.anvilcraft.items.PersistenceAdapter;
import com.alcist.anvilcraft.items.Plugin;
import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import com.alcist.anvilcraft.items.models.ICustomItemMeta;
import com.alcist.anvilcraft.items.models.ICustomItemStack;
import com.alcist.firehelper.BukkitFireListener;
import com.alcist.firehelper.Callback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import static com.alcist.anvilcraft.items.models.CustomItemMeta.*;

/**
 * Created by Adrián on 04/09/2015.
 */
public class FirebaseItemMetaAdapter implements PersistenceAdapter<ICustomItemMeta> {

    private Firebase itemsRef;
    private Firebase stackRef;
    private BukkitFireListener bukkitFireListener;

    public FirebaseItemMetaAdapter(Firebase firebase) {
        this.itemsRef = firebase.child("/items");
        this.stackRef = firebase.child("/stacks");
        this.bukkitFireListener = new BukkitFireListener<>(Plugin.class);
    }

    @Override
    public void getItem(String itemUuid, Callback<ICustomItemMeta> callback) {
        Query query = itemsRef.child(itemUuid);
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemMeta.class, callback));
    }

    @Override
    public void getAllItems(Callback<CustomItemResponse<ICustomItemMeta>> callback) {
        Query query = itemsRef;
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemMetaResponse.class, callback));
    }

    @Override
    public String saveItem(ICustomItemMeta item) {
        Firebase newItem = itemsRef.push();
        newItem.setValue(item);
        return newItem.getKey();
    }

    @Override
    public void saveItem(String id, ICustomItemMeta item) {
        itemsRef.child(id).setValue(item);
    }

    @Override
    public void getItemByName(String name, Callback<CustomItemResponse<ICustomItemMeta>> callback) {
        itemsRef.orderByChild(NAME)
                .equalTo(name)
                .addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemResponse.class, callback));
    }

    @Override
    public void removeItem(String itemUuid) {
        itemsRef.child(itemUuid).removeValue();
    }

    public abstract class CustomItemMetaResponse extends CustomItemResponse<CustomItemMeta> {}
}
