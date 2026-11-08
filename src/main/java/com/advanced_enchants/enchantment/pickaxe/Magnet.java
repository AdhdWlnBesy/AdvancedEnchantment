package com.advanced_enchants.enchantment.pickaxe;

import com.advanced_enchants.enchantment.effect.PickaxeEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public record Magnet() implements PickaxeEnchantmentEffect {
    @Override
    public void apply(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, int level) {
        world.getEntitiesByClass(ItemEntity.class, new Box(pos), itemEntity -> true).forEach(itemEntity -> {
            if (player.getInventory().getEmptySlot() == -1) return;
            player.giveItemStack(itemEntity.getStack());
            itemEntity.discard();
        });
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.MAGNET;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
