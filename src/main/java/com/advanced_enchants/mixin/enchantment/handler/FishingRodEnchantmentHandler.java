package com.advanced_enchants.mixin.enchantment.handler;

import com.advanced_enchants.enchantment.effect.FishingRodEnchantmentEffect;
import com.advanced_enchants.enchantment.fishing_rod.FireHook;
import com.advanced_enchants.enchantment.fishing_rod.PoisonHook;
import com.advanced_enchants.enchantment.fishing_rod.SharpHook;
import com.advanced_enchants.enchantment.fishing_rod.WitherHook;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(FishingBobberEntity.class)
public abstract class FishingRodEnchantmentHandler {
    @Shadow
    public abstract PlayerEntity getPlayerOwner();

    @Unique
    private static final List<FishingRodEnchantmentEffect> EFFECTS = Arrays.asList(
            new FireHook(), new WitherHook(), new PoisonHook(), new SharpHook()
    );

    @Inject(method = "updateHookedEntityId", at = @At("HEAD"))
    private void mainEffect(Entity entity, CallbackInfo ci) {
        PlayerEntity player = getPlayerOwner();
        if (player == null || entity.getWorld().isClient()) return;
        if (!(entity instanceof LivingEntity victim)) return;
        for (FishingRodEnchantmentEffect effect : EFFECTS) {
            RegistryEntry<Enchantment> entry = effect.getEnchantment(player.getServer());
            if (entry == null) continue;
            int level = EnchantmentHelper.getLevel(entry, player.getMainHandStack());
            if (level > 0 && effect.getChance(level).getChance(level) > player.getRandom().nextFloat()) {
                effect.apply(player, victim, level);
                effect.sendPacket((ServerPlayerEntity) player, entry, level);
            }
        }
    }
}
