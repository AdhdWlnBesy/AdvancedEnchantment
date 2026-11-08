package com.advanced_enchants.enchantment.effect;

import com.advanced_enchants.event.EnchantTrigNetWorking;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public interface WeaponEnchantmentEffect extends BaseEnchantmentEffect, EnchantmentEntityEffect {
    @Override
    default void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!(user instanceof LivingEntity livingEntity)) return;
        LivingEntity attacker = livingEntity.getAttacker();
        if (attacker == null) return;
        float chance = attacker.getRandom().nextFloat();
        if (chance < getChance(level).getChance(level)) {
            apply(attacker, livingEntity, level);
            if (shouldSendPacket() && attacker instanceof ServerPlayerEntity player) {
                EnchantTrigNetWorking.sendPacket(player, getEnchantment(player.getServer()).value().description(), level);
            }
        }
    }

    void apply(LivingEntity attacker, LivingEntity victim, int level);
}
