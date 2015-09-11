package com.alcist.anvilcraft.items.models;

import java.util.*;

/**
 * Created by Adrián on 04/09/2015.
 */
public class CustomItemMeta extends CustomMap {

    public static final String MATERIAL = "material";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String STACK_SIZE = "stack_size";
    public static final String MAX_DAMAGE = "max_damage";
    public static final String COUNT_DEATHS = "count_deaths";

    public CustomItemMeta() {}
    public CustomItemMeta(Map<Object, Object> map) {
        if(map != null) {
            putAll(map);
        }
    }

}
