package com.advanced_enchants;

import com.advanced_enchants.datagen.*;
import com.advanced_enchants.datagen.tags.BlocksTagsProvider;
import com.advanced_enchants.datagen.tags.EntityTagsProvider;
import com.advanced_enchants.datagen.tags.ItemTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

/**
 * 数据生成入口，汇总各数据生成器。
 *
 * @see com.advanced_enchants.datagen.AdvancementProvider
 */
public class AdvancedEnchantmentDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(WorldProvider::new);
        pack.addProvider(EnusProvider::new);
        pack.addProvider(ZhcnProvider::new);
        pack.addProvider(ModelsProvider::new);
        pack.addProvider(LootTableProvider::new);
        pack.addProvider(BlocksTagsProvider::new);
        pack.addProvider(ItemTagsProvider::new);
        pack.addProvider(EntityTagsProvider::new);
        pack.addProvider(RecipesProvider::new);
        pack.addProvider(AdvancementProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, EnchantmentProvider::bootstrap);
    }
}
