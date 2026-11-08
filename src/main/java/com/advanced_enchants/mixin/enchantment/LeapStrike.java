package com.advanced_enchants.mixin.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class LeapStrike {
    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 2)
    private boolean modifyBl3(boolean bl3) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getServer() != null) {
            RegistryEntry<Enchantment> entry = new com.advanced_enchants.enchantment.weapon.LeapStrike().getEnchantment(player.getServer());
            int level = EnchantmentHelper.getLevel(entry, player.getMainHandStack());
            if (player.getRandom().nextFloat() < new com.advanced_enchants.enchantment.weapon.LeapStrike().getChance(level).getChance(level)) {
                new com.advanced_enchants.enchantment.weapon.LeapStrike().sendPacket((ServerPlayerEntity) player, entry, level);
                return true;
            }
        }
        return bl3;
    }
}
