package com.advanced_enchants.enchantment.pickaxe;

import com.advanced_enchants.enchantment.effect.PickaxeEnchantmentEffect;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public record Chain() implements PickaxeEnchantmentEffect {
    @Override
    public void apply(World world, PlayerEntity player, BlockPos blockPos, BlockState state, BlockEntity blockEntity, int level) {
        if (!player.isSneaking()) return;
        int maxBlocks = switch (level) {
            case 1 -> 4;
            case 2 -> 12;
            case 3 -> 32;
            default -> 64;
        };
        Set<BlockPos> set = new HashSet<>(List.of(blockPos));
        Queue<BlockPos> queue = new LinkedList<>(List.of(blockPos));
        List<ItemStack> drops = new ArrayList<>();
        while (!queue.isEmpty() && set.size() < maxBlocks) {
            BlockPos current = queue.poll();
            for (BlockPos pos : getRanges()) {
                BlockPos nextPos = current.add(pos);
                if (set.contains(nextPos) || set.size() >= maxBlocks) continue;
                BlockState nextState = world.getBlockState(nextPos);
                if (!state.isOf(nextState.getBlock())) continue;
                set.add(nextPos);
                queue.add(nextPos);
                drops.addAll(Block.getDroppedStacks(nextState, (ServerWorld) world, nextPos, null, player, player.getMainHandStack()));
                world.breakBlock(nextPos, false, player);
            }
        }
        for (ItemStack stack : drops) {
            world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack));
        }
        int damage = set.size() - 1;
        if (damage > 0) {
            player.getMainHandStack().damage(damage, player, EquipmentSlot.MAINHAND);
        }
        if (player instanceof ServerPlayerEntity serverPlayer) {
            sendPacket(serverPlayer, getEnchantment(serverPlayer.server), level);
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.CHAIN;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }

    private List<BlockPos> getRanges() {
        List<BlockPos> range = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    range.add(new BlockPos(dx, dy, dz));
                }
            }
        }
        return range;
    }
}
