package com.advanced_enchants.enchantment.weapon;

import com.advanced_enchants.enchantment.effect.WeaponEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

public record SapEssence() implements WeaponEnchantmentEffect {
    public static final MapCodec<SapEssence> CODEC = MapCodec.unit(SapEssence::new);

    @Override
    public void apply(LivingEntity attacker, LivingEntity victim, int level) {
        if (victim.isDead() && attacker.getWorld() instanceof ServerWorld serverWorld) {
            int amount = victim.getXpToDrop(serverWorld, attacker);
            ExperienceOrbEntity.spawn(serverWorld, victim.getPos(), (int) Math.ceil(amount * switch (level) {
                case 1 -> 0.5f;
                case 3 -> 2;
                default -> 1;
            }));
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SAP_ESSENCE;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @Override
    public boolean shouldSendPacket() {
        return false;
    }
}
