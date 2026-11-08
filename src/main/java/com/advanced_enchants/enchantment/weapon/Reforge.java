package com.advanced_enchants.enchantment.weapon;

import com.advanced_enchants.enchantment.effect.WeaponEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

public record Reforge() implements WeaponEnchantmentEffect {
    public static final MapCodec<Reforge> CODEC = MapCodec.unit(Reforge::new);

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        ItemStack stack = attacker.getMainHandStack();
        stack.setDamage(Math.max(0, stack.getDamage() - 2));
        attacker.setStackInHand(Hand.MAIN_HAND, stack);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.REFORGE;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.2f);
    }

    @Override
    public boolean shouldSendPacket() {
        return false;
    }
}
