package com.advanced_enchants.enchantment.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ShieldEnchantmentEffect extends BaseEnchantmentEffect {
    void apply(LivingEntity victim, @Nullable LivingEntity attacker, float damage, int level, Hand hand);

    default @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
