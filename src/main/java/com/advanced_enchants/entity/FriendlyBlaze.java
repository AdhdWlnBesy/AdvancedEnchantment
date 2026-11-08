package com.advanced_enchants.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/**
 * 友好型烈焰人。
 *
 * @see FriendlyZombie
 */
public class FriendlyBlaze extends BlazeEntity {
    private int despawnCounter = 300;
    private final LivingEntity target;

    public FriendlyBlaze(World world, LivingEntity entity) {
        super(EntityType.BLAZE, world);
        target = entity;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        goalSelector.remove(new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.remove(new ActiveTargetGoal<>(this, PlayerEntity.class, true));
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
        setTarget(target);
        setAttacker(target);
    }
}