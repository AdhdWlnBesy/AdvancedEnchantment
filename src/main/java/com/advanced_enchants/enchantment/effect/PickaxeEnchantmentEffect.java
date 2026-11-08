package com.advanced_enchants.enchantment.effect;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 稿子附魔接口，挖掘触发
 */
public interface PickaxeEnchantmentEffect extends BaseEnchantmentEffect {
    /**
     * 实现稿子的附魔效果
     *
     * @param world  触发附魔效果的世界
     * @param player 挖掘玩家
     * @param pos    挖掘方块的位置
     * @param state  挖掘方块的BlockState
     * @param level  附魔等级
     */
    void apply(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, int level);

    @Override
    default boolean shouldSendPacket() {
        return false;
    }
}
