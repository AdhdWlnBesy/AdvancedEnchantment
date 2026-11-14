package com.advanced_enchants.block.vault;

import com.advanced_enchants.block.DungeonBlock;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModBlockEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 地牢宝库方块实体，负责奖池与抽奖。
 *
 * @see DungeonVaultBlock
 */
public class DungeonVaultBlockEntity extends BlockEntity {
    public DungeonVaultBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.DUNGEON_VAULT, pos, state);
        this.load = true;
    }

    public byte state;         //抽奖状态,0为空闲,1为抽奖中,2为发放物品
    public short tick;          //用于滚动物品，一个计数器
    public short time;          //用于冷却，一个计数器
    public float soundPitch;  //滚动声音快慢
    private boolean load;     //用于抽奖时重进恢复设置
    public UUID uuid;         //抽奖玩家UUID
    private List<ItemStack> pool = new ArrayList<>();//奖池，遍历的列表
    public List<ItemStack> showItems = new ArrayList<>();//在方块上显示的5个物品，因要滚动所以可变

    /**
     * 这两个如果要同步客户端(BlockEntityRenderer)，必须要写，害我卡了好几天
     */
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putByte("State", this.state);
        nbt.putShort("Time", this.time);
        nbt.putShort("Tick", this.tick);
        nbt.putFloat("SoundPitch", this.soundPitch);
        if (this.uuid != null) {
            nbt.putUuid("UUID", this.uuid);
        }
        NbtList pool = new NbtList();
        for (ItemStack stack : this.pool) {
            pool.add(stack.encodeAllowEmpty(registryLookup));
        }
        nbt.put("Pool", pool);

        NbtList showItems = new NbtList();
        for (ItemStack stack : this.showItems) {
            showItems.add(stack.encodeAllowEmpty(registryLookup));
        }
        nbt.put("ShowItems", showItems);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.state = nbt.getByte("State");
        this.tick = nbt.getShort("Tick");
        this.time = nbt.getShort("Time");
        this.soundPitch = nbt.getFloat("SoundPitch");
        if (nbt.contains("UUID")) {
            this.uuid = nbt.getUuid("UUID");
        } else {
            this.uuid = null;
        }

        List<ItemStack> newShow = new ArrayList<>();
        NbtList showList = nbt.getList("ShowItems", NbtElement.COMPOUND_TYPE);
        for (NbtElement elem : showList) {
            newShow.add(ItemStack.fromNbtOrEmpty(registryLookup, (NbtCompound) elem));
        }
        this.showItems = newShow;

        List<ItemStack> newPool = new ArrayList<>();
        NbtList poolList = nbt.getList("Pool", NbtElement.COMPOUND_TYPE);
        for (NbtElement elem : poolList) {
            newPool.add(ItemStack.fromNbtOrEmpty(registryLookup, (NbtCompound) elem));
        }
        this.pool = newPool;
    }

    public void initPool(List<DungeonVaultBlock.VaultPool> pools) { //初始化奖池
        List<ItemStack> temp = new ArrayList<>();
        for (DungeonVaultBlock.VaultPool pool : pools) {
            for (int i = 0; i < pool.weight(); i++) {
                temp.add(new ItemStack(pool.item(), pool.count()));
            }
        }
        Collections.shuffle(temp);
        pool = temp;

        showItems.clear();
        for (int i = 0; i < 5; i++) {
            showItems.add(this.pool.get(i % this.pool.size()));
        }
        markDirtyAndSync();
    }

    public void markDirtyAndSync() { //更新方块数据并同步客户端(务必要用serverWorld)
        markDirty();
        if (!(world instanceof ServerWorld serverWorld)) return;
        serverWorld.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public Direction getFacing() { //获取方块方向，不知道为什么onUse提供的方向在renderer里永远接受不到，只能这样写了
        try {
            if (getWorld() != null) {
                return getWorld().getBlockState(pos).get(DungeonBlock.FACING);
            } else return null;
        } catch (Exception e) {
            return null;
        }
    }

    private void lottery(World world, BlockPos pos) { //抽奖
        if (pool == null || pool.isEmpty() || uuid == null || world == null) {
            return;
        }
        PlayerEntity player = world.getPlayerByUuid(uuid);
        if (player == null) {
            return;
        }
        if (state == 1) {
            tick++;
            int speed;
            if (tick < 60) {
                speed = 1;
                soundPitch = 1.3f;
            } else if (tick < 100) {
                speed = 2;
                soundPitch = 1.2f;
            } else if (tick < 140) {
                speed = 3;
                soundPitch = 1.1f;
            } else if (tick < 180) {
                speed = 5;
                soundPitch = 1f;
            } else {
                tick = 0;
                state = 2;
                markDirtyAndSync();
                return;
            } //更改滚动速度

            if (this.tick % speed == 0) { //更改显示内容
                for (int i = 0; i < 5; i++) {
                    showItems.set(i, pool.get(((tick / speed) % pool.size() + i) % pool.size()));
                }
                ModAPI.playSoundsToBlock(SoundEvents.BLOCK_DISPENSER_DISPENSE, world, pos.up(), soundPitch);
                if (tick % 5 == 0) {
                    markDirtyAndSync();
                }
            }
        }
        if (state == 2) { // 弹出物品
            tick++;
            if (tick >= 60) {
                ItemStack drop = showItems.get(2).copy();
                ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, drop);
                world.spawnEntity(entity);
                player.sendMessage(Text.translatable("chat.lottery_vault_win_item").append(drop.getCount() + "*").append(drop.getItem().getName()), true);
                ModAPI.playSoundsToBlock(SoundEvents.ENTITY_ITEM_PICKUP, world, pos.up(), 1);
                tick = 0;
                state = 0;
                markDirtyAndSync();
            }
        }
    }

    public static void tick(World world, BlockPos pos, BlockState ignoredState, DungeonVaultBlockEntity vault) {
        if (vault.load) { //重进时还原设置
            vault.state = 0;
            vault.tick = 0;
            vault.soundPitch = 0;
            vault.uuid = null;
            vault.pool.clear();
            vault.showItems.clear();
            vault.load = false;
            vault.markDirtyAndSync();
        }

        //计时并驱动抽奖
        vault.time++;
        if (vault.state == 0) {
            if (vault.time >= 1300) {
                vault.time = 0;
                vault.markDirtyAndSync();
            }
        } else {
            vault.lottery(world, pos);
            vault.markDirtyAndSync();
        }
    }
}