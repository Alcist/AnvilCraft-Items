package com.alcist.anvilcraft.items.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * Created by Adrián on 04/09/2015.
 */
public class CustomItemMeta extends CustomMap {

    public static final String MATERIAL = "material";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String STACK_SIZE = "stack_size";

    public CustomItemMeta() {}
    public CustomItemMeta(Map<Object, Object> map) {
        putAll(map);
    }

}
