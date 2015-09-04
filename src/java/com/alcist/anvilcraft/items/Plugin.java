package com.alcist.anvilcraft.items;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by istar on 03/08/14.
 */
public class Plugin extends JavaPlugin {


    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
