package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.entity.FriendlyVindicator;
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

public record VindicatorGuard() implements ArmorHitEnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.VINDICATOR_GUARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.25f);
    }

    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot s, float damage) {
        if (player.getHealth() < 8) return;
        FriendlyVindicator vindicator = new FriendlyVindicator(player.getWorld(), attacker);
        vindicator.refreshPositionAndAngles(attacker.getPos(), 0, 0);
        vindicator.setCanPickUpLoot(false);
        vindicator.setHealth(player.getHealth() - 8);
        vindicator.setCustomName(Objects.requireNonNull(player.getDisplayName()).copy().append(Text.translatable("chat.vindicator_guard")));
        vindicator.setCustomNameVisible(true);
        vindicator.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, -1, 0));
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            vindicator.setEquipmentDropChance(slot, 0.0F);
        }
        player.getWorld().spawnEntity(vindicator);
    }
}
