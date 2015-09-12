package com.alcist.anvilcraft.items;
import com.alcist.firehelper.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adrián on 04/09/2015.
 */
public interface PersistenceAdapter<T> {

    void getItem(String itemUuid, Callback<T> callback);

    void getAllItems(Callback<CustomItemResponse<T>> callback);

    String saveItem(T item);

    void saveItem(String id, T item);

    void getItemByName(String name, Callback<CustomItemResponse<T>> callback);

    void removeItem(String itemUuid);

    abstract class CustomItemResponse<T> implements Map<String, T> {}


}
