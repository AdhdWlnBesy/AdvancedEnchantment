package com.advanced_enchants.enchantment.other;

import com.advanced_enchants.enchantment.effect.EnchantmentEffect;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public record Advancing() implements EnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level) {
        if (player.isFallFlying() && MinecraftClient.getInstance().options.useKey.isPressed()) {
            Vec3d vec3d = player.getRotationVector();
            Vec3d vec3d2 = player.getVelocity();
            player.addVelocity(vec3d.x * 0.1 + (vec3d.x * 1.5 - vec3d2.x) * 0.5,
                    vec3d.y * 0.1 + (vec3d.y * 1.5 - vec3d2.y) * 0.5,
                    vec3d.z * 0.1 + (vec3d.z * 1.5 - vec3d2.z) * 0.5);
            player.velocityModified = true;
            ModAPI.playSoundsToPlayer(SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, player);
            player.swingHand(Hand.MAIN_HAND);
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.ADVANCING;
    }
}
