package com.advanced_enchants.enchantment.weapon;

import com.advanced_enchants.enchantment.effect.WeaponEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record Vigorously() implements WeaponEnchantmentEffect {
    public static final MapCodec<Vigorously> CODEC = MapCodec.unit(Vigorously::new);

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        int strengthLevel = switch (level) {
            case 1, 2 -> 0;
            case 3 -> 1;
            case 4 -> 2;
            default -> 1;
        };
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 40, strengthLevel));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.VIGOROUSLY;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, switch (level) {
            case 1 -> 0.5F;
            case 2 -> 0.75F;
            case 3, 4 -> 1;
            default -> 1.0f;
        });
    }
}
