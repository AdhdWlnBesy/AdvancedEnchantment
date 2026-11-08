package com.advanced_enchants.block.broadcast;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.block.DungeonBlock;
import com.advanced_enchants.block.spawner.DungeonSpawnerBlockEntity;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModBlockEntityType;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.Objects;

/**
 * 地牢主控方块。
 *
 * @see DungeonBroadcastBlockEntity
 */
public class DungeonBroadcastBlock extends DungeonBlock {
    public DungeonBroadcastBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends DungeonBlock> getCodec() {
        return createCodec(DungeonBroadcastBlock::new);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DungeonBroadcastBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!AdvancedEnchantment.BUILT) {
            return world.isClient() ? null : validateTicker(type, ModBlockEntityType.DUNGEON_BROADCAST, DungeonBroadcastBlockEntity::tick);
        }
        return null;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClient() || world.getDifficulty().equals(Difficulty.PEACEFUL)) return;
        if (sourceBlock == Blocks.OAK_BUTTON) {
            if (world.getBlockEntity(pos) instanceof DungeonBroadcastBlockEntity broadcast) {
                if (broadcast.lock || broadcast.lock2) return;
                broadcast.lock2 = true;
                world.breakBlock(pos.up(), false);
                ModAPI.getBlockEntities(new Box(pos).expand(15), DungeonSpawnerBlockEntity.class, world, false)
                        .forEach(spawner -> {
                            broadcast.spawnerPos = spawner.getPos();
                            spawner.isBooting = true;
                        });//启动刷怪笼
                broadcast.isBooting = true;
                broadcast.battleProgress.setDragonMusic(false);
                broadcast.battleProgress.setDarkenSky(false);
                world.getEntitiesByClass(PlayerEntity.class, new Box(Objects.requireNonNull(world.getBlockEntity(pos)).getPos()).expand(15),
                        player -> true).forEach(player -> {
                    broadcast.battleProgress.addPlayer((ServerPlayerEntity) player);
                    broadcast.players.add(player.getUuid());
                });//添加玩家
            }
        }
    }
}
