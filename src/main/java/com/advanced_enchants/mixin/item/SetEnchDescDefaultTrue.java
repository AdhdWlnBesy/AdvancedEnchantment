package com.advanced_enchants.mixin.item;

import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 附魔描述默认显示的 Mixin
 */
@Mixin(targets = "net.darkhax.enchdesc.common.impl.EnchdescMod", remap = false)
public class SetEnchDescDefaultTrue {
    @Inject(method = "isKeybindConditionMet", at = @At("RETURN"), cancellable = true)
    private void mainEvent(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(Screen.hasShiftDown());
    }
}
