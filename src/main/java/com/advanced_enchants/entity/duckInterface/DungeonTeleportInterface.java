package com.advanced_enchants.entity.duckInterface;

/**
 * 禁止瞬移标记接口。
 *
 * @see com.advanced_enchants.mixin.entity.DungeonEndermanMixin
 */
public interface DungeonTeleportInterface {
    /**
     * 把标记写进实体 NBT 时使用的键；区块重载后靠它还原
     */
    String NO_TELEPORT_NBT_KEY = "AdvancedEnchantmentNoTeleport";

    /**
     * 标记为地牢生物：禁止一切瞬移
     */
    void advancedEnchantment$blockTeleport();
}
