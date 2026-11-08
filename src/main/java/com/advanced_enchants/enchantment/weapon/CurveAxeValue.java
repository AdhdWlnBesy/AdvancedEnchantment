package com.advanced_enchants.enchantment.weapon;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;

public record CurveAxeValue() implements EnchantmentLevelBasedValue {
    public static final MapCodec<CurveAxeValue> CODEC = MapCodec.unit(CurveAxeValue::new);

    @Override
    public float getValue(int level) {
        return switch (level) {
            case 1 -> 0.25F;
            case 2 -> 1F / 3F;
            case 3 -> 0.5F;
            default -> 0F;
        };
    }

    @Override
    public MapCodec<? extends EnchantmentLevelBasedValue> getCodec() {
        return CODEC;
    }
}
