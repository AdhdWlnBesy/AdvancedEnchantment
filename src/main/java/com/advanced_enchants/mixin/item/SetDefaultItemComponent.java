package com.advanced_enchants.mixin.item;

import com.advanced_enchants.register.ModDataComponentTypes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 设置物品默认组件的 Mixin。
 *
 * @see com.advanced_enchants.register.ModDataComponentTypes
 */
@Mixin(Item.Settings.class)
public abstract class SetDefaultItemComponent {
    @ModifyReturnValue(method = "getComponents", at = @At("RETURN"))
    private ComponentMap mainEffect(ComponentMap original) {
        ComponentMap.Builder builder = ComponentMap.builder();
        builder.addAll(original);
        builder.add(ModDataComponentTypes.ADVANCED_SLOTS, 4);
        return builder.build();
    }
}
