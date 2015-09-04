package com.alcist.anvilcraft.items;


import com.alcist.firehelper.FireHelper;
import com.firebase.client.Firebase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by istar on 03/08/14.
 */
public class Plugin extends JavaPlugin {


    @Override
    public void onEnable() {
        super.onEnable();
        FireHelper fireHelper = (FireHelper) Bukkit.getPluginManager().getPlugin("FireHelper");

        Firebase firebase = fireHelper.getFirebase();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
