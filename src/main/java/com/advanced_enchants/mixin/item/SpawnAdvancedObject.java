package com.advanced_enchants.mixin.item;

import com.advanced_enchants.register.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 生成高级对象的 Mixin。
 *
 * @see com.advanced_enchants.register.ModItems
 */
@Mixin(EnchantmentScreenHandler.class)
public abstract class SpawnAdvancedObject {
    @Shadow
    @Final
    public int[] enchantmentPower;
    @Shadow
    @Final
    private Inventory inventory;
    @Shadow
    @Final
    private ScreenHandlerContext context;

    @Inject(method = "onButtonClick", at = @At(value = "HEAD"), cancellable = true)
    private void mainEffect(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        if (id >= 0 && id < this.enchantmentPower.length) {
            ItemStack itemStack = this.inventory.getStack(0);
            ItemStack itemStack2 = this.inventory.getStack(1);
            int i = id + 1;
            if ((itemStack2.isEmpty() || itemStack2.getCount() < i) && !player.isInCreativeMode()) {
                cir.setReturnValue(false);
            } else if (this.enchantmentPower[id] <= 0 || itemStack.isEmpty() || (player.experienceLevel < i || player.experienceLevel < this.enchantmentPower[id]) && !player.getAbilities().creativeMode) {
                cir.setReturnValue(false);
            } else {
                this.context.run((world, blockPos) -> {
                    ItemStack outStack = ItemStack.EMPTY;
                    double chance = 1.25 * i - 0.25;
                    boolean change = false;
                    if (player.getRandom().nextFloat() < 0.08 * chance) {
                        outStack = new ItemStack(ModItems.ORDINARY_ENCHANTS_BOOK);
                        change = true;
                    } else if (player.getRandom().nextFloat() < 0.04 * chance) {
                        outStack = new ItemStack(ModItems.ADVANCED_ENCHANTS_BOOK);
                        change = true;
                    } else if (player.getRandom().nextFloat() < 0.02 * chance) {
                        outStack = new ItemStack(ModItems.RARE_ENCHANTS_BOOK);
                        change = true;
                    } else if (player.getRandom().nextFloat() < 0.01 * chance) {
                        outStack = new ItemStack(ModItems.PRECIOUS_ENCHANTS_BOOK);
                        change = true;
                    }
                    if (change) {
                        this.inventory.setStack(0, outStack);
                        world.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                        cir.cancel();
                    }
                });
            }
        }
    }
}
