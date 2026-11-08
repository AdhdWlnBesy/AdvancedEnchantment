package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.entity.FriendlyZombie;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record ZombieGuard() implements ArmorHitEnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.ZOMBIE_GUARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, (level == 1) ? 0.1f : 0.15f);
    }

    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot s, float damage) {
        if (player.getHealth() < 8) return;
        for (int i = 0; i < ((level > 1) ? 2 : 1); i++) {
            FriendlyZombie zombie = new FriendlyZombie(player.getWorld(), attacker);
            zombie.refreshPositionAndAngles(attacker.getPos(), 0, 0);
            zombie.setCanPickUpLoot(false);
            zombie.setHealth(player.getHealth() - 8);
            zombie.setCustomName(Objects.requireNonNull(player.getDisplayName()).copy().append(Text.translatable("chat.zombie_guard")));
            zombie.setCustomNameVisible(true);
            zombie.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 0));
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                zombie.setEquipmentDropChance(slot, 0.0F);
            }
            player.getWorld().spawnEntity(zombie);
        }
    }
}
