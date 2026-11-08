package com.advanced_enchants.block;

import com.advanced_enchants.gui.AnvilScreenHandler;
import net.minecraft.block.AnvilBlock;
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
 * 高级铁砧方块。
 *
 * @see com.advanced_enchants.gui.AnvilScreenHandler
 */
public class AdvancedAnvilBlock extends AnvilBlock {
    public AdvancedAnvilBlock() {
        super(Settings.create().requiresTool().strength(5.0f, 1200.0f).sounds(BlockSoundGroup.ANVIL));
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, playerEntity) ->
                new AnvilScreenHandler(syncId, inventory), Text.translatable("gui.advanced_enchants_anvil.text"));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        }
        return ActionResult.SUCCESS;
    }
}
