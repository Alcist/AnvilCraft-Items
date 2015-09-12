package com.alcist.anvilcraft.items.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

/**
 * Created by istar on 10/09/15.
 */
public abstract class CustomMap extends HashMap<Object, Object> {
    @JsonIgnore
    public <T> T iGet(Object key) {
        if(key.getClass().isEnum()) {
            key = ((Enum) key).name();
        }
        return (T) super.get(key);
    }

}
