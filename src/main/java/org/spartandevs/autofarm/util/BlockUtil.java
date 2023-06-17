package org.spartandevs.autofarm.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockUtil {

    public static Location getPlantLocation(BlockFace face, Location location, Plant plant) {
        location = location.clone();
        boolean cocoa = plant == Plant.COCOA_BEANS;
        int xOffset = cocoa ? 2 : 1; // Cocoa is placed on the side of the block

        switch (face) {
            case EAST:
                location.add(xOffset, 0, 0);
                break;
            case NORTH:
                location.add(0, 0, -xOffset);
                break;
            case SOUTH:
                location.add(0, 0, xOffset);
                break;
            case WEST:
                location.add(-xOffset, 0, 0);
                break;
            case UP:
            case DOWN:
                if (cocoa) break;
                location.add(0, face == BlockFace.UP ? 2 : -1, 0);
                break;
        }

        return location;
    }

    public static Location getGroundBlock(Location location) {
        return location.getBlock().getRelative(BlockFace.DOWN).getLocation();
    }

    public static Block getAboveBlock(Location location) {
        return location.getBlock().getRelative(BlockFace.UP).getLocation().getBlock();
    }

    private static final Map<BlockFace, BlockFace> ANTI_FACE = new HashMap<>();

    static {
        ANTI_FACE.put(BlockFace.NORTH, BlockFace.SOUTH);
        ANTI_FACE.put(BlockFace.SOUTH, BlockFace.NORTH);
        ANTI_FACE.put(BlockFace.WEST, BlockFace.EAST);
        ANTI_FACE.put(BlockFace.EAST, BlockFace.WEST);
    }

    public static void setBlock(Block block, Material material, BlockFace blockFace) {
        BlockFace face = ANTI_FACE.get(blockFace);

        block.setType(material);
        BlockData blockData = block.getBlockData();

        // Set the axis of cocoa beans, vines, and other faced blocks
        if (blockData instanceof Directional) {
            Directional directional = (Directional) blockData;
            directional.setFacing(face);
            block.setBlockData(directional);
        } else if (blockData instanceof MultipleFacing) {
            MultipleFacing multipleFacing = (MultipleFacing) blockData;
            multipleFacing.setFace(face, true);
            block.setBlockData(multipleFacing);
        }
    }

    private static final BlockFace[] FACES = {BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};

    public static Set<Material> getAdjacentMaterials(Location location) {
        return getAdjacentBlocks(location).stream().map(Block::getType).collect(Collectors.toSet());
    }

    public static Set<Block> getAdjacentBlocks(Location location) {
        Set<Block> blocks = new HashSet<>();

        for (BlockFace face : FACES) {
            blocks.add(location.getBlock().getRelative(face));
        }

        return blocks;
    }
}

