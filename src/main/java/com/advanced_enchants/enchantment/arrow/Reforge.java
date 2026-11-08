package com.advanced_enchants.enchantment.arrow;

import com.advanced_enchants.enchantment.effect.BowEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

public record Reforge() implements BowEnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level, LivingEntity target, ArrowEntity arrow, float amount) {
        ItemStack stack = player.getMainHandStack();
        stack.setDamage(Math.max(0, stack.getDamage() - 1));
        player.setStackInHand(Hand.MAIN_HAND, stack);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.REFORGE;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.2f);
    }
}
