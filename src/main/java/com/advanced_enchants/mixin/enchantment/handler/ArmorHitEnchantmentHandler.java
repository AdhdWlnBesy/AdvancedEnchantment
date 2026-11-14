package com.advanced_enchants.mixin.enchantment.handler;

import com.advanced_enchants.enchantment.EnchantmentHandler;
import com.advanced_enchants.enchantment.armor.*;
import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class ArmorHitEnchantmentHandler {

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"))
    private void mainEffect(DamageSource source, float amount, CallbackInfo ci) {
        if (((PlayerEntity) (Object) this) instanceof ServerPlayerEntity player) {
            if (player.getAttacker() == null) return;
            for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
                ItemStack armorStack = player.getInventory().getArmorStack(slot.getEntitySlotId());
                if (armorStack.isEmpty()) continue;
                for (ArmorHitEnchantmentEffect effect : EnchantmentHandler.ARMOR_HIT_EFFECTS) {
                    RegistryEntry<Enchantment> entry = effect.getEnchantment(player.getServer());
                    if (entry == null) continue;
                    int level = EnchantmentHelper.getLevel(entry, armorStack);
                    if (level > 0) {
                        float chance = player.getRandom().nextFloat();
                        if (chance < effect.getChance(level).getChance(level)) {
                            effect.apply(player.getAttacker(), player, level, slot, amount);
                            effect.sendPacket(player, entry, level);
                        }
                    }
                }
            }
        }
    }
}
