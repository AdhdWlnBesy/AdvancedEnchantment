package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.mixin.other.SetStatusEffectDuration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MagicWard {
    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"))
    private void mainEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getServer() != null) {
            int level = 0;
            RegistryEntry<Enchantment> entry = new com.advanced_enchants.enchantment.armor.MagicWard().getEnchantment(entity.getServer());
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                int temp = entity.getEquippedStack(slot).getEnchantments().getLevel(entry);
                if (temp > level) level = temp;
            }
            if (level > 0) {
                int duration = effect.getDuration();
                if (duration < 0) return;
                switch (effect.getEffectType().value().getCategory()) {
                    case HARMFUL -> duration -= MathHelper.floor(duration * level * 0.125f);
                    case BENEFICIAL -> duration += MathHelper.floor(duration * level * 0.125f);
                }
                ((SetStatusEffectDuration) effect).setDuration(duration);
            }
        }
    }
}
