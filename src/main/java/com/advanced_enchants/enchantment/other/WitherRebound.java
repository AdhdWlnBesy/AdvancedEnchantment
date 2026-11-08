package com.advanced_enchants.enchantment.other;

import com.advanced_enchants.enchantment.effect.ShieldEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

public record WitherRebound() implements ShieldEnchantmentEffect {
    @Override
    public void apply(LivingEntity victim, LivingEntity attacker, float damage, int level, Hand hand) {
        if (attacker != null) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, (level < 2) ? 80 : 120, (level > 2) ? 1 : 0));
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.WITHER_REBOUND;
    }
}
