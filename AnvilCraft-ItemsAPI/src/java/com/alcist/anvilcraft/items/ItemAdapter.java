package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItem;
import com.alcist.firehelper.Callback;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Adrián on 04/09/2015.
 */
public interface ItemAdapter {

    void getItem(String itemUuid, Callback<CustomItem> callback);

    void getAllItems(Callback<HashMap> callback);

    void saveItem(CustomItem itemStack);

    void removeItem(String itemUuid);

}
