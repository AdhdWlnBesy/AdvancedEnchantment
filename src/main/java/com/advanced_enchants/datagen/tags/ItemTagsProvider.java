package com.advanced_enchants.datagen.tags;

import com.advanced_enchants.register.ModItems;
import com.advanced_enchants.register.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

/**
 * 物品标签生成器。
 *
 * @see com.advanced_enchants.register.ModTags
 */
public class ItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.MINERAL)
                .add(Items.IRON_INGOT)
                .add(Items.GOLD_INGOT)
                .add(Items.DIAMOND)
                .add(Items.EMERALD);
        getOrCreateTagBuilder(ModTags.ADVANCED_ENCHANTS_SCROLL)
                .add(ModItems.ORDINARY_ENCHANTS_SCROLL)
                .add(ModItems.ADVANCED_ENCHANTS_SCROLL)
                .add(ModItems.RARE_ENCHANTS_SCROLL)
                .add(ModItems.PRECIOUS_ENCHANTS_SCROLL)
                .add(ModItems.ENCHANTED_SCROLL);
        getOrCreateTagBuilder(ModTags.LEVELUP_BALL)
                .add(ModItems.ORDINARY_LEVELUP_BALL)
                .add(ModItems.ADVANCED_LEVELUP_BALL)
                .add(ModItems.RARE_LEVELUP_BALL)
                .add(ModItems.PRECIOUS_LEVELUP_BALL);
        getOrCreateTagBuilder(ModTags.SLOT_BUILDER)
                .add(ModItems.LEVEL1_SLOT_BUILDER)
                .add(ModItems.LEVEL2_SLOT_BUILDER)
                .add(ModItems.LEVEL3_SLOT_BUILDER);
        getOrCreateTagBuilder(ModTags.ENCHANTABLE_BOW)
                .add(Items.BOW)
                .add(Items.CROSSBOW);
        getOrCreateTagBuilder(ModTags.ENCHANTABLE_ELYTRA)
                .add(Items.ELYTRA);
        getOrCreateTagBuilder(ModTags.ENCHANTABLE_SHIELD)
                .add(Items.SHIELD);
        getOrCreateTagBuilder(ModTags.BOW_AND_WEAPON)
                .addOptionalTag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .addOptionalTag(ModTags.ENCHANTABLE_BOW);


        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.BOW_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.FISHING_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.CROSSBOW_ENCHANTABLE).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
        getOrCreateTagBuilder(ItemTags.PICKAXES).addOptionalTag(ModTags.ADVANCED_ENCHANTS_SCROLL);
    }
}
