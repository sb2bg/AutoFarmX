package org.spartandevs.autofarm.util;

import com.cryptomorin.xseries.XMaterial;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Ground {
    DIRT(XMaterial.DIRT, XMaterial.GRASS_BLOCK, XMaterial.PODZOL, XMaterial.COARSE_DIRT, XMaterial.ROOTED_DIRT, XMaterial.MOSS_BLOCK),
    SAND(XMaterial.SAND, XMaterial.RED_SAND, XMaterial.SUSPICIOUS_SAND),
    FARMLAND(XMaterial.FARMLAND),
    SOUL_SAND(XMaterial.SOUL_SAND),
    MYCELIUM(XMaterial.MYCELIUM),
    GRAVEL(XMaterial.GRAVEL, XMaterial.SUSPICIOUS_GRAVEL),
    END_STONE(XMaterial.END_STONE),
    JUNGLE_LOG(XMaterial.JUNGLE_LOG),
    NETHER(XMaterial.CRIMSON_NYLIUM, XMaterial.WARPED_NYLIUM, XMaterial.SOUL_SOIL),
    ANY(XMaterial.values());

    private final Set<XMaterial> materials;

    Ground(XMaterial... materials) {
        this.materials = new HashSet<>(Arrays.asList(materials));
    }

    public Set<XMaterial> getMaterials() {
        return materials;
    }

    public static Set<XMaterial> combine(Ground... grounds) {
        Set<XMaterial> materials = new HashSet<>();
        Arrays.stream(grounds).forEach(ground -> materials.addAll(ground.getMaterials()));

        return materials;
    }
}
