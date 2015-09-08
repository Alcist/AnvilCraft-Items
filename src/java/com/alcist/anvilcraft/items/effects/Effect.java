package com.alcist.anvilcraft.items.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Adrián on 05/09/2015.
 */
public interface Effect {

    void launchEffect(Player player);

}
