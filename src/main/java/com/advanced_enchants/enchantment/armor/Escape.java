package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.event.EnchantTrigNetWorking;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record Escape() implements ArmorHitEnchantmentEffect {
    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        if (player.getHealth() <= 4) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 120 * level, level - 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 120 * level, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * level, 0));
            if (player instanceof ServerPlayerEntity serverPlayer) {
                EnchantTrigNetWorking.sendPacket(serverPlayer, getEnchantment(serverPlayer.getServer()).value().description(), level);
            }
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.ESCAPE;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
