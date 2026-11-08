package com.advanced_enchants.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/**
 * 友好型骷髅。
 *
 * @see FriendlyZombie
 */
public class FriendlySkeleton extends SkeletonEntity {
    private int despawnCounter = 300;
    private final LivingEntity target;

    public FriendlySkeleton(World world, LivingEntity entity) {
        super(EntityType.SKELETON, world);
        target = entity;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
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