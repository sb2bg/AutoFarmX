package org.spartandevs.autofarm.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.spartandevs.autofarm.AutoFarm;
import org.spartandevs.autofarm.util.Plant;

public class AutoFarmGUI {
    private final Gui gui = Gui
            .gui()
            .disableAllInteractions()
            .title(Component.text("AutoFarmX"))
            .create();

    public AutoFarmGUI(AutoFarm plugin) {
        for (Plant plant : Plant.values()) {
            Material material = plant.getPlant().parseMaterial();

            if (material == null) {
                continue;
            }

            GuiItem item = ItemBuilder
                    .from(material)
                    .name(Component.text(plant.getFormattedName(), NamedTextColor.YELLOW))
                    .lore(plugin.getConfigManager().isEnabled(plant)
                            ? Component.text("Enabled", NamedTextColor.GREEN)
                            : Component.text("Disabled", NamedTextColor.RED))
                    .asGuiItem();

            item.setAction(event -> plugin.getConfigManager().toggle(plant));
            gui.addItem(item);
        }

        GuiItem disableAll = ItemBuilder
                .from(Material.BARRIER)
                .name(Component.text("Disable All", NamedTextColor.RED))
                .asGuiItem();

        disableAll.setAction(event -> plugin.getConfigManager().togglePlugin());
        gui.setItem(6, 4, disableAll);

        gui.getFiller().fill(ItemBuilder
                .from(Material.GRAY_STAINED_GLASS_PANE)
                .name(Component.text(" "))
                .asGuiItem());
    }

    public void open(Player player) {
        gui.open(player);
    }
}
