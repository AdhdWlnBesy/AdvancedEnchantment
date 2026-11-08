package com.advanced_enchants.enchantment.weapon;

import com.advanced_enchants.enchantment.effect.WeaponEnchantmentEffect;
import com.advanced_enchants.event.EnchantTrigNetWorking;
import com.advanced_enchants.register.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record Restore() implements WeaponEnchantmentEffect {
    public static final MapCodec<Restore> CODEC = MapCodec.unit(Restore::new);

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.RESTORE;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        if (attacker.getHealth() <= 4) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, (level > 2) ? 100 : 60, 1));
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40 * level, (level > 1) ? 1 : 0));
            if (attacker instanceof ServerPlayerEntity serverPlayer) {
                EnchantTrigNetWorking.sendPacket(serverPlayer, getEnchantment(serverPlayer.getServer()).value().description(), level);
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @Override
    public boolean shouldSendPacket() {
        return false;
    }
}
