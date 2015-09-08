package com.alcist.anvilcraft.items.effects;

import com.alcist.anvilcraft.items.CustomItemFactory;
import com.alcist.anvilcraft.items.FirebaseItemAdapter;
import com.alcist.anvilcraft.items.Plugin;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Adrián on 06/09/2015.
 */
public class LightningEffect implements Listener, Effect {

    public static final String effectName = Effects.LIGHTNING.name;

    @Override
    public void launchEffect(Player player) {
        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setMetadata("lightning", new FixedMetadataValue(JavaPlugin.getPlugin(Plugin.class), true));
    }

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

    @EventHandler
    public void onArrowHitsPlayer(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Arrow) {
            if(event.getDamager().getMetadata("lightning") != null) {
                if(event.getDamager().getMetadata("lightning").get(0).asBoolean()) {
                    event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
                }
            }
        }
    }

}
