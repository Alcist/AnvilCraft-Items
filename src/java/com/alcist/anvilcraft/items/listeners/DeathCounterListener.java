package com.alcist.anvilcraft.items.listeners;

import com.alcist.anvilcraft.items.Plugin;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by istar on 11/09/15.
 */
public class DeathCounterListener implements Listener {

    Plugin plugin;

    public DeathCounterListener() {
        plugin = JavaPlugin.getPlugin(Plugin.class);
    }

    @EventHandler
    public void onPlayerKill(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
            double remainHealth = ((LivingEntity) e.getEntity()).getHealth() - e.getFinalDamage();
            if(remainHealth <= 0) {
                Player killer = (Player) e.getDamager();
                LivingEntity dead = (LivingEntity) e.getEntity();
                ItemStack weapon = killer.getItemInHand();
                String weaponId = CustomItemStack.getCustomIdFromStack(weapon);

                if( weaponId != null) {
                    plugin.getItemData().getItemStack(weaponId, custom -> {
                        custom.increaseDeaths(dead);
                        plugin.getItemData().saveItemStack(weaponId, custom);
                        weapon.setItemMeta(custom.toItemStack().getItemMeta());
                    });
                }
            }
        }
    }
}
