package com.alcist.anvilcraft.items;


import com.alcist.anvilcraft.items.effects.FireballEffect;
import com.alcist.anvilcraft.items.effects.LightningEffect;
import com.alcist.anvilcraft.items.effects.WitherSkullEffect;
import com.alcist.firehelper.FireHelper;
import com.firebase.client.Firebase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Created by istar on 03/08/14.
 */
public class Plugin extends JavaPlugin implements AnvilCraftItems {

    private Firebase firebase;
    private FirebaseItemAdapter firebaseItemAdapter;

    @Override
    public void onEnable() {
        super.onEnable();

        firebase = ((FireHelper) Bukkit.getPluginManager().getPlugin("FireHelper")).getFirebase();
        firebaseItemAdapter = new FirebaseItemAdapter(firebase);

        getServer().getPluginManager().registerEvents(new FireballEffect(), this);
        getServer().getPluginManager().registerEvents(new LightningEffect(), this);
        getServer().getPluginManager().registerEvents(new WitherSkullEffect(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public ItemAdapter getItemData() {
        return firebaseItemAdapter;
    }
}
