package com.advanced_enchants.enchantment.arrow;

import com.advanced_enchants.enchantment.effect.BowEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record Palsy() implements BowEnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level, LivingEntity target, ArrowEntity arrow, float amount) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, level * 60, 5));
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, level * 60, 0));
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.PALSY;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.FINAL, 0.5f);
    }
}
