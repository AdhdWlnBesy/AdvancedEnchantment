package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * 模组物品组注册。
 *
 * @see com.advanced_enchants.register.ModItems
 */
public class ModItemGroups {
    public static final RegistryKey<ItemGroup> ADVANCED_ENCHANTS_GROUPS = register();//创建物品栏
    private static ItemGroup.DisplayContext display_context;

    private static RegistryKey<ItemGroup> register() {//创建物品栏函数
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.ofVanilla("advanced_enchants_groups"));
    }

    public static void loadItemGroups() {//加载物品栏函数
        Registry.register(Registries.ITEM_GROUP, ADVANCED_ENCHANTS_GROUPS,
                ItemGroup.create(ItemGroup.Row.TOP, 7).displayName(Text.translatable("advanced_enchants.advanced_enchants_groups")).//为物品栏添加语言文件
                        icon(() -> new ItemStack(ModItems.ADVANCED_ENCHANTS_COMPENDIUM))
                        .entries((displayContext, entries) -> {
                            display_context = displayContext;
                            entries.add(ModItems.ADVANCED_ENCHANTS_COMPENDIUM);
                            entries.add(ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE);
                            entries.add(ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE);
                            entries.add(ModBlocks.ADVANCED_ENCHANTS_ANVIL);
                            entries.add(ModBlocks.DUNGEON_VAULT);
                            entries.add(ModBlocks.DUNGEON_SPAWNER);
                            entries.add(ModItems.EMPTY_SCROLL);
                            entries.add(ModItems.ORDINARY_ENCHANTS_BOOK);
                            entries.add(ModItems.ADVANCED_ENCHANTS_BOOK);
                            entries.add(ModItems.RARE_ENCHANTS_BOOK);
                            entries.add(ModItems.PRECIOUS_ENCHANTS_BOOK);
                            entries.add(ModItems.ORDINARY_LEVELUP_BALL);
                            entries.add(ModItems.ADVANCED_LEVELUP_BALL);
                            entries.add(ModItems.RARE_LEVELUP_BALL);
                            entries.add(ModItems.PRECIOUS_LEVELUP_BALL);
                            entries.add(ModItems.LEVEL1_SLOT_BUILDER);
                            entries.add(ModItems.LEVEL2_SLOT_BUILDER);
                            entries.add(ModItems.LEVEL3_SLOT_BUILDER);
                            entries.add(ModItems.DUNGEON1_KEY);
                            entries.add(ModItems.DUNGEON2_KEY);
                            entries.add(ModItems.DUNGEON3_KEY);
                            entries.add(ModItems.DUNGEON4_KEY);
                            entries.add(ModItems.DUNGEON5_KEY);
                            display_context.lookup().getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent(enchant -> {
                                ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
                                stack.addEnchantment(enchant.getOrThrow(ModEnchantments.SOUL_BIND), 1);
                                entries.add(stack);
                                for (RegistryKey<Enchantment> key : ModEnchantments.ADVANCED_ENCHANTMENTS) {
                                    ItemStack scrollStack = new ItemStack(ModEnchantments.getEnchantmentLevelType(key).scroll);
                                    RegistryEntry<Enchantment> enchantEntry = enchant.getOrThrow(key);
                                    scrollStack.addEnchantment(enchantEntry, enchantEntry.value().getMaxLevel());
                                    entries.add(scrollStack);
                                }
                            });
                        }).build());
        AdvancedEnchantment.LOGGER.info("Loading ItemGroups successfully!");
    }
}