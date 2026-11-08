package com.advanced_enchants.enchantment.pickaxe;

import com.advanced_enchants.enchantment.effect.PickaxeEnchantmentEffect;
import com.advanced_enchants.event.EnchantTrigNetWorking;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import com.advanced_enchants.register.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public record MiningExperience() implements PickaxeEnchantmentEffect {
    @Override
    public void apply(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity entity, int level) {
        if (ModAPI.hasEnchantment(Enchantments.SILK_TOUCH, world.getServer(), player.getMainHandStack())
                || ModAPI.hasEnchantment(Enchantments.SILK_TOUCH, world.getServer(), player.getOffHandStack())) return;
        if (state.isIn(ModTags.ORES) && world instanceof ServerWorld serverWorld) {
            ExperienceOrbEntity.spawn(serverWorld, Vec3d.of(pos), level * 3);
            if (player instanceof ServerPlayerEntity serverPlayer) {
                EnchantTrigNetWorking.sendPacket(serverPlayer, getEnchantment(serverPlayer.getServer()).value().description(), level);
            }
        }
    }

    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.MINING_EXPERIENCE;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.ALWAYS, 1);
    }
}
