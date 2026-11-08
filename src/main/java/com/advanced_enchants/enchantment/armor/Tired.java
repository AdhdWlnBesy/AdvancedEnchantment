package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record Tired() implements ArmorHitEnchantmentEffect {
    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 80, (level < 3) ? 0 : 1));
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.TIRED;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.2f);
    }
}
