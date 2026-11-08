package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.entity.FriendlyCreeper;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record CreeperGuard() implements ArmorHitEnchantmentEffect {
    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        for (int i = 0; i < 1; i++) {
            CreeperEntity creeper = new FriendlyCreeper(player.getWorld(), attacker);
            creeper.refreshPositionAndAngles(attacker.getPos(), 0, 0);
            creeper.setHealth(player.getHealth() / 2);
            creeper.setCustomName(Objects.requireNonNull(player.getDisplayName()).copy().append(Text.translatable("chat.creeper_guard")));
            creeper.setCustomNameVisible(true);
            player.getWorld().spawnEntity(creeper);
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.CREEPER_GUARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, (level > 1) ? 1 : 0.5f);
    }
}
