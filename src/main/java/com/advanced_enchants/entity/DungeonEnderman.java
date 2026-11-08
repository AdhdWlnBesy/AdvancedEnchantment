package com.advanced_enchants.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.world.World;

/**
 * 地牢末影人。
 *
 * @see DungeonEndermanMixin
 */
public class DungeonEnderman extends EndermanEntity {
    public DungeonEnderman(World world) {
        super(EntityType.ENDERMAN, world);
    }

    @Override
    protected boolean teleportRandomly() {
        return false;
    }
}
