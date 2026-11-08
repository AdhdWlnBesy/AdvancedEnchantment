package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record FireWard() implements ArmorHitEnchantmentEffect {
    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        if (level < 3) {
            player.extinguish();
            if (player instanceof ServerPlayerEntity serverPlayer) {
                sendPacket(serverPlayer, getEnchantment(serverPlayer.getServer()), level);
            }
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.FIRE_WARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.4f);
    }

    @Override
    public boolean shouldSendPacket() {
        return false;
    }
}
