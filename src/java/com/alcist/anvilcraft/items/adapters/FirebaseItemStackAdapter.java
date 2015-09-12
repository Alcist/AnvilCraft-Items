package com.alcist.anvilcraft.items.adapters;

import com.alcist.anvilcraft.items.PersistenceAdapter;
import com.alcist.anvilcraft.items.Plugin;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import com.alcist.anvilcraft.items.models.ICustomItemStack;
import com.alcist.firehelper.BukkitFireListener;
import com.alcist.firehelper.Callback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import static com.alcist.anvilcraft.items.models.CustomItemMeta.NAME;

/**
 * Created by ruben on 9/12/15.
 */
public class FirebaseItemStackAdapter implements PersistenceAdapter<ICustomItemStack> {

    private Firebase itemsRef;
    private BukkitFireListener bukkitFireListener;

    public FirebaseItemStackAdapter(Firebase firebase) {
        this.itemsRef = firebase.child("/stacks");
        this.bukkitFireListener = new BukkitFireListener<>(Plugin.class);
    }

    @Override
    public void getItem(String itemUuid, Callback<ICustomItemStack> callback) {
        Query query = itemsRef.child(itemUuid);
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemStack.class, item -> {
            CustomItemStack custom = ((CustomItemStack) item);
            custom.setId(itemUuid);
            callback.onCallBack(custom);
        }));
    }

    @Override
    public void getAllItems(Callback<CustomItemResponse<ICustomItemStack>> callback) {
        Query query = itemsRef;
        query.addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemStackResponse.class, callback));
    }

    @Override
    public String saveItem(ICustomItemStack item) {
        Firebase newItem = itemsRef.push();
        newItem.setValue(item);
        item.setId(newItem.getKey());
        return newItem.getKey();
    }

    @Override
    public void saveItem(String id, ICustomItemStack item) {
        itemsRef.child(id).setValue(item);
    }

    @Override
    public void getItemByName(String name, Callback<CustomItemResponse<ICustomItemStack>> callback) {
        itemsRef.orderByChild(NAME)
                .equalTo(name)
                .addListenerForSingleValueEvent(bukkitFireListener.listen(CustomItemResponse.class, callback));
    }

    @Override
    public void removeItem(String itemUuid) {
        itemsRef.child(itemUuid).removeValue();
    }

    public abstract class CustomItemStackResponse extends CustomItemResponse<CustomItemStack> {}
}
