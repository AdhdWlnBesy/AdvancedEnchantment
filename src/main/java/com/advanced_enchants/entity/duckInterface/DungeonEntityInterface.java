package com.advanced_enchants.entity.duckInterface;

/**
 * 地牢实体标记接口。
 *
 * @see com.advanced_enchants.mixin.entity.DungeonEntityMixin
 */
public interface DungeonEntityInterface {
    void advancedEnchantment$setIsDungeonEntity();

    boolean advancedEnchantment$isDungeonEntity();
}
