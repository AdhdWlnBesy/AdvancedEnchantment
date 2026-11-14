package com.advanced_enchants.mixin.enchantment.handler;

import com.advanced_enchants.enchantment.EnchantmentHandler;
import com.advanced_enchants.enchantment.effect.ShieldEnchantmentEffect;
import com.advanced_enchants.enchantment.other.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class ShieldEnchantmentHandler {
    @Inject(method = "damageShield", at = @At("TAIL"))
    private void damageShield(float amount, CallbackInfo ci) {
        PlayerEntity victim = (PlayerEntity) (Object) this;
        if (victim.getWorld().isClient) return;
        LivingEntity attacker = victim.getAttacker();
        if (victim.getServer() == null) return;
        for (ShieldEnchantmentEffect effect : EnchantmentHandler.SHIELD_EFFECTS) {
            RegistryEntry<Enchantment> entry = effect.getEnchantment(victim.getServer());
            int level = EnchantmentHelper.getLevel(entry, victim.getOffHandStack());
            int level1 = victim.getMainHandStack().getEnchantments().getLevel(entry);
            if (level > 0 && effect.getChance(level).getChance(level) > victim.getRandom().nextFloat()) {
                effect.apply(victim, attacker, amount, level, Hand.OFF_HAND);
                if (victim instanceof ServerPlayerEntity serverPlayer) {
                    effect.sendPacket(serverPlayer, entry, level);
                }
            }
            if (level1 > 0 && effect.getChance(level1).getChance(level1) > victim.getRandom().nextFloat()) {
                effect.apply(victim, attacker, amount, level1, Hand.MAIN_HAND);
                if (victim instanceof ServerPlayerEntity serverPlayer) {
                    effect.sendPacket(serverPlayer, entry, level1);
                }
            }
        }
    }
}
