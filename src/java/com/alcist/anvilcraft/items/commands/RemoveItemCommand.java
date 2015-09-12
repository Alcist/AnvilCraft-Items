package com.alcist.anvilcraft.items.commands;

import com.alcist.anvilcraft.items.PersistenceAdapter;
import com.alcist.anvilcraft.items.Plugin;
import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.commandapi.CommandInfo;
import com.alcist.commandapi.SubCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created by istar on 10/09/15.
 */
@CommandInfo(name = "remove",
        shortInv = "rm",
        longInv = "remove",
        usage = "/it rm",
        desc = "Remove custom items from the item storage",
        permission = "anvilcraft.items.remove")
public class RemoveItemCommand extends SubCommand {

    Plugin plugin;

    public RemoveItemCommand() {
        plugin = JavaPlugin.getPlugin(Plugin.class);
    }

    @Override
    public boolean execute(CommandSender sender, CommandLine commandLine, String... args) {
        List<String> arg = commandLine.getArgList();
        if(arg.size() == 0) {
            sender.sendMessage("You have to specify the item name");
        }
        else {
            StringBuilder name = new StringBuilder();
            arg.forEach(str -> {
                name.append(str);
                if(!str.equals(arg.get(arg.size() -1))) {
                    name.append(' ');
                }
            });
            plugin.getItemMetaAdapter().getItemByName(name.toString(), (bundle) -> {
                if (bundle != null && bundle.size() > 0) {
                    bundle.forEach((key, item) -> {
                        plugin.getItemMetaAdapter().removeItem(key);
                    });
                    sender.sendMessage("The item has been removed.");
                }
                else {
                    sender.sendMessage("There's no item with that name.");
                }
            });
        }

        return true;
    }

    @Override
    public Options getOptions() {
        return new Options();
    }
}
