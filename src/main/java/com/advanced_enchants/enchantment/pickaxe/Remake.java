package com.advanced_enchants.enchantment.pickaxe;

import com.advanced_enchants.enchantment.effect.PickaxeEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public record Remake() implements PickaxeEnchantmentEffect {
    @Override
    public void apply(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, int level) {
        ItemStack stack = player.getMainHandStack();
        stack.setDamage(Math.max(0, stack.getDamage() - 2));
        player.setStackInHand(Hand.MAIN_HAND, stack);
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.REMAKE;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.LINEAR, 0.2f);
    }
}
