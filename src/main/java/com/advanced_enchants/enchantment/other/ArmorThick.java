package com.advanced_enchants.enchantment.other;

import com.advanced_enchants.enchantment.effect.EnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record ArmorThick() implements EnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level) {
        EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if (instance != null) {
            instance.setBaseValue((level > 1) ? 8 : 4);
            if (level > 1) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, level - 2));
            }
        }
    }

    @Override
    public void notWarn(ServerPlayerEntity player) {
        EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if (instance != null) {
            instance.setBaseValue(0);
        }
        player.removeStatusEffect(StatusEffects.RESISTANCE);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.ARMOR_THICK;
    }
}
