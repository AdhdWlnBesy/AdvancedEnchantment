package com.advanced_enchants.enchantment.weapon;

import com.advanced_enchants.enchantment.effect.WeaponEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public record Banish() implements WeaponEnchantmentEffect {
    public static final MapCodec<Banish> CODEC = MapCodec.unit(Banish::new);

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.BANISH;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, switch (level) {
            case 1 -> 0.08f;
            case 2 -> 0.12f;
            case 3, 4 -> 0.15f;
            default -> 1;
        });
    }

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        int count = 0;
        for (StatusEffectInstance effectInstance : new ArrayList<>(attacker.getStatusEffects())) {
            if (count >= level) break;
            if (effectInstance.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) {
                attacker.removeStatusEffect(effectInstance.getEffectType());
                count++;
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
