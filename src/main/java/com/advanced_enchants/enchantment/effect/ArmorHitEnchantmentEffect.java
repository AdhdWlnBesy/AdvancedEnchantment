package com.advanced_enchants.enchantment.effect;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * 受击时，盔甲触发附魔效果接口
 */
public interface ArmorHitEnchantmentEffect extends BaseEnchantmentEffect {
    /**
     * 受击时，盔甲触发附魔
     *
     * @param attacker 攻击者
     * @param player   受害玩家
     * @param level    附魔等级
     */
    void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage);
}
