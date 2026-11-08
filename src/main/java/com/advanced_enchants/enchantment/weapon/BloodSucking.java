package com.advanced_enchants.enchantment.weapon;

import com.advanced_enchants.enchantment.effect.WeaponEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record BloodSucking() implements WeaponEnchantmentEffect {
    public static final MapCodec<BloodSucking> CODEC = MapCodec.unit(BloodSucking::new);

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        int user_blood = switch (level) {
            case 1, 2 -> 1;
            case 3, 4 -> 2;
            case 5 -> 3;
            default -> 100;
        };
        float victim_blood = switch (level) {
            case 1 -> 0;
            case 2 -> 1;
            case 3, 4 -> 1.5f;
            case 5 -> 2.5f;
            default -> 100;
        };
        if (attacker.getHealth() < attacker.getMaxHealth()) {
            attacker.heal(user_blood);
        }
        if (victim_blood > 0) {
            victim.setHealth(victim.getHealth() - victim_blood);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.BLOODSUCKING;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, switch (level) {
            case 1, 2, 3 -> 0.15F;
            case 4, 5 -> 0.25F;
            default -> 1.0f;
        });
    }
}
