package com.advanced_enchants.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

/**
 * 地牢苦力怕。
 *
 * @see com.advanced_enchants.mixin.entity.DungeonCreeperMixin
 */
public class DungeonCreeper extends CreeperEntity {
    public DungeonCreeper(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        if (world.isClient()) return;
        if (this.getFuseSpeed() == 1 && getClientFuseTime(1.0F) >= 1.0F) {
            float power = 3 * (shouldRenderOverlay() ? 2.0F : 1.0F);
            Explosion explosion = world.createExplosion(this, Explosion.createDamageSource(world, this), getExplosionBehavior(), getPos(), power, false, World.ExplosionSourceType.MOB);
            explosion.affectWorld(false);
            this.onRemoval(Entity.RemovalReason.KILLED);
            this.discard();
        }
        super.tick();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("isDungeonCreeper", true);
    }

    public static ExplosionBehavior getExplosionBehavior() {
        return new ExplosionBehavior() {
            @Override
            public boolean shouldDamage(Explosion explosion, Entity entity) {
                return entity instanceof PlayerEntity;
            }

            @Override
            public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
                return false;
            }
        };
    }
}
