package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.EnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record Speed() implements EnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SPEED;
    }

    @Override
    public void apply(ServerPlayerEntity player, int level) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, level - 1));
    }

    @Override
    public void notWarn(ServerPlayerEntity player) {
        player.removeStatusEffect(StatusEffects.SPEED);
    }
}
