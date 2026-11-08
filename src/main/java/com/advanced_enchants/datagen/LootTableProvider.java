package com.advanced_enchants.datagen;

import com.advanced_enchants.register.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * 战利品表生成器。
 *
 * @see com.advanced_enchants.event.VaultLottery
 */
public class LootTableProvider extends FabricBlockLootTableProvider {
    public LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.ADVANCED_ENCHANTS_ANVIL);
        addDrop(ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE);
        addDrop(ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE);
    }
}
