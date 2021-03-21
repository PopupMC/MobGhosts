package com.popupmc.mobghosts;

import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class HostileMobs {
    public static void setup() {
        for(EntityType type : list) {
            hashList.put(type, true);
        }
    }

    public static final EntityType[] list = {
            EntityType.BLAZE,
            EntityType.CREEPER,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDERMITE,
            EntityType.EVOKER,
            EntityType.GHAST,
            EntityType.GUARDIAN,
            EntityType.HOGLIN,
            EntityType.HUSK,
            EntityType.MAGMA_CUBE,
            EntityType.PHANTOM,
            EntityType.PIGLIN_BRUTE,
            EntityType.PILLAGER,
            EntityType.RAVAGER,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON,
            EntityType.SLIME,
            EntityType.STRAY,
            EntityType.VEX,
            EntityType.VINDICATOR,
            EntityType.WITCH,
            EntityType.WITHER_SKELETON,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIE_HORSE,
            EntityType.ILLUSIONER,
            EntityType.RABBIT,
            EntityType.BEE,
            EntityType.CAVE_SPIDER,
            EntityType.DOLPHIN,
            EntityType.ENDERMAN,
            EntityType.LLAMA,
            EntityType.PIGLIN,
            EntityType.PANDA,
            EntityType.POLAR_BEAR,
            EntityType.SPIDER,
            EntityType.WOLF,
            EntityType.ZOMBIFIED_PIGLIN
    };

    public static final HashMap<EntityType,Boolean> hashList = new HashMap<>();
}
