package com.advanced_enchants.datagen;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.register.ModBlocks;
import com.advanced_enchants.register.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * 模型数据生成器。
 *
 * @see com.advanced_enchants.register.ModItems
 */
public class ModelsProvider extends FabricModelProvider {
    private static BlockStateModelGenerator blockStateModelGenerator;

    public ModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ModelsProvider.blockStateModelGenerator = blockStateModelGenerator;
        Block smithing = ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE;
        Block vault = ModBlocks.DUNGEON_VAULT;
        Block spawner = ModBlocks.DUNGEON_SPAWNER;

        registerStateBlock(Models.CUBE, smithing, new Identifier[]{
                getIdentifier(smithing, BlockModel.BOTTOM),
                getIdentifier(smithing, BlockModel.TOP),
                getIdentifier(smithing, BlockModel.FRONT),
                getIdentifier(smithing, BlockModel.FRONT),
                getIdentifier(smithing, BlockModel.SIDE),
                getIdentifier(smithing, BlockModel.SIDE),
                getIdentifier(smithing, BlockModel.FRONT),
                null, null, null, null
        }, false);
        registerStateBlock(Models.TEMPLATE_VAULT, vault, new Identifier[]{
                null, null, null, null, null, null, null,
                getIdentifier(vault, BlockModel.SIDE),
                getIdentifier(vault, BlockModel.FRONT),
                getIdentifier(vault, BlockModel.BOTTOM),
                getIdentifier(vault, BlockModel.TOP),
        }, true);
        registerStateBlock(Models.TEMPLATE_VAULT, spawner, new Identifier[]{
                null, null, null, null, null, null, null,
                getIdentifier(spawner, BlockModel.SIDE),
                getIdentifier(spawner, BlockModel.FRONT),
                getIdentifier(spawner, BlockModel.BOTTOM),
                getIdentifier(spawner, BlockModel.TOP),
        }, true);
        blockStateModelGenerator.registerStateWithModelReference(ModBlocks.PEBBLE, Blocks.COBBLESTONE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Item key = ModItems.DUNGEON1_KEY;
        itemModelGenerator.register(ModItems.ADVANCED_ENCHANTS_COMPENDIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.ORDINARY_ENCHANTS_BOOK, Models.GENERATED);
        itemModelGenerator.register(ModItems.ADVANCED_ENCHANTS_BOOK, Models.GENERATED);
        itemModelGenerator.register(ModItems.RARE_ENCHANTS_BOOK, Models.GENERATED);
        itemModelGenerator.register(ModItems.PRECIOUS_ENCHANTS_BOOK, Models.GENERATED);
        itemModelGenerator.register(ModItems.ORDINARY_ENCHANTS_SCROLL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ADVANCED_ENCHANTS_SCROLL, Models.GENERATED);
        itemModelGenerator.register(ModItems.RARE_ENCHANTS_SCROLL, Models.GENERATED);
        itemModelGenerator.register(ModItems.EMPTY_SCROLL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENCHANTED_SCROLL, Models.GENERATED);
        itemModelGenerator.register(ModItems.PRECIOUS_ENCHANTS_SCROLL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ORDINARY_LEVELUP_BALL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ADVANCED_LEVELUP_BALL, Models.GENERATED);
        itemModelGenerator.register(ModItems.RARE_LEVELUP_BALL, Models.GENERATED);
        itemModelGenerator.register(ModItems.PRECIOUS_LEVELUP_BALL, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEVEL1_SLOT_BUILDER, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEVEL2_SLOT_BUILDER, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEVEL3_SLOT_BUILDER, Models.GENERATED);
        itemModelGenerator.register(ModItems.DUNGEON1_KEY, key, Models.GENERATED);
        itemModelGenerator.register(ModItems.DUNGEON2_KEY, key, Models.GENERATED);
        itemModelGenerator.register(ModItems.DUNGEON3_KEY, key, Models.GENERATED);
        itemModelGenerator.register(ModItems.DUNGEON4_KEY, key, Models.GENERATED);
        itemModelGenerator.register(ModItems.DUNGEON5_KEY, key, Models.GENERATED);
        itemModelGenerator.register(ModBlocks.DUNGEON_BROADCAST.asItem(), Models.GENERATED);
    }


    private static void registerStateBlock(Model model, Block block, Identifier[] models, boolean isFacing) {
        TextureMap map = new TextureMap();
        TextureKey[] key = new TextureKey[]{TextureKey.DOWN, TextureKey.UP, TextureKey.NORTH, TextureKey.SOUTH, TextureKey.EAST, TextureKey.WEST
                , TextureKey.PARTICLE, TextureKey.SIDE, TextureKey.FRONT, TextureKey.BOTTOM, TextureKey.TOP};
        for (int i = 0; i < Math.min(key.length, models.length); i++) {
            if (models[i] != null) {
                map.put(key[i], models[i]);
            }
        }
        if (isFacing) {
            Identifier identifier = model.upload(block, map.copyAndAdd(TextureKey.FRONT, TextureMap.getId(block)), blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                            BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
                    .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
        } else {
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block,
                    model.upload(getIdentifier(block, BlockModel.NULL), map, blockStateModelGenerator.modelCollector)));
        }
    }

    private static Identifier getIdentifier(Block block, BlockModel blockModel) {
        return Identifier.of(AdvancedEnchantment.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath() + blockModel.id);
    }

    private enum BlockModel {
        BOTTOM("_bottom"),
        TOP("_top"),
        FRONT("_front"),
        SIDE("_side"),
        BASE("_base"),
        NULL("");

        public final String id;

        BlockModel(String id) {
            this.id = id;
        }
    }
}
