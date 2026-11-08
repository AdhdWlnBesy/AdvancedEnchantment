package com.advanced_enchants.block.spawner;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.block.DungeonBlock;
import com.advanced_enchants.register.ModBlockEntityType;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 地牢刷怪笼方块。
 *
 * @see DungeonSpawnerBlockEntity
 */
public class DungeonSpawnerBlock extends DungeonBlock {
    public DungeonSpawnerBlock(Settings settings) {
        super(settings.nonOpaque().blockVision(Blocks::never));
        setDefaultState(this.stateManager.getDefaultState().with(SPAWNER_TYPE, 1));
    }

    public static final IntProperty SPAWNER_TYPE = IntProperty.of("spawner_type", 1, 3);

    @Override
    protected MapCodec<? extends DungeonBlock> getCodec() {
        return createCodec(DungeonSpawnerBlock::new);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DungeonSpawnerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!AdvancedEnchantment.BUILT) {
            return world.isClient() ? null : validateTicker(type, ModBlockEntityType.DUNGEON_SPAWNER, DungeonSpawnerBlockEntity::tick);
        }
        return null;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite()).with(DUNGEON_LEVEL, 1).with(SPAWNER_TYPE, 1);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SPAWNER_TYPE);
    }
}
