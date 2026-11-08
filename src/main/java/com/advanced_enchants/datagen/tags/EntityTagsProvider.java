package com.advanced_enchants.datagen.tags;

import com.advanced_enchants.register.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * 实体标签生成器。
 *
 * @see ModTags
 */
public class EntityTagsProvider extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.CREEPER).add(EntityType.CREEPER);
        getOrCreateTagBuilder(ModTags.ZOMBIE_SKELETON)
                .add(EntityType.ZOMBIE_VILLAGER)
                .add(EntityType.ZOMBIE)
                .add(EntityType.HUSK)
                .add(EntityType.DROWNED)
                .add(EntityType.SKELETON)
                .add(EntityType.STRAY)
                .add(EntityType.BOGGED)
                .add(EntityType.WITHER_SKELETON);
        getOrCreateTagBuilder(ModTags.SPIDER)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER);
        getOrCreateTagBuilder(ModTags.ENDER_MOB)
                .add(EntityType.ENDERMAN)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.ENDERMITE)
                .add(EntityType.SHULKER);
        getOrCreateTagBuilder(ModTags.NETHER_MOB)
                .add(EntityType.BLAZE)
                .add(EntityType.GHAST)
                .add(EntityType.MAGMA_CUBE)
                .add(EntityType.HOGLIN)
                .add(EntityType.ZOGLIN)
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.WITHER);

        getOrCreateTagBuilder(ModTags.MONSTER)
                .addOptionalTag(ModTags.OVERWORLD)
                .addOptionalTag(ModTags.NETHER_MOB)
                .addOptionalTag(ModTags.ENDER_MOB);

        getOrCreateTagBuilder(ModTags.OVERWORLD)
                .addOptionalTag(ModTags.ZOMBIE_SKELETON)
                .addOptionalTag(ModTags.CREEPER)
                .add(EntityType.WITCH)
                .addOptionalTag(ModTags.SPIDER)
                .add(EntityType.PHANTOM)
                .add(EntityType.SLIME)
                .add(EntityType.SILVERFISH)
                .add(EntityType.GUARDIAN)
                .add(EntityType.ELDER_GUARDIAN)
                .add(EntityType.PILLAGER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.EVOKER)
                .add(EntityType.VEX)
                .add(EntityType.RAVAGER)
                .add(EntityType.BREEZE)
                .add(EntityType.WARDEN);
    }
}
