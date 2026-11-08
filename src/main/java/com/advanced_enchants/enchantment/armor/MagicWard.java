package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.BaseEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record MagicWard() implements BaseEnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.MAGIC_WARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
