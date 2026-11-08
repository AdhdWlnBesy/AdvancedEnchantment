package com.advanced_enchants.mixin.item;

import com.advanced_enchants.register.ModDataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 重置高级附魔槽位的 Mixin。
 *
 * @see com.advanced_enchants.mixin.item.AdvancedSlotsShow
 */
@Mixin(GrindstoneScreenHandler.class)
public class ResetAdvancedSlots {
    @Inject(method = "grind", at = @At("TAIL"))
    private void mainEffect(ItemStack item, CallbackInfoReturnable<ItemStack> cir) {
        item.set(ModDataComponentTypes.ADVANCED_SLOTS, ModDataComponentTypes.getAllSlots(item));
    }
}
