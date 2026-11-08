package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;

public record Recast() implements ArmorHitEnchantmentEffect {
    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        ItemStack armor = player.getInventory().getArmorStack(slot.getEntitySlotId());
        if (!armor.isDamageable() || armor.getDamage() <= 0) return;
        armor.setDamage(Math.min(0, armor.getDamage() - 4));
        player.getInventory().armor.set(slot.getEntitySlotId(), armor);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.RECAST;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.1f);
    }

    @Override
    public boolean shouldSendPacket() {
        return false;
    }
}
