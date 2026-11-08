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

public record SapVital() implements WeaponEnchantmentEffect {
    public static final MapCodec<SapVital> CODEC = MapCodec.unit(SapVital::new);

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        if (attacker instanceof PlayerEntity player) {
            if (player.getHungerManager().getFoodLevel() > 0 && player.getRandom().nextBoolean()) {
                player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() + 1);
            }
            player.getHungerManager().setSaturationLevel(player.getHungerManager().getSaturationLevel() + attacker.getRandom().nextBetween(2, 5));
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SAP_VITAL;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.12f);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
