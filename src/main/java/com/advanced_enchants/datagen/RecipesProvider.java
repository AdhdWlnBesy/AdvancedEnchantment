package com.advanced_enchants.datagen;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.register.ModBlocks;
import com.advanced_enchants.register.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

/**
 * 配方数据生成器。
 *
 * @see com.advanced_enchants.register.ModItems
 */
public class RecipesProvider extends FabricRecipeProvider {
    public RecipesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE)
                .pattern("ABC")
                .pattern("BDB")
                .pattern("EFE")
                .input('A', Ingredient.ofItems(Items.IRON_INGOT))
                .input('B', Ingredient.ofItems(Items.DIAMOND))
                .input('C', Ingredient.ofItems(Items.GOLD_INGOT))
                .input('D', Ingredient.ofItems(Items.SMITHING_TABLE))
                .input('E', Ingredient.ofItems(Items.LAPIS_LAZULI))
                .input('F', Ingredient.ofItems(Items.EMERALD))
                .criterion("has_item", RecipesProvider.conditionsFromItem(Items.SMITHING_TABLE))
                .offerTo(exporter, Identifier.of(AdvancedEnchantment.MOD_ID, "advanced_enchants_smithing_table"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ADVANCED_ENCHANTS_ANVIL)
                .pattern("BAB")
                .pattern("DCD")
                .pattern("EDE")
                .input('A', Items.LEATHER)
                .input('B', Items.DIAMOND)
                .input('C', Items.ANVIL)
                .input('D', Items.LAPIS_LAZULI)
                .input('E', Items.IRON_INGOT)
                .criterion("has_item", RecipesProvider.conditionsFromItem(Items.ANVIL))
                .offerTo(exporter, Identifier.of(AdvancedEnchantment.MOD_ID, "advanced_enchants_anvil"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE)
                .pattern(" A ")
                .pattern("BCB")
                .pattern("DBD")
                .input('A', Items.LAPIS_LAZULI)
                .input('B', Items.DIAMOND)
                .input('C', Items.GRINDSTONE)
                .input('D', Items.IRON_INGOT)
                .criterion("has_item", RecipesProvider.conditionsFromItem(Items.GRINDSTONE))
                .offerTo(exporter, Identifier.of(AdvancedEnchantment.MOD_ID, "advanced_enchants_grindstone"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EMPTY_SCROLL)
                .pattern(" A ")
                .pattern("CBC")
                .pattern("   ")
                .input('A', Items.STICK)
                .input('B', Items.PAPER)
                .input('C', Items.LAPIS_LAZULI)
                .criterion("has_item", RecipesProvider.conditionsFromItem(Items.PAPER))
                .offerTo(exporter, Identifier.of(AdvancedEnchantment.MOD_ID, "empty_scroll"));
    }
}
