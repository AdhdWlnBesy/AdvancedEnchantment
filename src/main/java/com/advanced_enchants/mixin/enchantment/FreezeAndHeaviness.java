package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.enchantment.armor.Heaviness;
import com.advanced_enchants.enchantment.weapon.Freeze;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class FreezeAndHeaviness {
    @Shadow
    public abstract @Nullable DamageSource getRecentDamageSource();

    @ModifyArgs(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"))
    private void mainEffect(Args args) {
        if (getRecentDamageSource() != null && getRecentDamageSource().getAttacker() instanceof LivingEntity attacker) {
            if (attacker.getServer() == null) return;
            RegistryEntry<Enchantment> entry1 = new Freeze().getEnchantment(attacker.getServer());
            int level1 = EnchantmentHelper.getLevel(entry1, attacker.getMainHandStack());
            RegistryEntry<Enchantment> entry2 = new Heaviness().getEnchantment(attacker.getServer());
            int level2 = EnchantmentHelper.getEquipmentLevel(entry2, (LivingEntity) (Object) this);
            double x = args.get(0);
            double y = args.get(1);
            double z = args.get(2);
            if (level1 == 1 || level2 == 1) {
                args.set(0, x * 0.5);
                args.set(1, y * 0.5);
                args.set(2, z * 0.5);
            } else if (level1 == 2 || level2 == 2) {
                args.set(0, 0.0D);
                args.set(1, 0.0D);
                args.set(2, 0.0D);
            }
            if (attacker instanceof ServerPlayerEntity player) {
                if (level1 > 0) {
                    new Freeze().sendPacket(player, entry1, level1);
                }
                if (level2 > 0) {
                    new Heaviness().sendPacket(player, entry2, level2);
                }
            }
        }
    }
}