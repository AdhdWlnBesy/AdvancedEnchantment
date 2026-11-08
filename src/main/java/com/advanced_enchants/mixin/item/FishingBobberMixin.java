package com.advanced_enchants.mixin.item;

import com.advanced_enchants.register.ModEnchantments;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 鱼钩行为的 Mixin。
 *
 * @see com.advanced_enchants.enchantment.fishing_rod.SharpHook
 */
@Mixin(FishingBobberEntity.class)
public class FishingBobberMixin {
    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
    private ObjectArrayList<ItemStack> onUse(ObjectArrayList<ItemStack> original) {
        original.removeIf(stack -> ModEnchantments.getAdvancedEnchantmentsCount(EnchantmentHelper.getEnchantments(stack).getEnchantments()) > 0);
        return original;
    }
}
