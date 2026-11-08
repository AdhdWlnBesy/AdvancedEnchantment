package com.advanced_enchants.entity.duckInterface;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;

/**
 * 玩家扩展接口。
 *
 * @see com.advanced_enchants.mixin.entity.PlayerMixinImpl
 */
public interface PlayerInterface {
    void advancedEnchantment$setEnchantmentKey(RegistryKey<Enchantment> enchantment);

    RegistryKey<Enchantment> advancedEnchantment$getEnchantmentKey();

    void advancedEnchantment$addSmithingCount();

    int advancedEnchantment$getSmithingCount();
}
