package com.advanced_enchants.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * 友好型卫道士。
 *
 * @see com.advanced_enchants.entity.FriendlyZombie
 */
public class FriendlyVindicator extends VindicatorEntity {
    private int despawnCounter = 300;
    private final LivingEntity target;

    public FriendlyVindicator(World world, LivingEntity entity) {
        super(EntityType.VINDICATOR, world);
        target = entity;
        setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
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