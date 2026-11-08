package com.advanced_enchants.enchantment.other;

import com.advanced_enchants.enchantment.effect.ShieldEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

public record ShockRebound() implements ShieldEnchantmentEffect {
    @Override
    public void apply(LivingEntity victim, LivingEntity attacker, float damage, int level, Hand hand) {
        if (attacker == null) return;
        if (victim instanceof PlayerEntity player) {
            player.attack(attacker);
        } else {
            victim.tryAttack(attacker);
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SHOCK_REBOUND;
    }
}
