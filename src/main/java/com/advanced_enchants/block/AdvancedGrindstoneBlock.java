package com.advanced_enchants.block;

import com.advanced_enchants.gui.GrindstoneScreenHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 高级砂轮方块。
 *
 * @see com.advanced_enchants.gui.GrindstoneScreenHandler
 */
public class AdvancedGrindstoneBlock extends GrindstoneBlock {
    public AdvancedGrindstoneBlock() {
        super(AbstractBlock.Settings.create().requiresTool().strength(2.5f, 1200.0f));
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerEntity)
                -> new GrindstoneScreenHandler(syncId, playerInventory), Text.translatable("gui.advanced_enchants_grindstone.text"));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        }
        return ActionResult.SUCCESS;
    }
}
