package org.spartandevs.autofarm.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.spartandevs.autofarm.AutoFarm;
import org.spartandevs.autofarm.util.Message;
import org.spartandevs.autofarm.util.Plant;

@CommandAlias("autofarm|af")
public class AutoFarmCommand extends BaseCommand {
    @Dependency
    private AutoFarm plugin;

    @HelpCommand
    public void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("reload|rl")
    @CommandPermission("autofarm.reload")
    @Description("Reloads the config file and other resources.")
    public void onReload(CommandSender sender) {
        plugin.getConfigManager().reload();
        Message.sendSuccess(sender, "Successfully reloaded the plugin.");
    }

    @Subcommand("enable")
    @Syntax("<plant>")
    @CommandPermission("autofarm.modify")
    @Description("Enables auto farming for a plant.")
    @CommandCompletion("@plants @nothing")
    public void onEnable(CommandSender sender, Plant plant) {
        plugin.getConfigManager().toggle(plant, true);
        Message.sendSuccess(sender, "Enabled auto farming for " + plant.getFormattedName() + ".");
    }

    @Subcommand("disable")
    @Syntax("<plant>")
    @CommandPermission("autofarm.modify")
    @Description("Disables auto farming for a plant.")
    @CommandCompletion("@plants @nothing")
    public void onDisable(CommandSender sender, Plant plant) {
        plugin.getConfigManager().toggle(plant, false);
        Message.sendSuccess(sender, "Disabled auto farming for " + plant.getFormattedName() + ".");
    }

    @Subcommand("plugin disable")
    @CommandPermission("autofarm.modify")
    @Description("Disables the plugin.")
    public void onDisablePlugin(CommandSender sender) {
        plugin.getConfigManager().togglePlugin(false);
        Message.sendSuccess(sender, "Disabled the plugin.");
    }

    @Subcommand("plugin enable")
    @CommandPermission("autofarm.modify")
    @Description("Enables the plugin.")
    public void onEnablePlugin(CommandSender sender) {
        plugin.getConfigManager().togglePlugin(true);
        Message.sendSuccess(sender, "Enabled the plugin.");
    }

    @CatchUnknown
    public void onUnknownCommand(CommandSender sender) {
        Message.sendError(sender, "Unknown command. Type /autofarm help for help.");
    }
}
