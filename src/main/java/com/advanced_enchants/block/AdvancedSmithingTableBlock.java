package com.advanced_enchants.block;

import com.advanced_enchants.gui.SmithingScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 高级锻造台方块。
 *
 * @see com.advanced_enchants.gui.SmithingScreenHandler
 */
public class AdvancedSmithingTableBlock extends Block {
    public AdvancedSmithingTableBlock() {
        super(Settings.create().requiresTool().strength(3.5f, 1200.0f).sounds(BlockSoundGroup.WOOD));
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerEntity)
                -> new SmithingScreenHandler(syncId, playerInventory), Text.translatable("gui.advanced_enchants_smithing.text"));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        }
        return ActionResult.SUCCESS;
    }
}
