package com.advanced_enchants.datagen;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.event.SimpleCriterion;
import com.advanced_enchants.register.ModBlocks;
import com.advanced_enchants.register.ModItems;
import com.advanced_enchants.register.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 进度数据生成器。
 *
 * @see com.advanced_enchants.event.SimpleCriterion
 */
public class AdvancementProvider extends FabricAdvancementProvider {
    public AdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    public static final SimpleCriterion SIMPLE_TRIGGER = Criteria.register("simple_trigger", new SimpleCriterion());
    private static Consumer<AdvancementEntry> consumer;

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> Entryconsumer) {
        consumer = Entryconsumer;
        AdvancementEntry root = Advancement.Builder.create().display(
                        ModItems.ADVANCED_ENCHANTS_COMPENDIUM.getDefaultStack(),
                        Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + "root"), Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + "root_description"),
                        Identifier.ofVanilla("textures/block/polished_diorite.png"),
                        AdvancementFrame.TASK, false, false, false
                ).criterion("compendium", InventoryChangedCriterion.Conditions.items(ModItems.ADVANCED_ENCHANTS_COMPENDIUM))
                .build(consumer, "advanced_enchantments");

        AdvancementEntry advanced_smithing_table = getItemRegister(ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE.asItem().getDefaultStack(), "smithing_table", root);
        AdvancementEntry smithing_success = register(new ItemStack(ModItems.ORDINARY_ENCHANTS_SCROLL), "smithing_success", AdvancementFrame.TASK, advanced_smithing_table);
        AdvancementEntry smithing_fail = register(new ItemStack(Items.BARRIER), "smithing_fail", AdvancementFrame.TASK, advanced_smithing_table);
        register(new ItemStack(ModItems.PRECIOUS_ENCHANTS_SCROLL), "smithing_precious_fail", AdvancementFrame.GOAL, smithing_fail);
        register(new ItemStack(Items.SKELETON_SKULL), "smithing_multiple_fail", AdvancementFrame.CHALLENGE, smithing_fail);
        ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
        ItemStack stack1 = new ItemStack(Items.NETHERITE_CHESTPLATE);
        registryLookup.getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent(enchant -> {
            stack.addEnchantment(enchant.getOrThrow(Enchantments.SHARPNESS), 1);
            stack1.addEnchantment(enchant.getOrThrow(Enchantments.PROTECTION), 1);
        });
        AdvancementEntry smithing_success_full = register(stack, "smithing_success_full", AdvancementFrame.GOAL, smithing_success);
        register(stack1, "smithing_success_all_full", AdvancementFrame.CHALLENGE, smithing_success_full);

        AdvancementEntry advanced_anvil = getItemRegister(ModBlocks.ADVANCED_ENCHANTS_ANVIL.asItem().getDefaultStack(), "advanced_anvil", root);
        register(new ItemStack(ModItems.ORDINARY_LEVELUP_BALL), "levelup_enchant", AdvancementFrame.TASK, advanced_anvil);
        AdvancementEntry slot_builder_levelup = register(new ItemStack(ModItems.LEVEL1_SLOT_BUILDER), "slot_builder_levelup", AdvancementFrame.GOAL, advanced_anvil);
        register(new ItemStack(ModItems.LEVEL3_SLOT_BUILDER), "slot_builder_levelup_full", AdvancementFrame.CHALLENGE, slot_builder_levelup);
        AdvancementEntry advanced_grindstone = getItemRegister(ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE.asItem().getDefaultStack(), "advanced_grindstone", root);
        AdvancementEntry empty_scroll = getItemRegister(ModItems.EMPTY_SCROLL.getDefaultStack(), "empty_scroll", advanced_grindstone);
        register(ModItems.ENCHANTED_SCROLL.getDefaultStack(), "enchanted_enchants_scroll", AdvancementFrame.GOAL, empty_scroll);
        register(ModItems.ADVANCED_ENCHANTS_BOOK.getDefaultStack(), "reset_enchants_scroll", AdvancementFrame.TASK, advanced_grindstone);

        RegistryWrapper<Structure> structureWrapper = registryLookup.getWrapperOrThrow(RegistryKeys.STRUCTURE);
        AdvancementEntry dungeon = Advancement.Builder.create().parent(root).display(ModBlocks.DUNGEON_SPAWNER.asItem().getDefaultStack(),
                        Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + "dungeon"), Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + "dungeon_description"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("dungeon", TickCriterion.Conditions.createLocation(LocationPredicate.Builder.create().structure(
                        structureWrapper.getOrThrow(ModTags.DUNGEONS)))).build(consumer, "dungeon");
        AdvancementEntry dungeon_win = register(Items.IRON_AXE.getDefaultStack(), "dungeon_win", AdvancementFrame.TASK, dungeon);
        AdvancementEntry dungeon_win_level3 = register(Items.DIAMOND_AXE.getDefaultStack(), "dungeon_win_level3", AdvancementFrame.TASK, dungeon_win);
        register(Items.NETHERITE_AXE.getDefaultStack(), "dungeon_win_level5", AdvancementFrame.GOAL, dungeon_win_level3);
        AdvancementEntry vault = register(ModBlocks.DUNGEON_VAULT.asItem().getDefaultStack(), "vault", AdvancementFrame.TASK, dungeon_win);
        register(ModBlocks.DUNGEON_VAULT.asItem().getDefaultStack(), "vault_high", AdvancementFrame.GOAL, vault);

        AdvancementEntry mob_levelup = register(Blocks.ZOMBIE_HEAD.asItem().getDefaultStack(), "mob_levelup", AdvancementFrame.TASK, root);
        AdvancementEntry mob_levelup2 = register(Blocks.SKELETON_SKULL.asItem().getDefaultStack(), "mob_levelup2", AdvancementFrame.TASK, mob_levelup);
        register(Blocks.WITHER_SKELETON_SKULL.asItem().getDefaultStack(), "mob_levelup3", AdvancementFrame.GOAL, mob_levelup2);
    }

    private static AdvancementEntry register(ItemStack icon, String id, AdvancementFrame frame, AdvancementEntry parent) {
        return Advancement.Builder.create().parent(parent).display(icon, Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + id)
                        , Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + id + "_description"), null, frame, true, true, false)
                .criterion(id, SimpleCriterion.Conditions.create(id)).build(consumer, id);
    }

    private static AdvancementEntry getItemRegister(ItemStack icon, String id, AdvancementEntry parent) {
        return Advancement.Builder.create().parent(parent).display(icon, Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + id)
                        , Text.translatable("advancements." + AdvancedEnchantment.MOD_ID + id + "_description"), null, AdvancementFrame.TASK, true, true, false)
                .criterion(id, InventoryChangedCriterion.Conditions.items(icon.getItem())).build(consumer, id);
    }

    public static void loadCriteria() {
        AdvancedEnchantment.LOGGER.info("Loading criteria successfully!");
    }
}
