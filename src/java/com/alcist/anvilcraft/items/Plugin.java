package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import com.alcist.firehelper.BukkitFireListener;
import com.alcist.firehelper.FireHelper;
import com.firebase.client.Firebase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
        new ItemsCommandHandler(this);
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
