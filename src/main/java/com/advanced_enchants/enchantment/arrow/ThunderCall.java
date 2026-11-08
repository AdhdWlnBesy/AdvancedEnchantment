package com.advanced_enchants.enchantment.arrow;

import com.advanced_enchants.enchantment.effect.BowEnchantmentEffect;
import com.advanced_enchants.entity.duckInterface.NoFireThunder;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public record ThunderCall() implements BowEnchantmentEffect {
    @Override
    public void apply(ServerPlayerEntity player, int level, LivingEntity target, ArrowEntity arrow, float amount) {
        Entity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, target.getWorld());
        lightning.refreshPositionAfterTeleport(target.getPos());
        ((NoFireThunder) lightning).advancedEnchantment$setNoFire();
        target.getWorld().spawnEntity(lightning);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.THUNDER_CALL;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.3f);
    }
}
