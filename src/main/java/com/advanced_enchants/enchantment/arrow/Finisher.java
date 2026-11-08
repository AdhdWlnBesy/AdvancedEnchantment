package com.advanced_enchants.enchantment.arrow;

import com.advanced_enchants.enchantment.effect.BowEnchantmentEffect;
import com.advanced_enchants.event.EnchantTrigNetWorking;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record Finisher() implements BowEnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level, LivingEntity target, ArrowEntity arrow, float amount) {
        float maxHealth = target.getMaxHealth() / 2;
        float value = maxHealth - target.getHealth();
        if (value > 0) {
            float damage = amount * (value / maxHealth) / 1.5f;
            float remaining = target.getHealth() - Math.min(damage, amount * 0.5f);
            if (remaining <= 0) target.kill();
            else target.setHealth(remaining);
            EnchantTrigNetWorking.sendPacket(player, getEnchantment(player.getServer()).value().description(), level);
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.FINISHER;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }

    @Override
    public boolean shouldSendPacket() {
        return false;
    }
}
