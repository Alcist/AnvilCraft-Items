package com.alcist.anvilcraft.items.commands;

import com.alcist.anvilcraft.items.Plugin;
import com.alcist.anvilcraft.items.models.CustomItemMeta;
import com.alcist.commandapi.CommandInfo;
import com.alcist.commandapi.SubCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Created by istar on 08/09/15.
 */
@CommandInfo(name = "create",
        shortInv = "cr",
        longInv = "create",
        usage = "/it crate",
        desc = "Create new Custom Items",
        permission = "anvilcraft.items.create")
public class CreateItemCommand extends SubCommand {

    Plugin plugin;

    public CreateItemCommand() {
        super();
        this.plugin = JavaPlugin.getPlugin(Plugin.class);
    }

    @Override
    public boolean execute(CommandSender sender, CommandLine commandLine, String... args) {
        if(sender instanceof Conversable) {
            Conversable s = (Conversable) sender;
            new ConversationFactory(plugin)
                    .withFirstPrompt(new PromptName(new PromptMaterial(new PromptFinal())))
                    .withLocalEcho(false)
                    .withConversationCanceller(new ConversationQuit())
                    .addConversationAbandonedListener((conversation) -> {
                        if (conversation.gracefulExit()) {
                            Map<Object, Object> session = conversation.getContext().getAllSessionData();
                            CustomItemMeta meta = new CustomItemMeta((Map)session);
                            plugin.getItemData().saveItem(meta);
                            sender.sendMessage("Item created");
                        }
                    })
                    .buildConversation(s)
                    .begin();
        }

        return true;
    }

    private class ConversationQuit implements ConversationCanceller {

        @Override
        public void setConversation(Conversation conversation) {

        }

        @Override
        public boolean cancelBasedOnInput(ConversationContext context, String input) {
            return input.equals("quit") || input.equals("exit") || input.equals("cancel");
        }

        @Override
        public ConversationCanceller clone() {
            return this;
        }
    }

    private class PromptName extends StringPrompt {

        private Prompt next;

        public PromptName(Prompt next) {
            this.next = next;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            return "What's the name of your Item?";
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            context.setSessionData("name", input);
            return next;
        }
    }

    private class PromptMaterial extends StringPrompt {

        Prompt next;

        public PromptMaterial(Prompt next) {
            this.next = next;
        }

        @Override
        public String getPromptText(ConversationContext context) {
            return "What's the material of your item?";
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {

            Material material = Material.matchMaterial(input);
            if(material != null) {
                context.setSessionData("material", material);
                return next;
            }
            else {
                context.getForWhom().sendRawMessage("Could not find the material");
                return this;
            }

        }
    }

    private class PromptFinal extends BooleanPrompt {

        public PromptFinal() {
        }

        @Override
        public String getPromptText(ConversationContext context) {
            context.getForWhom().sendRawMessage(context.getAllSessionData().toString());
            return "Is this ok?";
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
            if(input) {
                context.getForWhom().sendRawMessage("Creation cancelled");
                return END_OF_CONVERSATION;
            }
            else {
                context.getForWhom().sendRawMessage("Write \"quit\" to exit.");
                return new PromptFinal();
            }
        }
    }



    @Override
    public Options getOptions() {
        return new Options();
    }
}
