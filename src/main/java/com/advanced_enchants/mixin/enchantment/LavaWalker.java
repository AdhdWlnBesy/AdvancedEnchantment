package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.register.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public class LavaWalker {
    @Inject(method = "melt", at = @At(value = "HEAD"), cancellable = true)
    private void mainEffect(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        if (state.isOf(ModBlocks.PEBBLE)) {
            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
            ci.cancel();
        }
    }
}
