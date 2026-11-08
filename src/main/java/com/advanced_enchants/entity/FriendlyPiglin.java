package com.advanced_enchants.entity;

import com.advanced_enchants.mixin.entity.PiglinBrainInvoker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/**
 * 友好型猪灵。
 *
 * @see com.advanced_enchants.entity.FriendlyZombie
 */
public class FriendlyPiglin extends PiglinEntity {
    private int despawnCounter = 300;
    private final LivingEntity target;

    public FriendlyPiglin(World world, LivingEntity entity) {
        super(EntityType.PIGLIN, world);
        target = entity;
    }

    @Override
    protected void drop(ServerWorld world, DamageSource damageSource) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient()) {
            despawnCounter--;
            if (despawnCounter <= 0 && isAlive()) {
                discard();
            }
            if (target.isRemoved() || target.isDead()) {
                discard();
            }
        }
        PiglinBrainInvoker.onAttacked(this, target);
    }
}