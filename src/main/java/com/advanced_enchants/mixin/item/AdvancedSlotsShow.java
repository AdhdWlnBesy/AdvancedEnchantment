package com.advanced_enchants.mixin.item;

import com.advanced_enchants.gui.SmithingScreenHandler;
import com.advanced_enchants.register.ModDataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * 高级附魔槽位显示的 Mixin。
 *
 * @see ModScreenHandler
 */
@Mixin(value = {Item.class, ShieldItem.class})
public class AdvancedSlotsShow {
    @Inject(method = "appendTooltip", at = @At("TAIL"))
    private void mainEffect(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        if (SmithingScreenHandler.canEnchanted(stack)) {
            tooltip.add(Text.translatable("item.advanced_slot", ModDataComponentTypes.getAllSlots(stack), ModDataComponentTypes.getSlots(stack)));
        }
    }
}
