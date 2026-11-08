package com.advanced_enchants.enchantment.weapon;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.util.math.random.Random;

public record IncreaseByPercentEffect(EnchantmentLevelBasedValue value) implements EnchantmentValueEffect {
    public static final MapCodec<IncreaseByPercentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(IncreaseByPercentEffect::value))
                    .apply(instance, IncreaseByPercentEffect::new));

    @Override
    public float apply(int level, Random random, float inputValue) {
        return inputValue * (1 + this.value.getValue(level)) + random.nextInt(level + 1);
    }

    @Override
    public MapCodec<IncreaseByPercentEffect> getCodec() {
        return CODEC;
    }
}
