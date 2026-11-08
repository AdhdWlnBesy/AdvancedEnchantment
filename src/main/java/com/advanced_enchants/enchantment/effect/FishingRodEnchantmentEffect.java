package com.advanced_enchants.enchantment.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * 鱼竿勾住生物触发附魔接口
 */
public interface FishingRodEnchantmentEffect extends BaseEnchantmentEffect {
    /**
     * 鱼竿勾住生物时，触发效果
     *
     * @param player 攻击玩家
     * @param victim 攻击生物
     * @param level  附魔等级
     */
    void apply(PlayerEntity player, LivingEntity victim, int level);
}
