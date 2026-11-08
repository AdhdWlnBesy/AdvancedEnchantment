package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.entity.FriendlyBlaze;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record BlazeGuard() implements ArmorHitEnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.BLAZE_GUARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, 0.1f * (level + 1));
    }

    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot s, float damage) {
        FriendlyBlaze blaze = new FriendlyBlaze(player.getWorld(), attacker);
        blaze.refreshPositionAndAngles(attacker.getPos(), 0, 0);
        blaze.setCanPickUpLoot(false);
        blaze.setCustomName(Objects.requireNonNull(player.getDisplayName()).copy().append(Text.translatable("chat.blaze_guard")));
        blaze.setCustomNameVisible(true);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            blaze.setEquipmentDropChance(slot, 0.0F);
        }
        player.getWorld().spawnEntity(blaze);
    }
}
