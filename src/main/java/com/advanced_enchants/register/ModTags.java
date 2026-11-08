package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

/**
 * 模组标签注册。
 *
 * @see ModBlocks
 */
public class ModTags {
    public static final TagKey<Item> ADVANCED_ENCHANTS_SCROLL = itemOf("advanced_enchants_scroll");
    public static final TagKey<Item> LEVELUP_BALL = itemOf("levelup_ball");
    public static final TagKey<Item> SLOT_BUILDER = itemOf("slot_builder");
    public static final TagKey<Item> MINERAL = itemOf("mineral");
    public static final TagKey<Item> ENCHANTABLE_BOW = itemOf("enchantable_bow");
    public static final TagKey<Item> ENCHANTABLE_ELYTRA = itemOf("elytra");
    public static final TagKey<Item> ENCHANTABLE_SHIELD = itemOf("shield");
    public static final TagKey<Item> BOW_AND_WEAPON = itemOf("bow_and_weapon");
    public static final TagKey<EntityType<?>> CREEPER = entityOf("creeper");
    public static final TagKey<EntityType<?>> ZOMBIE_SKELETON = entityOf("zombie_skeleton");
    public static final TagKey<EntityType<?>> ENDER_MOB = entityOf("ender_mob");
    public static final TagKey<EntityType<?>> NETHER_MOB = entityOf("nether_mob");
    public static final TagKey<EntityType<?>> SPIDER = entityOf("spider");
    public static final TagKey<EntityType<?>> MONSTER = entityOf("monster");
    public static final TagKey<EntityType<?>> OVERWORLD = entityOf("overworld");
    public static final TagKey<Block> ORES = blockOf("ores");
    public static final TagKey<Block> ORE_BLOCK = blockOf("ore_block");
    public static final TagKey<Structure> DUNGEONS = TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(AdvancedEnchantment.MOD_ID, "dungeons"));

    private static TagKey<Item> itemOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(AdvancedEnchantment.MOD_ID, id));
    }

    private static TagKey<EntityType<?>> entityOf(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(AdvancedEnchantment.MOD_ID, id));
    }

    private static TagKey<Block> blockOf(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(AdvancedEnchantment.MOD_ID, id));
    }

    public static void loadTags() {
        AdvancedEnchantment.LOGGER.info("Loading Tags successfully!");
    }
}
