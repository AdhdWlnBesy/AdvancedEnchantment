package com.advanced_enchants.enchantment.other;

import com.advanced_enchants.enchantment.effect.ShieldEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public record Fortify() implements ShieldEnchantmentEffect {
    @Override
    public void apply(LivingEntity victim, LivingEntity attacker, float damage, int level, Hand hand) {
        int i = 1 + MathHelper.floor(damage);
        int j = victim.getRandom().nextBetween(3, 5);
        ItemStack stack = victim.getOffHandStack();
        stack.setDamage(Math.max(0, i - j));
        victim.setStackInHand(hand, stack);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.FORTIFY;
    }
}
