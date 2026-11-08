package com.advanced_enchants.enchantment.fishing_rod;

import com.advanced_enchants.enchantment.effect.FishingRodEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record FireHook() implements FishingRodEnchantmentEffect {
    @Override
    public void apply(PlayerEntity player, LivingEntity victim, int level) {
        victim.setFireTicks(level * 40);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.FIRE_HOOK;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
