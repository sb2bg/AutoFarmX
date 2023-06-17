package org.spartandevs.autofarm.util;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;

import java.util.Set;

public class PlantChecks {
    public static boolean generic(Location plantLocation, Set<XMaterial> ground) {
        if (plantLocation.getBlock().getType() != Material.AIR) {
            return false;
        }

        return ground.contains(XMaterial.matchXMaterial(BlockUtil.getGroundBlock(plantLocation).getBlock().getType()));
    }

    public static boolean cactus(Location plantLocation, Set<XMaterial> ground) {
        if (plantLocation.getBlock().getType() != Material.AIR) {
            return false;
        }

        // Cactus requires air surrounding it and above it
        if (BlockUtil.getAdjacentMaterials(plantLocation).contains(Material.AIR)) {
            return false;
        }

        if (BlockUtil.getAboveBlock(plantLocation).getType() != Material.AIR) {
            return false;
        }

        return ground.contains(XMaterial.matchXMaterial(BlockUtil.getGroundBlock(plantLocation).getBlock().getType()));
    }

    public static boolean sea(Location plantLocation, Set<XMaterial> ground) {
        if (plantLocation.getBlock().getType() != Material.WATER) {
            return false;
        }

        return ground.contains(XMaterial.matchXMaterial(BlockUtil.getGroundBlock(plantLocation).getBlock().getType()));
    }

    public static boolean sugarCane(Location plantLocation, Set<XMaterial> ground) {
        if (plantLocation.getBlock().getType() != Material.AIR) {
            return false;
        }

        Location groundBlock = BlockUtil.getGroundBlock(plantLocation);

        if (!ground.contains(XMaterial.matchXMaterial(groundBlock.getBlock().getType()))) {
            return false;
        }

        // Sugar cane requires water next to it
        for (Block adj : BlockUtil.getAdjacentBlocks(groundBlock)) {
            if (adj.getType() == Material.WATER) {
                return true;
            }

            if (!Version.SERVER_VERSION.isVersionOrHigher(Version.V13) || !(adj.getBlockData() instanceof Waterlogged)) {
                continue;
            }

            Waterlogged waterlogged = (Waterlogged) adj.getBlockData();

            if (waterlogged.isWaterlogged()) {
                return true;
            }
        }

        return false;
    }

    public static boolean onFace(Location plantLocation, Set<XMaterial> ground) {
        if (plantLocation.getBlock().getType() != Material.AIR) {
            return false;
        }

        Bukkit.broadcastMessage("This doesn't work yet!");

        Material groundBlock = BlockUtil.getGroundBlock(plantLocation).getBlock().getType();
        return ground.contains(XMaterial.matchXMaterial(groundBlock));
    }
}
