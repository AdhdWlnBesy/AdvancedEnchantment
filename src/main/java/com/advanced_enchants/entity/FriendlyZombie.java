package com.advanced_enchants.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/**
 * 友好型僵尸。
 *
 * @see FriendlySkeleton
 */
public class FriendlyZombie extends ZombieEntity {
    private int despawnCounter = 300;
    private final LivingEntity target;

    public FriendlyZombie(World world, LivingEntity entity) {
        super(EntityType.ZOMBIE, world);
        target = entity;
    }

    @Override
    protected void initGoals() {
        goalSelector.add(8, new LookAroundGoal(this));
        initCustomGoals();
    }

    @Override
    protected void initCustomGoals() {
        goalSelector.add(2, new ZombieAttackGoal(this, 1.0, false));
        goalSelector.add(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
        goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(ZombifiedPiglinEntity.class));
        targetSelector.add(3, new ActiveTargetGoal<>(this, MerchantEntity.class, false));
        targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        targetSelector.add(5, new ActiveTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
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
