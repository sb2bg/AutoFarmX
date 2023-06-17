package org.spartandevs.autofarm;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.ConditionFailedException;
import org.bukkit.plugin.java.JavaPlugin;
import org.spartandevs.autofarm.commands.AutoFarmCommand;
import org.spartandevs.autofarm.config.ConfigManager;
import org.spartandevs.autofarm.listeners.DispenserListener;
import org.spartandevs.autofarm.util.Plant;

public class AutoFarm extends JavaPlugin {
    private final ConfigManager configManager = new ConfigManager(this);

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
    }

    @SuppressWarnings("deprecation") // Unstable help API
    private void registerCommands() {
        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.enableUnstableAPI("help");

        manager.getCommandCompletions().registerStaticCompletion("plants", Plant.getConfigPaths());

        manager.getCommandContexts().registerContext(Plant.class, c -> {
            String plantName = c.popFirstArg();
            Plant plant = Plant.fromPath(plantName);

            if (plant == null) {
                throw new ConditionFailedException("Invalid plant.");
            }

            return plant;
        });

        manager.getCommandConditions().addCondition(String.class, "validPath", (c, exec, value) -> {
            if (Plant.getConfigPaths().contains(value)) {
                return;
            }

            throw new ConditionFailedException("Invalid plant.");
        });

        manager.registerCommand(new AutoFarmCommand().setExceptionHandler((command, registeredCommand, sender, args, throwable) -> {
            sender.sendMessage("An error occurred while executing the command. Please check the console for more information.");
            throwable.printStackTrace();
            return true;
        }));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new DispenserListener(this), this);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
