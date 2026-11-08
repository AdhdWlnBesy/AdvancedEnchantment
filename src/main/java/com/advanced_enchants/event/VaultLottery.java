package com.advanced_enchants.event;

import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModBlocks;
import com.advanced_enchants.register.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 宝库抽奖事件。
 *
 * @see com.advanced_enchants.block.vault.DungeonVaultBlockEntity
 */
@Deprecated
public class VaultLottery {
    public record VaultPool(Item item, int count, int weight) {
    }

    private static final List<VaultPool> POOL1 = List.of(
            new VaultPool(Items.IRON_INGOT, 64, 2),
            new VaultPool(Items.IRON_BLOCK, 8, 1),
            new VaultPool(Items.GOLD_INGOT, 64, 2),
            new VaultPool(Items.GOLD_BLOCK, 8, 1),
            new VaultPool(Items.EMERALD, 32, 2),
            new VaultPool(Items.DIAMOND, 32, 2),
            new VaultPool(ModItems.ORDINARY_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.ORDINARY_ENCHANTS_BOOK, 2, 1),
            new VaultPool(ModItems.ADVANCED_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 1, 1),
            new VaultPool(ModItems.ORDINARY_LEVELUP_BALL, 2, 1),
            new VaultPool(ModItems.ORDINARY_LEVELUP_BALL, 1, 1),
            new VaultPool(ModItems.ADVANCED_LEVELUP_BALL, 1, 1),
            new VaultPool(ModItems.LEVEL1_SLOT_BUILDER, 1, 1)
    );

    public static int state;
    public static int tick;
    public static float soundPitch;
    public static BlockPos pos;
    public static Direction facing;
    public static PlayerEntity player;
    public static List<ItemStack> pool;
    public static final List<ItemStack> showItems = new ArrayList<>();

    @Deprecated
// 注册宝库抽奖事件。
    public static void mainEvent() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player.isSneaking()) return ActionResult.PASS;
            BlockPos pos = hitResult.getBlockPos();
            if (world.getBlockState(pos).isOf(ModBlocks.DUNGEON_VAULT) && player.getMainHandStack().isOf(ModItems.DUNGEON1_KEY)) {
                if (state != 0) return ActionResult.PASS;
                List<ItemStack> tempPool = new ArrayList<>();
                for (VaultPool pool : POOL1) {
                    for (int i = 0; i < pool.weight(); i++) {
                        tempPool.add(new ItemStack(pool.item(), pool.count()));
                    }
                }
                Collections.shuffle(tempPool);
                pool = tempPool;
                tick = 0;
                soundPitch = 0;
                showItems.clear();
                for (int i = 0; i < 5; i++) {
                    showItems.add(ItemStack.EMPTY);
                }
                VaultLottery.pos = pos.up();
                VaultLottery.player = player;
                facing = hitResult.getSide();
                state = 1;
                return ActionResult.SUCCESS_NO_ITEM_USED;
            }
            return ActionResult.PASS;
        });
        WorldRenderEvents.AFTER_ENTITIES.register(wrc -> {
            if (state == 0) return;
            if (facing == Direction.EAST || facing == Direction.WEST) {
                ModAPI.showItem(showItems.get(0), pos.north(2), wrc.world());
                ModAPI.showItem(showItems.get(1), pos.north(), wrc.world());
                ModAPI.showItem(showItems.get(2), pos, wrc.world());
                ModAPI.showItem(showItems.get(3), pos.south(), wrc.world());
                ModAPI.showItem(showItems.get(4), pos.south(2), wrc.world());
            } else {
                ModAPI.showItem(showItems.get(0), pos.east(2), wrc.world());
                ModAPI.showItem(showItems.get(1), pos.east(), wrc.world());
                ModAPI.showItem(showItems.get(2), pos, wrc.world());
                ModAPI.showItem(showItems.get(3), pos.west(), wrc.world());
                ModAPI.showItem(showItems.get(4), pos.west(2), wrc.world());
            }
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (pool == null || pool.isEmpty() || player == null || player.getWorld() == null) return;
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
                    return;
                }
                if (tick % speed == 0) {
                    for (int i = 0; i < 5; i++) {
                        showItems.set(i, pool.get(((tick / speed) % pool.size() + i) % pool.size()));
                    }
                    player.getWorld().playSound(player, pos, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, soundPitch);
                }
            }
            if (state == 2) {
                tick++;
                if (tick >= 60) {
                    ItemStack drop = showItems.get(2).copy();
                    ItemEntity entity = new ItemEntity(player.getWorld(), pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, drop);
                    player.getWorld().spawnEntity(entity);
                    player.sendMessage(Text.translatable("chat.lottery_vault_win_item").append(drop.getCount() + "*").append(drop.getItem().getName()), true);
                    player.getWorld().playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1F, 1F);
                    tick = 0;
                    state = 0;
                }
            }
        });
    }
}
