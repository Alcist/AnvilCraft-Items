package com.alcist.anvilcraft.items.effects;

import com.alcist.anvilcraft.items.CustomItemFactory;
import com.alcist.anvilcraft.items.FirebaseItemAdapter;
import com.alcist.anvilcraft.items.Plugin;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Adrián on 06/09/2015.
 */
public class WitherSkullEffect implements Listener, Effect {

    public static final String effectName = Effects.WITHERSKULL.name;

    @Override
    public void launchEffect(Player player) {
        WitherSkull witherSkull = player.launchProjectile(WitherSkull.class);
        witherSkull.setMetadata("witherskull", new FixedMetadataValue(JavaPlugin.getPlugin(Plugin.class), true));
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
        if(event.getDamager() instanceof WitherSkull) {
            if(event.getDamager().getMetadata("witherskull") != null) {
                if(event.getDamager().getMetadata("witherskull").get(0).asBoolean()) {
                   if(event.getEntity() instanceof LivingEntity) {
                       LivingEntity entity = (LivingEntity) event.getEntity();
                       entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5000, 1));
                   }
                }
            }
        }
    }

}
