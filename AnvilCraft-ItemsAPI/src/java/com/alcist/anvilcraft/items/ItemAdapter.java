package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import com.alcist.firehelper.Callback;

import java.util.HashMap;

/**
 * Created by Adrián on 04/09/2015.
 */
public interface ItemAdapter {

    void getItem(String itemUuid, Callback<CustomItemMeta> callback);

    void getAllItems(Callback<CustomItemResponse> callback);

    String saveItem(CustomItemMeta item);

    void saveItem(String id, CustomItemMeta item);

    void getItemByName(String name, Callback<CustomItemResponse> callback);

    void removeItem(String itemUuid);

    class CustomItemResponse extends HashMap<String, CustomItemMeta> {}

    void getItemStack(String itemId, Callback<CustomItemStack> callback);

    String saveItemStack(CustomItemStack item);

    void saveItemStack(String id, CustomItemStack item);
    void removeItemStack(String id);


}
