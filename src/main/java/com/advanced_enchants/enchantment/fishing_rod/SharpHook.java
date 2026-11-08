package com.advanced_enchants.enchantment.fishing_rod;

import com.advanced_enchants.enchantment.effect.FishingRodEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record SharpHook() implements FishingRodEnchantmentEffect {
    @Override
    public void apply(PlayerEntity player, LivingEntity victim, int level) {
        victim.damage(player.getDamageSources().playerAttack(player), level * 2);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SHARP_HOOK;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
