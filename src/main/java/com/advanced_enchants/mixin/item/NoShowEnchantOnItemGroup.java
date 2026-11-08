package com.advanced_enchants.mixin.item;

import com.advanced_enchants.register.ModEnchantments;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

/**
 * 物品组中隐藏附魔书的 Mixin。
 *
 * @see com.advanced_enchants.register.ModItemGroups
 */
@Mixin(ItemGroups.class)
public class NoShowEnchantOnItemGroup {
    @ModifyExpressionValue(method = "addMaxLevelEnchantedBooks", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/RegistryWrapper;streamEntries()Ljava/util/stream/Stream;"))
    private static Stream<RegistryEntry.Reference<Enchantment>> filterMaxLevelBooks(Stream<RegistryEntry.Reference<Enchantment>> original) {
        return original.filter(entry -> !ModEnchantments.ADVANCED_ENCHANTMENTS.contains(entry.getKey().orElse(null)));
    }

    @ModifyExpressionValue(method = "addAllLevelEnchantedBooks", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/RegistryWrapper;streamEntries()Ljava/util/stream/Stream;"))
    private static Stream<RegistryEntry.Reference<Enchantment>> filterAllLevelBooks(Stream<RegistryEntry.Reference<Enchantment>> original) {
        return original.filter(entry -> !ModEnchantments.ADVANCED_ENCHANTMENTS.contains(entry.getKey().orElse(null)));
    }
}
