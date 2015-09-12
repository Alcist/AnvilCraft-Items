package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.adapters.FirebaseItemMetaAdapter;
import com.alcist.anvilcraft.items.adapters.FirebaseItemStackAdapter;
import com.alcist.anvilcraft.items.listeners.DeathCounterListener;
import com.alcist.anvilcraft.items.listeners.ItemControlListener;
import com.alcist.anvilcraft.items.models.ICustomItemMeta;
import com.alcist.anvilcraft.items.models.ICustomItemStack;
import com.alcist.firehelper.FireHelper;
import com.firebase.client.Firebase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by istar on 03/08/14.
 */
public class Plugin extends JavaPlugin implements AnvilCraftItems {

    private Firebase firebase;
    private FirebaseItemMetaAdapter firebaseItemMetaAdapter;
    private FirebaseItemStackAdapter firebaseItemStackAdapter;

    @Override
    public void onEnable() {
        super.onEnable();

        firebase = ((FireHelper) Bukkit.getPluginManager().getPlugin("FireHelper")).getFirebase();
        firebaseItemMetaAdapter = new FirebaseItemMetaAdapter(firebase);
        firebaseItemStackAdapter = new FirebaseItemStackAdapter(firebase);

        new ItemsCommandHandler(this);
        getServer().getPluginManager().registerEvents(new ItemControlListener(), this);
        getServer().getPluginManager().registerEvents(new DeathCounterListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public PersistenceAdapter<ICustomItemMeta> getItemMetaAdapter() {
        return firebaseItemMetaAdapter;
    }

    @Override
    public PersistenceAdapter<ICustomItemStack> getItemStackAdapter() {
        return firebaseItemStackAdapter;
    }
}
