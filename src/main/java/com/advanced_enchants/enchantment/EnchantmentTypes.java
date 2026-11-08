package com.advanced_enchants.enchantment;

import com.advanced_enchants.register.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum EnchantmentTypes {
    ORDINARY(ModItems.ORDINARY_ENCHANTS_SCROLL, ModItems.ORDINARY_LEVELUP_BALL, 1),
    ADVANCED(ModItems.ADVANCED_ENCHANTS_SCROLL, ModItems.ADVANCED_LEVELUP_BALL, 2),
    RARE(ModItems.RARE_ENCHANTS_SCROLL, ModItems.RARE_LEVELUP_BALL, 3),
    PRECIOUS(ModItems.PRECIOUS_ENCHANTS_SCROLL, ModItems.PRECIOUS_LEVELUP_BALL, 4),
    NULL(Items.ENCHANTED_BOOK, Items.ENDER_EYE, 0);

    public final Item scroll;
    public final Item levelUpBall;
    public final int level;

    EnchantmentTypes(Item item, Item levelBall, int level) {
        this.levelUpBall = levelBall;
        this.scroll = item;
        this.level = level;
    }
}
