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

public record Blind() implements WeaponEnchantmentEffect {
    public static final MapCodec<Blind> CODEC = MapCodec.unit(Blind::new);

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        victim.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, level * 40, attacker.getRandom().nextBoolean() ? 1 : 0));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.BLIND;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.FINAL, 0.15f);
    }
}
