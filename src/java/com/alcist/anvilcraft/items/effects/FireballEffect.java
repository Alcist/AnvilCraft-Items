package com.alcist.anvilcraft.items.effects;

import com.alcist.anvilcraft.items.CustomItemFactory;
import com.alcist.anvilcraft.items.FirebaseItemAdapter;
import com.alcist.anvilcraft.items.Plugin;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Adrián on 05/09/2015.
 */
public class FireballEffect implements Effect {

    public static final String effectName = "fireball";

    @Override
    public void launchEffect(Player player) {
        player.launchProjectile(Fireball.class);
    }

    @Override
    @EventHandler
    public void onEffect(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInHand();
            if(CustomItemFactory.isCustomItem(itemStack)) {
                Plugin plugin = JavaPlugin.getPlugin(Plugin.class);
                FirebaseItemAdapter firebaseItemAdapter = (FirebaseItemAdapter) plugin.getItemData();
                firebaseItemAdapter.getItem(CustomItemFactory.getUuid(itemStack), response -> {
                    if(response.effects.contains(effectName)) {
                        launchEffect(player);
                    }
                });
            }
        }
    }

}
