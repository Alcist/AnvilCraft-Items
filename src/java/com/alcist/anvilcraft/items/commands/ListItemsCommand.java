package com.alcist.anvilcraft.items.commands;

import com.alcist.anvilcraft.items.Plugin;
import com.alcist.commandapi.CommandInfo;
import com.alcist.commandapi.SubCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by istar on 08/09/15.
 */
@CommandInfo(name = "list",
        shortInv = "ls",
        longInv = "list",
        usage = "/it ls [filters]",
        desc = "list custom items",
        permission = "anvilcraft.items.list")
public class ListItemsCommand extends SubCommand {

    Plugin plugin;

    public ListItemsCommand() {
        this.plugin = JavaPlugin.getPlugin(Plugin.class);
    }

    @Override
    public boolean execute(CommandSender sender, CommandLine commandLine, String... args) {
        plugin.getItemData().getAllItems(items -> {
            if(items != null) {
                items.forEach((key, item) -> {
                    sender.sendMessage(item.getName());
                });
            }
            else {
                sender.sendMessage("No items to show, create items first!");
            }
        });
        return true;
    }

    @Override
    public Options getOptions() {
        return new Options();
    }
}
