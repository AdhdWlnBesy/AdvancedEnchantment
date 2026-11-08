package com.advanced_enchants.enchantment.pickaxe;

import com.advanced_enchants.enchantment.effect.PickaxeEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public record QuickDigging() implements PickaxeEnchantmentEffect {
    @Override
    public void apply(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, int level) {
        int miningLevel = switch (level) {
            case 1 -> 1;
            case 2, 3 -> 2;
            case 4 -> 3;
            default -> 10;
        };
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 60, miningLevel));
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.QUICK_DIGGING;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, switch (level) {
            case 1, 2 -> 0.2f;
            case 3, 4 -> 0.4f;
            default -> 1;
        });
    }

    @Override
    public boolean shouldSendPacket() {
        return true;
    }
}
