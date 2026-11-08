package com.advanced_enchants.enchantment.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * 弓附魔效果接口，命中触发
 *
 * @see com.advanced_enchants.mixin.enchantment.handler.BowEnchantmentHandler
 */
public interface BowEnchantmentEffect extends BaseEnchantmentEffect {
    /**
     * 实现弓的附魔效果
     *
     * @param player 攻击玩家
     * @param level  附魔等级
     * @param target 射中的目标
     * @param arrow  射中目标的箭矢
     * @param amount 箭矢伤害
     */
    void apply(ServerPlayerEntity player, int level, LivingEntity target, ArrowEntity arrow, float amount);
}
