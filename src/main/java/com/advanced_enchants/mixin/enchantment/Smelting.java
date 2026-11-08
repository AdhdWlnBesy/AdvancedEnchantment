package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 熔炼附魔效果实现，通过修改掉落物品栈，查询熔炉烧制列表来实现
 */
@Mixin(Block.class)
public class Smelting {
    @Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void mainEffect(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (!(entity instanceof PlayerEntity player)) return;
        if (player.isCreative()) return;
        List<ItemStack> items = cir.getReturnValue();
        if (items.isEmpty()) return;
        if (!ModAPI.hasEnchantment(ModEnchantments.SMELTING, world.getServer(), player.getMainHandStack())
                || ModAPI.hasEnchantment(Enchantments.SILK_TOUCH, world.getServer(), player.getMainHandStack())
                || ModAPI.hasEnchantment(Enchantments.SILK_TOUCH, world.getServer(), player.getOffHandStack())) return;

        if (world.getRegistryManager() == null) return;
        List<ItemStack> smeltedResultDrops = new ArrayList<>(items.size());
        for (ItemStack rawDrop : items) {
            if (rawDrop.isOf(Items.NETHERRACK)) {
                smeltedResultDrops.add(rawDrop.copy());
                continue;
            }
            if (rawDrop.isEmpty()) continue;
            SingleStackRecipeInput recipeInput = new SingleStackRecipeInput(rawDrop);
            Optional<SmeltingRecipe> smeltingMatch = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, recipeInput, world).map(RecipeEntry::value);
            if (smeltingMatch.isPresent()) {
                ItemStack smeltOutput = smeltingMatch.get().craft(recipeInput, world.getRegistryManager()).copy();
                smeltOutput.setCount(rawDrop.getCount());
                smeltedResultDrops.add(smeltOutput);
            } else {
                smeltedResultDrops.add(rawDrop.copy());
            }
        }
        cir.setReturnValue(smeltedResultDrops);
    }
}