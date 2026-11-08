package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.EnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record Gills() implements EnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, -1, 0));
    }

    @Override
    public void notWarn(ServerPlayerEntity player) {
        player.removeStatusEffect(StatusEffects.WATER_BREATHING);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.GILLS;
    }
}
