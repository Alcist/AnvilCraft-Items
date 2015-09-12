package com.alcist.anvilcraft.items.commands;

import com.alcist.anvilcraft.items.PersistenceAdapter;
import com.alcist.anvilcraft.items.Plugin;
import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.anvilcraft.items.models.CustomItemStack;
import com.alcist.commandapi.CommandInfo;
import static com.alcist.anvilcraft.items.models.CustomItemStack.*;
import static com.alcist.anvilcraft.items.models.CustomItemMeta.*;
import com.alcist.commandapi.SubCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Created by istar on 10/09/15.
 */
@CommandInfo(name = "give",
        shortInv = "gv",
        longInv = "give",
        usage = "give <Item Name> [-am <amount>]",
        desc = "Give a custom item to a user.",
        permission = "anvilcraft.items.give"
)
public class GiveItemCommand extends SubCommand {

    @Override
    public boolean execute(CommandSender sender, CommandLine commandLine, String... args) {

        StringBuilder name = new StringBuilder();
        commandLine.getArgList().forEach(str -> {
            name.append(str);
            if(!str.equals(commandLine.getArgList().get(commandLine.getArgList().size() -1))) {
                name.append(' ');
            }
        });

        JavaPlugin.getPlugin(Plugin.class).getItemMetaAdapter().getItemByName(name.toString(), bundle -> {

            if (bundle != null && bundle.size() > 0) {
                bundle.forEach((key, item) -> {
                    CustomItemStack custom = new CustomItemStack(item);

                    try {
                        if (commandLine.hasOption("amount")) {
                            Number size = (Number) ((HashMap) custom.get(META)).get(STACK_SIZE);
                            int amount = Integer.valueOf(commandLine.getOptionValue("amount"), 10);
                            if (size != null && size.intValue() - amount > 0) {
                                custom.put(AMOUNT, amount);
                            } else {
                                sender.sendMessage("The stack size for this item is limited.");
                                return;
                            }
                        }
                    } catch (Exception e) {
                        sender.sendMessage("Invalid value for amount");
                        return;
                    }

                    String id = JavaPlugin.getPlugin(Plugin.class).getItemStackAdapter().saveItem(custom);
                    custom.setId(id);

                    Player player = (Player) sender;
                    player.getInventory().addItem(custom.toItemStack());
                });
            }
            else {
                sender.sendMessage("There's not item with that name.");
            }

        });
        return true;
    }

    @Override
    public Options getOptions() {
        return new Options()
                .addOption(Option.builder("am")
                        .longOpt("amount")
                        .hasArg()
                        .argName("number")
                        .build());
    }
}
