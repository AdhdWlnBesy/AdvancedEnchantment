package com.advanced_enchants.enchantment.pickaxe;

import com.advanced_enchants.enchantment.effect.PickaxeEnchantmentEffect;
import com.advanced_enchants.event.EnchantTrigNetWorking;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import com.advanced_enchants.register.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public record WealthDigging() implements PickaxeEnchantmentEffect {
    @Override
    public void apply(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, int level) {
        if (ModAPI.hasEnchantment(Enchantments.SILK_TOUCH, world.getServer(), player.getMainHandStack())
                || ModAPI.hasEnchantment(Enchantments.SILK_TOUCH, world.getServer(), player.getOffHandStack())) return;
        if (state.isIn(ModTags.ORES)) {
            Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, player, player.getMainHandStack()).forEach(
                    stack -> {
                        int count = stack.getCount();
                        if (player.getRandom().nextBoolean()) {
                            count *= 2;
                        } else {
                            count += (int) Math.ceil(count * 0.5);
                        }
                        world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(stack.getItem(), count)));
                    });
            if (player instanceof ServerPlayerEntity serverPlayer) {
                EnchantTrigNetWorking.sendPacket(serverPlayer, getEnchantment(serverPlayer.getServer()).value().description(), level);
            }
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.WEALTH_DIGGING;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
