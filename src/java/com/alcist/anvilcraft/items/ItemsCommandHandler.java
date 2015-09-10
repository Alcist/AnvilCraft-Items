package com.alcist.anvilcraft.items;

import com.alcist.anvilcraft.items.commands.CreateItemCommand;
import com.alcist.anvilcraft.items.commands.ListItemsCommand;
import com.alcist.anvilcraft.items.commands.RemoveItemCommand;
import com.alcist.commandapi.CommandHandler;
import com.alcist.commandapi.CommandInfo;
import com.alcist.commandapi.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by istar on 08/09/15.
 */
@CommandInfo(
        name = "items",
        shortInv = "it",
        longInv = "items",
        usage = "/it <subcommand> [subcommand options]",
        desc = "Base command for AnvilCraft Items",
        permission = "anvilcraft.items")
public class ItemsCommandHandler extends CommandHandler {

    public ItemsCommandHandler(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public Class<? extends SubCommand>[] getCommands() {
        return new Class[]{
                ListItemsCommand.class,
                CreateItemCommand.class,
                RemoveItemCommand.class
        };
    }
}
