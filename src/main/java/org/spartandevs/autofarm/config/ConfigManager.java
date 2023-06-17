package org.spartandevs.autofarm.config;

import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bstats.bukkit.Metrics;
import org.spartandevs.autofarm.AutoFarm;
import org.spartandevs.autofarm.util.Plant;

public class ConfigManager extends BaseDocumentManager {
    public ConfigManager(AutoFarm plugin) {
        super(plugin, "config.yml");
        registerStatistics();
    }

    @Override
    protected UpdaterSettings updater() {
        return UpdaterSettings
                .builder()
                .setVersioning(new BasicVersioning("config-version"))
                .build();
    }

    public boolean isEnabled() {
        return document.getBoolean("enabled");
    }

    public boolean isEnabled(Plant plant) {
        return document.getBoolean(plant.getConfigPath());
    }

    public void togglePlugin() {
        boolean toggled = !isEnabled();
        document.set("enabled", toggled);
        save();
    }

    public void togglePlugin(boolean toggled) {
        document.set("enabled", toggled);
        save();
    }

    public void toggle(Plant plant) {
        boolean toggled = !isEnabled(plant);
        document.set(plant.getConfigPath(), toggled);
        save();
    }

    public void toggle(Plant plant, boolean toggled) {
        document.set(plant.getConfigPath(), toggled);
        save();
    }

    private void registerStatistics() {
        Metrics metrics = new Metrics(plugin, 8913);
    }
}
