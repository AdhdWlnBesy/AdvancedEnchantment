package com.advanced_enchants.enchantment.weapon;

import com.advanced_enchants.enchantment.effect.WeaponEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record Sustain() implements WeaponEnchantmentEffect {
    public static final MapCodec<Sustain> CODEC = MapCodec.unit(Sustain::new);

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SUSTAIN;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, (level == 1) ? 0.5f : 0.8f);
    }

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        if (!(attacker instanceof PlayerEntity player)) return;
        if (player.getHungerManager().getFoodLevel() > 0) {
            player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() + 1);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
