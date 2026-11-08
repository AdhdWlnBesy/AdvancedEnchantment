package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record Block() implements ArmorHitEnchantmentEffect {
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
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.BLOCK;
    }

    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        float defense = (float) switch (level) {
            case 1 -> (double) 2 / 3;
            case 2 -> 0.5;
            case 3 -> (double) 1 / 3;
            case 4 -> 0.25;
            default -> 0;
        };
        player.heal(damage * (1 - defense));
    }
}
