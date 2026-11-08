package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.event.EnchantTrigNetWorking;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntity.class)
public class Gambler {
    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    private float mainEffect(float amount) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getServer() != null) {
            RegistryEntry<Enchantment> entry = new com.advanced_enchants.enchantment.weapon.Gambler().getEnchantment(player.getServer());
            int level = EnchantmentHelper.getLevel(entry, player.getMainHandStack());
            if (level > 0) {
                if (player.getRandom().nextBoolean()) {
                    EnchantTrigNetWorking.sendPacket((ServerPlayerEntity) player, entry.value().description().copy().formatted(Formatting.RED), level);
                    return amount * 0.7f;
                } else {
                    new com.advanced_enchants.enchantment.weapon.Gambler().sendPacket((ServerPlayerEntity) player, entry, level);
                    return amount * 1.5f;
                }
            }
        }
        return amount;
    }
}
