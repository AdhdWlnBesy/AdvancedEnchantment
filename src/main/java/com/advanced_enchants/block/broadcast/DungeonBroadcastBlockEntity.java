package com.advanced_enchants.block.broadcast;

import com.advanced_enchants.block.DungeonBlock;
import com.advanced_enchants.block.spawner.DungeonSpawnerBlockEntity;
import com.advanced_enchants.block.vault.DungeonVaultBlockEntity;
import com.advanced_enchants.datagen.AdvancementProvider;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModBlockEntityType;
import com.advanced_enchants.register.ModTags;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructureStart;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 地牢主控方块实体，负责波次、范围和战斗进度。
 *
 * @see DungeonBroadcastBlock
 */

@SuppressWarnings("SpellCheckingInspection")
public class DungeonBroadcastBlockEntity extends BlockEntity {
    public final ServerBossBar battleProgress = new ServerBossBar(Text.translatable("chat.battle_progress", 0, 1), BossBar.Color.RED, BossBar.Style.NOTCHED_10); //进度栏
    private byte tick = 0; //用来存储战斗时间
    public short time;      //用来冷却计时
    public boolean lock;  //锁1，使地牢在冷却时不能启动
    public boolean lock2; //锁2，按钮会使方块产生4个tick更新，这个锁让实际产生更新变成1个
    public boolean isBooting;//是否启动
    public List<UUID> players = new ArrayList<>();//玩家列表
    public BlockPos spawnerPos;//获取波数的刷怪笼的位置
    public int[] rangeBox; //地牢范围 minX,minY,minZ,maxX,maxY,maxZ，第一次 tick 由附近的方块实体推出来
    public boolean scanned;//是否已经扫描过
    public List<BlockPos> orePositions = new ArrayList<>();//地牢自带的矿块位置，第一次 tick 时从世界里扫出来
    public BlockPos chestPos; //地牢箱位置，第一次 tick 时从世界里扫出来
    private static final int RANGE_VERSION = 3; //范围算法版本，旧存档的范围会在 tick 里重算

    public DungeonBroadcastBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.DUNGEON_BROADCAST, pos, state);
        PlayerBlockBreakEvents.BEFORE.register((world, player, breakPos, breakState, blockEntity) ->
                world != this.world || !breakState.isIn(ModTags.ORE_BLOCK) || !orePositions.contains(breakPos));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putBoolean("IsBooting", isBooting);
        nbt.putByte("Tick", tick);
        nbt.putShort("Time", time);
        nbt.putBoolean("Lock", lock);
        nbt.putBoolean("Lock2", lock2);

        if (players != null) {
            NbtList uuidList = new NbtList();
            for (UUID uuid : players) {
                NbtCompound uuidTag = new NbtCompound();
                uuidTag.putUuid("Id", uuid);
                uuidList.add(uuidTag);
            }
            nbt.put("Players", uuidList);
        }

        if (spawnerPos != null) {
            nbt.putInt("PosX", spawnerPos.getX());
            nbt.putInt("PosY", spawnerPos.getY());
            nbt.putInt("PosZ", spawnerPos.getZ());
        }

        if (chestPos != null) {
            nbt.putInt("ChestPosX", chestPos.getX());
            nbt.putInt("ChestPosY", chestPos.getY());
            nbt.putInt("ChestPosZ", chestPos.getZ());
        }

        if (rangeBox != null) nbt.putIntArray("Range", rangeBox);
        nbt.putInt("RangeVersion", RANGE_VERSION);
        nbt.putBoolean("scanned", scanned);
        nbt.putIntArray("Ores", orePositions.stream().flatMapToInt(p -> IntStream.of(p.getX(), p.getY(), p.getZ())).toArray());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        isBooting = nbt.getBoolean("IsBooting");
        tick = nbt.getByte("Tick");
        time = nbt.getShort("Time");
        lock = nbt.getBoolean("Lock");
        lock2 = nbt.getBoolean("Lock2");

        List<UUID> newPlayers = new ArrayList<>();
        NbtList uuidList = nbt.getList("Players", NbtCompound.COMPOUND_TYPE);
        for (int i = 0; i < uuidList.size(); i++) {
            newPlayers.add(uuidList.getCompound(i).getUuid("Id"));
        }
        players = newPlayers;

        spawnerPos = new BlockPos(nbt.getInt("PosX"), nbt.getInt("PosY"), nbt.getInt("PosZ"));
        chestPos = nbt.contains("ChestPosX")
                ? new BlockPos(nbt.getInt("ChestPosX"), nbt.getInt("ChestPosY"), nbt.getInt("ChestPosZ"))
                : null;

        rangeBox = nbt.getInt("RangeVersion") >= RANGE_VERSION && nbt.getIntArray("Range").length == 6
                ? nbt.getIntArray("Range")
                : null;
        scanned = nbt.getBoolean("scanned");
        List<BlockPos> newOres = new ArrayList<>();
        int[] ores = nbt.getIntArray("Ores");
        for (int i = 0; i + 2 < ores.length; i += 3) {
            newOres.add(new BlockPos(ores[i], ores[i + 1], ores[i + 2]));
        }
        orePositions = newOres;
    }

    /**
     * @see DungeonVaultBlockEntity
     */
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void reset() { //重置地牢
        isBooting = false;
        time = 0;
        battleProgress.clearPlayers();
        lock = true;
        if (world != null) {
            world.setBlockState(pos.up(), Blocks.OAK_BUTTON.getDefaultState().with(Properties.BLOCK_FACE, BlockFace.FLOOR));
        }
        ModAPI.getBlockEntities(getDungeonRange(pos), DungeonSpawnerBlockEntity.class, world, false)
                .forEach(spawnerEntity -> {
                    spawnerEntity.refreshLivingEntities().forEach(Entity::discard);
                    spawnerEntity.reset();
                });
        players.clear();
        markDirtyAndSync();
    }

    public void markDirtyAndSync() { //更新方块数据并同步客户端
        markDirty();
        if (!(world instanceof ServerWorld serverWorld)) return;
        serverWorld.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    private static BlockPos findChest(World world, BlockPos center) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos pos = new BlockPos(center.getX() + x, center.getY(), center.getZ() + z);
                if (world.getBlockState(pos).isOf(Blocks.CHEST)) return pos;
            }
        }
        return null;
    }

    private static boolean isNear(BlockPos a, BlockPos b) {
        return Math.abs(a.getX() - b.getX()) <= 4
                && Math.abs(a.getY() - b.getY()) <= 4
                && Math.abs(a.getZ() - b.getZ()) <= 4;
    }

    public Box getDungeonRange(BlockPos pos) {
        if (rangeBox == null) return new Box(pos).expand(20);
        return new Box(rangeBox[0], rangeBox[1], rangeBox[2], rangeBox[3], rangeBox[4], rangeBox[5]);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, DungeonBroadcastBlockEntity broadcast) {
        // 更新地牢范围与箱子位置
        if (world instanceof ServerWorld serverWorld) {
            boolean changed = false;
            if (broadcast.rangeBox == null) {
                StructureStart start = serverWorld.getStructureAccessor().getStructureContaining(blockPos, ModTags.DUNGEONS);
                if (start != null && start.hasChildren()) {
                    // getBoundingBox() 已经按 bury 外扩 12 格，再补 12 格，恢复原来的大范围。
                    BlockBox box = start.getBoundingBox();
                    broadcast.rangeBox = new int[]{
                            box.getMinX() - 12, box.getMinY() - 4, box.getMinZ() - 12,
                            box.getMaxX() + 12, box.getMaxY() + 14, box.getMaxZ() + 12
                    };
                    broadcast.orePositions.clear();
                    broadcast.scanned = false;
                    changed = true;
                }
            }
            if (broadcast.chestPos == null || !isNear(broadcast.chestPos, blockPos)) {
                BlockPos chest = findChest(serverWorld, blockPos);
                if (chest != null) {
                    broadcast.chestPos = chest;
                    changed = true;
                } else if (broadcast.chestPos != null) {
                    broadcast.chestPos = null;
                    changed = true;
                }
            }
            if (changed) broadcast.markDirtyAndSync();
        }

        // 第一次 tick 扫描地牢里的矿块
        if (!world.isClient && broadcast.rangeBox != null && !broadcast.scanned) {
            int[] box = broadcast.rangeBox;
            for (BlockPos orePos : BlockPos.iterate(box[0], box[1], box[2], box[3], box[4], box[5])) {
                if (world.getBlockState(orePos).isIn(ModTags.ORE_BLOCK))
                    broadcast.orePositions.add(orePos.toImmutable());
            }
            broadcast.scanned = true;
            broadcast.markDirty();
        }

        // 未启动时只走冷却计时
        if (!broadcast.isBooting) {
            broadcast.time++;
            if (broadcast.time >= 24000) {
                broadcast.time = 0;
                broadcast.lock = false;
            }
            broadcast.markDirtyAndSync();
            return;
        }

        broadcast.tick++;

        // 和平难度下踢出玩家并重置地牢
        if (world.getDifficulty() == Difficulty.PEACEFUL) {
            for (UUID uuid : broadcast.players) {
                PlayerEntity player = world.getPlayerByUuid(uuid);
                if (player == null) continue;
                player.sendMessage(Text.translatable("chat.broadcast.break"));
            }
            broadcast.reset();
        }
        int totalSpawnWaveNumber;
        // 找到地牢里的刷怪笼并记录；找不到就先返回，找到记录波数
        if (!(world.getBlockEntity(broadcast.spawnerPos) instanceof DungeonSpawnerBlockEntity spawner)) {
            ModAPI.getBlockEntities(broadcast.getDungeonRange(blockPos), DungeonSpawnerBlockEntity.class, world, true)
                    .forEach(entity -> broadcast.spawnerPos = entity.getPos());
            broadcast.markDirtyAndSync();
            return;
        } else {
            totalSpawnWaveNumber = spawner.spawnWaveNumber;
        }

        // 移除死亡或离开范围的玩家
        if (broadcast.players.removeIf(uuid -> {
            PlayerEntity player = world.getPlayerByUuid(uuid);
            if (player == null) return true;
            return player.isDead() || !broadcast.getDungeonRange(blockPos).contains(player.getPos());
        })) broadcast.markDirtyAndSync();

        // 每 30 tick 统计一次存活怪物和波数，判断是否通关
        if (broadcast.tick >= 30) {
            broadcast.lock2 = false;
            AtomicInteger totalSpawn = new AtomicInteger();
            AtomicInteger endBlocks = new AtomicInteger();
            ModAPI.getBlockEntities(broadcast.getDungeonRange(blockPos), DungeonSpawnerBlockEntity.class, world, false)
                    .forEach((spawnerEntity) -> {
                        totalSpawn.addAndGet(spawnerEntity.livingEntities.size());
                        if (spawnerEntity.livingEntities.isEmpty() && spawnerEntity.spawnWaveNumber >= 10) {
                            endBlocks.addAndGet(1);
                        }
                    });
            int level = state.get(DungeonBlock.DUNGEON_LEVEL);
            if (endBlocks.get() == (level == 2 ? 5 : 8) || (totalSpawn.get() == 0 && totalSpawnWaveNumber >= 10)) {
                broadcast.players.forEach(uuid -> {
                    PlayerEntity player = world.getPlayerByUuid(uuid);
                    if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "dungeon_win");
                    if (level == 3) {
                        AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "dungeon_win_level3");
                    }
                    if (level == 5) {
                        AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "dungeon_win_level5");
                    }
                });
                broadcast.reset();
                return;
            }
            broadcast.battleProgress.setName(Text.translatable("chat.battle_progress", totalSpawn.get(), totalSpawnWaveNumber));
            broadcast.battleProgress.setPercent(Math.min(totalSpawnWaveNumber / 10f, 1f));
            broadcast.markDirtyAndSync();
            broadcast.tick = 0;
        }

        // 没有玩家时重置
        if (broadcast.players.isEmpty()) broadcast.reset();
    }
}
