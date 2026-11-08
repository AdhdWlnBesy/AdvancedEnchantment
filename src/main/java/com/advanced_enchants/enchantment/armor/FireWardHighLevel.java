package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.EnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record FireWardHighLevel() implements EnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.FIRE_WARD;
    }

    @Override
    public void apply(ServerPlayerEntity player, int level) {
        if (level > 2) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, -1, 0));
        }
    }

    @Override
    public void notWarn(ServerPlayerEntity player) {
        player.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
    }
}
