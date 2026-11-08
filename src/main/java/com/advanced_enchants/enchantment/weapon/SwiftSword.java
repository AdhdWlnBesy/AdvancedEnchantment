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

public record SwiftSword() implements WeaponEnchantmentEffect {
    public static final MapCodec<SwiftSword> CODEC = MapCodec.unit(SwiftSword::new);

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 40, (level > 2) ? 1 : 0));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }


    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SWIFT_SWORD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, switch (level) {
            case 1 -> 0.2F;
            case 2 -> 0.25F;
            case 3 -> 0.35f;
            default -> 1.0f;
        });
    }
}
