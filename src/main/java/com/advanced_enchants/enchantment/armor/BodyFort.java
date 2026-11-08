package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.EnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record BodyFort() implements EnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, -1, level * 2 - 1, false, false, true));
    }

    @Override
    public void notWarn(ServerPlayerEntity player) {
        player.removeStatusEffect(StatusEffects.HEALTH_BOOST);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.BODY_FORT;
    }
}
