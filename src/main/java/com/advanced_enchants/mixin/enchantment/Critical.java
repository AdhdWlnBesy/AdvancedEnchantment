package com.advanced_enchants.mixin.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntity.class)
public class Critical {
    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    private float mainEffect(float amount) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getServer() != null) {
            RegistryEntry<Enchantment> entry = new com.advanced_enchants.enchantment.weapon.Critical().getEnchantment(player.getServer());
            int level = EnchantmentHelper.getLevel(entry, player.getMainHandStack());
            boolean critical = player.getAttackCooldownProgress(0.5F) > 0.9F
                    && player.fallDistance > 0.0F
                    && !player.isOnGround()
                    && !player.isClimbing()
                    && !player.isTouchingWater()
                    && !player.hasStatusEffect(StatusEffects.BLINDNESS)
                    && !player.hasVehicle()
                    && !player.isSprinting();
            if (level > 0 && critical) {
                new com.advanced_enchants.enchantment.weapon.Critical().sendPacket((ServerPlayerEntity) player, entry, level);
                return amount * (1 + (float) level / 10);
            }
        }
        return amount;
    }
}
