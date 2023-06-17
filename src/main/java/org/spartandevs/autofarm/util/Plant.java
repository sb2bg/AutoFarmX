package org.spartandevs.autofarm.util;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum Plant {
    WHEAT(XMaterial.WHEAT, XMaterial.WHEAT_SEEDS, Ground.FARMLAND.getMaterials(), PlantChecks::generic),
    CARROT(XMaterial.CARROTS, XMaterial.CARROT, Ground.FARMLAND.getMaterials(), PlantChecks::generic),
    POTATO(XMaterial.POTATOES, XMaterial.POTATO, Ground.FARMLAND.getMaterials(), PlantChecks::generic),
    BEETROOT(XMaterial.BEETROOTS, XMaterial.BEETROOT_SEEDS, Ground.FARMLAND.getMaterials(), PlantChecks::generic),
    NETHER_WART(XMaterial.NETHER_WART, XMaterial.NETHER_WART, Ground.SOUL_SAND.getMaterials(), PlantChecks::generic),
    COCOA_BEANS(XMaterial.COCOA_BEANS, XMaterial.COCOA_BEANS, Ground.JUNGLE_LOG.getMaterials(), PlantChecks::onFace, BlockFace.UP, BlockFace.DOWN),
    SUGAR_CANE(XMaterial.SUGAR_CANE, XMaterial.SUGAR_CANE, Ground.combine(Ground.DIRT, Ground.SAND), PlantChecks::sugarCane),
    CACTUS(XMaterial.CACTUS, XMaterial.CACTUS, Ground.SAND.getMaterials(), PlantChecks::cactus),
    BAMBOO(XMaterial.BAMBOO, XMaterial.BAMBOO, Ground.combine(Ground.DIRT, Ground.SAND, Ground.GRAVEL), PlantChecks::generic),
    KELP(XMaterial.KELP, XMaterial.KELP, Ground.SAND.getMaterials(), PlantChecks::sea),
    MELON(XMaterial.MELON, XMaterial.MELON_SEEDS, Ground.FARMLAND.getMaterials(), PlantChecks::generic),
    PUMPKIN(XMaterial.PUMPKIN, XMaterial.PUMPKIN_SEEDS, Ground.FARMLAND.getMaterials(), PlantChecks::generic),
    GRASS(XMaterial.GRASS, XMaterial.GRASS, Ground.DIRT.getMaterials(), PlantChecks::generic),
    FERN(XMaterial.FERN, XMaterial.FERN, Ground.DIRT.getMaterials(), PlantChecks::generic),
    VINE(XMaterial.VINE, XMaterial.VINE, Ground.ANY.getMaterials(), PlantChecks::onFace, BlockFace.UP, BlockFace.DOWN),
    CHORUS_FLOWER(XMaterial.CHORUS_FLOWER, XMaterial.CHORUS_FLOWER, Ground.END_STONE.getMaterials(), PlantChecks::generic),
    DEAD_BUSH(XMaterial.DEAD_BUSH, XMaterial.DEAD_BUSH, Ground.combine(Ground.DIRT, Ground.SAND), PlantChecks::generic),
    SWEET_BERRIES(XMaterial.SWEET_BERRIES, XMaterial.SWEET_BERRIES, Ground.combine(Ground.DIRT, Ground.FARMLAND), PlantChecks::generic),

    // TODO Mushrooms need extra work (https://minecraft.fandom.com/wiki/Mushroom)
    BROWN_MUSHROOM(XMaterial.BROWN_MUSHROOM, XMaterial.BROWN_MUSHROOM, Ground.ANY.getMaterials(), PlantChecks::generic),
    RED_MUSHROOM(XMaterial.RED_MUSHROOM, XMaterial.RED_MUSHROOM, Ground.ANY.getMaterials(), PlantChecks::generic),
    CRIMSON_FUNGUS(XMaterial.CRIMSON_FUNGUS, XMaterial.CRIMSON_FUNGUS, Ground.combine(Ground.DIRT, Ground.NETHER), PlantChecks::generic),
    WARPED_FUNGUS(XMaterial.WARPED_FUNGUS, XMaterial.WARPED_FUNGUS, Ground.combine(Ground.DIRT, Ground.NETHER), PlantChecks::generic),
    CRIMSON_ROOTS(XMaterial.CRIMSON_ROOTS, XMaterial.CRIMSON_ROOTS, Ground.combine(Ground.DIRT, Ground.NETHER), PlantChecks::generic),
    WARPED_ROOTS(XMaterial.WARPED_ROOTS, XMaterial.WARPED_ROOTS, Ground.combine(Ground.DIRT, Ground.NETHER), PlantChecks::generic),
    TWISTING_VINES(XMaterial.TWISTING_VINES, XMaterial.TWISTING_VINES, Ground.ANY.getMaterials(), PlantChecks::onFace, BlockFace.UP, BlockFace.DOWN),
    WEEPING_VINES(XMaterial.WEEPING_VINES, XMaterial.WEEPING_VINES, Ground.ANY.getMaterials(), PlantChecks::onFace, BlockFace.UP, BlockFace.DOWN),
    NETHER_SPROUTS(XMaterial.NETHER_SPROUTS, XMaterial.NETHER_SPROUTS, Ground.combine(Ground.DIRT, Ground.NETHER), PlantChecks::generic),

    ;

    private final XMaterial plant;
    private final XMaterial seed;
    private final Set<XMaterial> ground;
    private final Plantable plantable;
    private final BlockFace[] blacklistedFaces;

    interface Plantable {
        boolean isPlantable(Location location, Set<XMaterial> ground);
    }

    Plant(XMaterial plant, XMaterial seed, Set<XMaterial> ground, Plantable plantable, BlockFace... blacklistedFaces) {
        this.plant = plant;
        this.seed = seed;
        this.ground = ground;
        this.plantable = plantable;
        this.blacklistedFaces = blacklistedFaces;
    }

    public XMaterial getPlant() {
        return plant;
    }

    public XMaterial getSeed() {
        return seed;
    }

    public Set<XMaterial> getGround() {
        return ground;
    }

    public static Plant fromPath(String path) {
        for (Plant plant : values()) {
            if (plant.getConfigPath().equals(path)) {
                return plant;
            }
        }

        return null;
    }

    public boolean isPlantable(Location location) {
        return plantable.isPlantable(location, ground);
    }

    public String getFormattedName() {
        return Message.capitalize(PLANTS.get(this.getSeed()).replace("-", " "));
    }

    private static final Map<XMaterial, String> PLANTS = new HashMap<>();

    static {
        for (Plant plant : values()) {
            String path = plant.name().toLowerCase().replace("_", "-");

            PLANTS.put(plant.getSeed(), path);
        }
    }

    public String getConfigPath() {
        return PLANTS.get(this.getSeed());
    }

    public static boolean isPlant(XMaterial material) {
        return PLANTS.containsKey(material);
    }

    public static Collection<String> getConfigPaths() {
        return PLANTS.values();
    }
}
