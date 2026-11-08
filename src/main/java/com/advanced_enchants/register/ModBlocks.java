package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.block.AdvancedAnvilBlock;
import com.advanced_enchants.block.AdvancedGrindstoneBlock;
import com.advanced_enchants.block.AdvancedSmithingTableBlock;
import com.advanced_enchants.block.broadcast.DungeonBroadcastBlock;
import com.advanced_enchants.block.spawner.DungeonSpawnerBlock;
import com.advanced_enchants.block.vault.DungeonVaultBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * 模组方块注册。
 *
 * @see com.advanced_enchants.register.ModItems
 */
public class ModBlocks {
    public static final Block ADVANCED_ENCHANTS_SMITHING_TABLE = registerBlocks("advanced_enchants_smithing_table", new AdvancedSmithingTableBlock());
    public static final Block ADVANCED_ENCHANTS_ANVIL = registerBlocks("advanced_enchants_anvil", new AdvancedAnvilBlock());
    public static final Block ADVANCED_ENCHANTS_GRINDSTONE = registerBlocks("advanced_enchants_grindstone", new AdvancedGrindstoneBlock());
    public static final Block DUNGEON_VAULT = registerBlocks("dungeon_vault", new DungeonVaultBlock(AbstractBlock.Settings.create()));
    public static final Block DUNGEON_SPAWNER = registerBlocks("dungeon_spawner", new DungeonSpawnerBlock(AbstractBlock.Settings.create()));
    public static final Block DUNGEON_BROADCAST = registerBlocks("dungeon_broadcast", new DungeonBroadcastBlock(AbstractBlock.Settings.create()));
    public static final Block PEBBLE = registerBlocks("pebble", new FrostedIceBlock(AbstractBlock.Settings.create().nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never)));

    //注册一个方块，并创建对应的方块物品
    private static Block registerBlocks(String id, Block block) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(AdvancedEnchantment.MOD_ID, id), blockItem);
        blockItem.appendBlocks(Item.BLOCK_ITEMS, blockItem);
        return Registry.register(Registries.BLOCK, Identifier.of(AdvancedEnchantment.MOD_ID, id), block);
    }

    public static void loadBlocks() {
        AdvancedEnchantment.LOGGER.info("Loading Blocks successfully!");
    }
}