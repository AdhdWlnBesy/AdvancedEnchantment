package com.advanced_enchants.enchantment.effect;

import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

/**
 * 通用的附魔效果实现，每5tick执行一次apply，并默认一定触发
 */
public interface EnchantmentEffect extends BaseEnchantmentEffect {
    /**
     * 实现附魔效果
     *
     * @param player 玩家实体
     * @param level  附魔等级
     */
    void apply(ServerPlayerEntity player, int level);

    default void notWarn(ServerPlayerEntity player) {
    }

    @Override
    default @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
