package com.advanced_enchants.mixin.enchantment.handler;

import com.advanced_enchants.enchantment.arrow.*;
import com.advanced_enchants.enchantment.effect.BowEnchantmentEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Arrays;
import java.util.List;

/**
 * 弓的附魔效果处理类，当箭射中目标触发
 *
 * @see BowEnchantmentEffect
 */
@Mixin(PersistentProjectileEntity.class)
public class BowEnchantmentHandler {
    @Unique
    private static final List<BowEnchantmentEffect> EFFECTS = Arrays.asList(
            new WitherArrow(), new Palsy(), new Rime(), new com.advanced_enchants.enchantment.arrow.ThunderCall(), new Bombard(), new PoisonArrow(),
            new Reforge(), new Laches(), new Finisher()
    );

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;onHit(Lnet/minecraft/entity/LivingEntity;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    protected void mainEffect(EntityHitResult entityHitResult, CallbackInfo ci, Entity entity, float f, double d, Entity entity2, DamageSource damageSource, int i, boolean bl, int j, LivingEntity livingEntity2) {
        if (!(((PersistentProjectileEntity) (Object) this) instanceof ArrowEntity arrow)) return;
        if (arrow.getWorld().isClient) return;
        if (!(arrow.getOwner() instanceof ServerPlayerEntity player)) return;
        for (BowEnchantmentEffect effect : EFFECTS) {
            RegistryEntry<Enchantment> entry = effect.getEnchantment(player.getServer());
            if (entry == null) continue;
            int level = EnchantmentHelper.getLevel(entry, player.getMainHandStack());
            if (level > 0 && effect.getChance(level).getChance(level) > player.getRandom().nextFloat()) {
                effect.apply(player, level, livingEntity2, arrow, i);
                effect.sendPacket(player, entry, level);
            }
        }
    }
}
