package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.entity.FriendlyPiglin;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record PiglinGuard() implements ArmorHitEnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.PIGLIN_GUARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, (level == 1) ? 0.1f : 0.15f);
    }

    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot s, float damage) {
        if (player.getHealth() < 8) return;
        for (int i = 0; i < level; i++) {
            FriendlyPiglin piglin = new FriendlyPiglin(player.getWorld(), attacker);
            piglin.refreshPositionAndAngles(attacker.getPos(), 0, 0);
            piglin.setCanPickUpLoot(false);
            piglin.setHealth(player.getHealth() - 8);
            piglin.setCustomName(Objects.requireNonNull(player.getDisplayName()).copy().append(Text.translatable("chat.piglin_guard")));
            piglin.setCustomNameVisible(true);
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                piglin.setEquipmentDropChance(slot, 0.0F);
            }
            player.getWorld().spawnEntity(piglin);
        }
    }
}
