package com.advanced_enchants.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.world.World;

/**
 * 地牢潜影贝。
 *
 * @see com.advanced_enchants.mixin.entity.DungeonShulkerMixin
 */
public class DungeonShulker extends ShulkerEntity {
    public DungeonShulker(World world) {
        super(EntityType.SHULKER, world);
    }
}
