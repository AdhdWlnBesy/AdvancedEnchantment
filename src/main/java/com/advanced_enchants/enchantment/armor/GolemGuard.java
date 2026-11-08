package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.entity.FriendlyGolem;
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

public record GolemGuard() implements ArmorHitEnchantmentEffect {
    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot slot, float damage) {
        for (int i = 0; i < ((level > 3) ? 2 : 1); i++) {
            FriendlyGolem golem = new FriendlyGolem(player.getWorld(), attacker);
            golem.refreshPositionAndAngles(attacker.getPos(), 0, 0);
            golem.setCustomName(Objects.requireNonNull(player.getDisplayName()).copy().append(Text.translatable("chat.golem_guard")));
            golem.setCustomNameVisible(true);
            if (level > 1) {
                golem.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, 0));
            }
            if (level > 2) {
                golem.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, 0));
            }
            if (level > 2) {
                golem.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, 0));
            }
            player.getWorld().spawnEntity(golem);
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.GOLEM_GUARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, (level > 1) ? 0.1f : 0.05f);
    }
}
