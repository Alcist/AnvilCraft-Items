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

    String saveItem(CustomItemMeta itemStack);

    void removeItem(String itemUuid);

    class CustomItemResponse extends HashMap<String, CustomItemMeta> {}

}
