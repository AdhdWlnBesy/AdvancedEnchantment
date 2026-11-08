package com.advanced_enchants.datagen.tags;

import com.advanced_enchants.register.ModBlocks;
import com.advanced_enchants.register.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

/**
 * 方块标签生成器。
 *
 * @see com.advanced_enchants.register.ModTags
 */
public class BlocksTagsProvider extends FabricTagProvider.BlockTagProvider {
    public BlocksTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.ADVANCED_ENCHANTS_ANVIL)
                .add(ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE)
                .add(ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ADVANCED_ENCHANTS_ANVIL)
                .add(ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE)
                .add(ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE);
        getOrCreateTagBuilder(ModTags.ORES)
                .addOptionalTag(BlockTags.COAL_ORES)
                .addOptionalTag(BlockTags.IRON_ORES)
                .addOptionalTag(BlockTags.COPPER_ORES)
                .addOptionalTag(BlockTags.GOLD_ORES)
                .addOptionalTag(BlockTags.DIAMOND_ORES)
                .addOptionalTag(BlockTags.EMERALD_ORES)
                .addOptionalTag(BlockTags.LAPIS_ORES)
                .addOptionalTag(BlockTags.REDSTONE_ORES);
        getOrCreateTagBuilder(ModTags.ORE_BLOCK)
                .add(Blocks.IRON_BLOCK)
                .add(Blocks.GOLD_BLOCK)
                .add(Blocks.EMERALD_BLOCK)
                .add(Blocks.DIAMOND_BLOCK)
                .add(Blocks.NETHERITE_BLOCK);
    }
}
