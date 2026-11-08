package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/**
 * 模组物品注册。
 *
 * @see ModBlocks
 */
public class ModItems {
    private static int count = 0;

    public static final Item ADVANCED_ENCHANTS_COMPENDIUM = registerItems("advanced_enchants_compendium", new ColorItem(Formatting.WHITE, 1, false));
    public static final Item ORDINARY_ENCHANTS_BOOK = registerItems("ordinary_enchants_book", new ColorItem(Formatting.GREEN, 8, true));
    public static final Item ADVANCED_ENCHANTS_BOOK = registerItems("advanced_enchants_book", new ColorItem(Formatting.YELLOW, 8, true));
    public static final Item RARE_ENCHANTS_BOOK = registerItems("rare_enchants_book", new ColorItem(Formatting.AQUA, 8, true));
    public static final Item PRECIOUS_ENCHANTS_BOOK = registerItems("precious_enchants_book", new ColorItem(Formatting.DARK_PURPLE, 8, true));
    public static final Item ORDINARY_ENCHANTS_SCROLL = registerItems("ordinary_enchants_scroll", new ColorItem(Formatting.GREEN, 1, false));
    public static final Item ADVANCED_ENCHANTS_SCROLL = registerItems("advanced_enchants_scroll", new ColorItem(Formatting.YELLOW, 1, false));
    public static final Item RARE_ENCHANTS_SCROLL = registerItems("rare_enchants_scroll", new ColorItem(Formatting.AQUA, 1, false));
    public static final Item PRECIOUS_ENCHANTS_SCROLL = registerItems("precious_enchants_scroll", new ColorItem(Formatting.DARK_PURPLE, 1, false));
    public static final Item EMPTY_SCROLL = registerItems("empty_scroll", new Item(new Item.Settings()));
    public static final Item ENCHANTED_SCROLL = registerItems("enchanted_scroll", new GlintItem(Formatting.WHITE, 1, false));
    public static final Item ORDINARY_LEVELUP_BALL = registerItems("ordinary_levelup_ball", new GlintItem(Formatting.GREEN, 4, false));
    public static final Item ADVANCED_LEVELUP_BALL = registerItems("advanced_levelup_ball", new GlintItem(Formatting.YELLOW, 4, false));
    public static final Item RARE_LEVELUP_BALL = registerItems("rare_levelup_ball", new GlintItem(Formatting.AQUA, 4, false));
    public static final Item PRECIOUS_LEVELUP_BALL = registerItems("precious_levelup_ball", new GlintItem(Formatting.DARK_PURPLE, 4, false));
    public static final Item LEVEL1_SLOT_BUILDER = registerItems("level1_slot_builder", new GlintItem(Formatting.DARK_GREEN, 1, true));
    public static final Item LEVEL2_SLOT_BUILDER = registerItems("level2_slot_builder", new GlintItem(Formatting.GOLD, 1, true));
    public static final Item LEVEL3_SLOT_BUILDER = registerItems("level3_slot_builder", new GlintItem(Formatting.RED, 1, true));
    public static final Item DUNGEON1_KEY = registerItems("dungeon1_key", new GlintItem(Formatting.WHITE, 8, false));
    public static final Item DUNGEON2_KEY = registerItems("dungeon2_key", new GlintItem(Formatting.GOLD, 8, false));
    public static final Item DUNGEON3_KEY = registerItems("dungeon3_key", new GlintItem(Formatting.GREEN, 8, false));
    public static final Item DUNGEON4_KEY = registerItems("dungeon4_key", new GlintItem(Formatting.AQUA, 8, false));
    public static final Item DUNGEON5_KEY = registerItems("dungeon5_key", new GlintItem(Formatting.DARK_GRAY, 8, false));

    private static Item registerItems(String id, Item item) {
        count++;
        return Registry.register(Registries.ITEM, Identifier.of(AdvancedEnchantment.MOD_ID, id), item);
    }

    public static void loadItems() {
        AdvancedEnchantment.LOGGER.info("Loading {} Items successfully!", count);
    }
}

class ColorItem extends Item {
    private final Formatting color;
    private final boolean isBold;

    public ColorItem(Formatting color, int maxCount, boolean IsBold) {
        super(new Item.Settings().maxCount(maxCount));
        isBold = IsBold;
        this.color = color;
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText literal = super.getName(stack).copy();
        return (isBold) ? literal.formatted(color, Formatting.BOLD) : literal.formatted(color);
    }
}

class GlintItem extends ColorItem {
    public GlintItem(Formatting color, int maxCount, boolean IsBold) {
        super(color, maxCount, IsBold);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}