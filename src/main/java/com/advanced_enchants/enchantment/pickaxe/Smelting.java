package com.advanced_enchants.enchantment.pickaxe;

import com.advanced_enchants.enchantment.effect.BaseEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record Smelting() implements BaseEnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SMELTING;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
