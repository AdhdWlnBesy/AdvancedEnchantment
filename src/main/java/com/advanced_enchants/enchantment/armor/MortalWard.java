package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import com.advanced_enchants.register.ModTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record MortalWard() implements ArmorHitEnchantmentEffect {
    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        if (attacker.getType().isIn(ModTags.OVERWORLD)) {
            player.heal(damage * 0.15f * level);
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.MORTAL_WARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.FINAL, 0.5f);
    }
}
