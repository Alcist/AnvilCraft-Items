package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.ICustomItemMeta;
import com.alcist.anvilcraft.items.models.ICustomItemStack;

/**
 * Created by Adrián on 04/09/2015.
 */
public interface AnvilCraftItems {
    PersistenceAdapter<ICustomItemMeta> getItemMetaAdapter();
    PersistenceAdapter<ICustomItemStack> getItemStackAdapter();
}
