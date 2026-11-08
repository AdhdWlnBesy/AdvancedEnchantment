package com.advanced_enchants.block.vault;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.block.DungeonBlock;
import com.advanced_enchants.datagen.AdvancementProvider;
import com.advanced_enchants.register.ModBlockEntityType;
import com.advanced_enchants.register.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * 地牢宝库方块。
 *
 * @see DungeonVaultBlockEntity
 */
public class DungeonVaultBlock extends DungeonBlock {
    public DungeonVaultBlock(Settings settings) {
        super(settings.nonOpaque().blockVision(Blocks::never));
    }

    // 奖池注册
    public record VaultPool(Item item, int count, int weight) {
    }

    private static final List<VaultPool> POOL1 = List.of(
            new VaultPool(Items.IRON_INGOT, 64, 1),
            new VaultPool(Items.IRON_BLOCK, 8, 1),
            new VaultPool(Items.GOLD_INGOT, 64, 1),
            new VaultPool(Items.GOLD_BLOCK, 8, 1),
            new VaultPool(Items.EMERALD, 32, 2),
            new VaultPool(Items.DIAMOND, 32, 2),
            new VaultPool(ModItems.ORDINARY_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.ORDINARY_ENCHANTS_BOOK, 2, 1),
            new VaultPool(ModItems.ADVANCED_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.ORDINARY_LEVELUP_BALL, 2, 1),
            new VaultPool(ModItems.ORDINARY_LEVELUP_BALL, 1, 2),
            new VaultPool(ModItems.ADVANCED_LEVELUP_BALL, 1, 1),
            new VaultPool(ModItems.LEVEL1_SLOT_BUILDER, 1, 1)
    );

    private static final List<VaultPool> POOL2 = List.of(
            new VaultPool(Items.IRON_INGOT, 96, 1),
            new VaultPool(Items.IRON_BLOCK, 12, 1),
            new VaultPool(Items.GOLD_INGOT, 96, 1),
            new VaultPool(Items.GOLD_BLOCK, 12, 1),
            new VaultPool(Items.EMERALD, 48, 2),
            new VaultPool(Items.DIAMOND, 48, 2),
            new VaultPool(ModItems.ORDINARY_ENCHANTS_BOOK, 2, 2),
            new VaultPool(ModItems.ADVANCED_ENCHANTS_BOOK, 2, 1),
            new VaultPool(ModItems.ADVANCED_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.ORDINARY_LEVELUP_BALL, 2, 2),
            new VaultPool(ModItems.ADVANCED_LEVELUP_BALL, 2, 1),
            new VaultPool(ModItems.RARE_LEVELUP_BALL, 1, 1),
            new VaultPool(ModItems.LEVEL2_SLOT_BUILDER, 1, 1)
    );

    private static final List<VaultPool> POOL3 = List.of(
            new VaultPool(Items.IRON_BLOCK, 16, 1),
            new VaultPool(Items.GOLD_BLOCK, 16, 1),
            new VaultPool(Items.EMERALD, 64, 2),
            new VaultPool(Items.EMERALD_BLOCK, 8, 1),
            new VaultPool(Items.DIAMOND, 64, 2),
            new VaultPool(Items.DIAMOND_BLOCK, 8, 1),
            new VaultPool(ModItems.ADVANCED_ENCHANTS_BOOK, 2, 3),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.PRECIOUS_ENCHANTS_BOOK, 1, 1),
            new VaultPool(ModItems.ADVANCED_LEVELUP_BALL, 2, 2),
            new VaultPool(ModItems.RARE_LEVELUP_BALL, 1, 2),
            new VaultPool(ModItems.RARE_LEVELUP_BALL, 2, 1),
            new VaultPool(ModItems.LEVEL3_SLOT_BUILDER, 1, 1)
    );

    private static final List<VaultPool> POOL4 = List.of(
            new VaultPool(Items.GOLD_BLOCK, 24, 1),
            new VaultPool(Items.EMERALD_BLOCK, 12, 2),
            new VaultPool(Items.EMERALD_BLOCK, 16, 1),
            new VaultPool(Items.DIAMOND_BLOCK, 12, 2),
            new VaultPool(Items.DIAMOND_BLOCK, 16, 1),
            new VaultPool(ModItems.ADVANCED_ENCHANTS_BOOK, 3, 1),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 2, 2),
            new VaultPool(ModItems.PRECIOUS_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.PRECIOUS_ENCHANTS_BOOK, 2, 1),
            new VaultPool(ModItems.RARE_LEVELUP_BALL, 2, 2),
            new VaultPool(ModItems.PRECIOUS_LEVELUP_BALL, 1, 2),
            new VaultPool(ModItems.LEVEL2_SLOT_BUILDER, 2, 1)
    );

    private static final List<VaultPool> POOL5 = List.of(
            new VaultPool(Items.EMERALD_BLOCK, 16, 1),
            new VaultPool(Items.EMERALD_BLOCK, 32, 1),
            new VaultPool(Items.DIAMOND_BLOCK, 16, 1),
            new VaultPool(Items.DIAMOND_BLOCK, 32, 1),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 2, 2),
            new VaultPool(ModItems.RARE_ENCHANTS_BOOK, 3, 1),
            new VaultPool(ModItems.PRECIOUS_ENCHANTS_BOOK, 1, 2),
            new VaultPool(ModItems.PRECIOUS_ENCHANTS_BOOK, 2, 2),
            new VaultPool(ModItems.PRECIOUS_ENCHANTS_BOOK, 3, 1),
            new VaultPool(ModItems.RARE_LEVELUP_BALL, 2, 2),
            new VaultPool(ModItems.PRECIOUS_LEVELUP_BALL, 1, 2),
            new VaultPool(ModItems.RARE_LEVELUP_BALL, 3, 1),
            new VaultPool(ModItems.PRECIOUS_LEVELUP_BALL, 2, 2),
            new VaultPool(ModItems.LEVEL3_SLOT_BUILDER, 2, 1)
    );

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DungeonVaultBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!AdvancedEnchantment.BUILT) {
            return world.isClient() ? null : validateTicker(type, ModBlockEntityType.DUNGEON_VAULT, DungeonVaultBlockEntity::tick);
        }
        return null;
    }

    @Override
    protected MapCodec<? extends DungeonBlock> getCodec() {
        return createCodec(DungeonVaultBlock::new);
    }

    //开启抽奖
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.isSneaking()) return ActionResult.PASS;
        if (world.getBlockEntity(pos) instanceof DungeonVaultBlockEntity vault) {
            if (vault.state != 0) return ActionResult.PASS;
            if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
                List<VaultPool> pool;
                int dungeonLevel = state.get(DungeonBlock.DUNGEON_LEVEL);
                ItemStack stack = player.getMainHandStack();
                if (dungeonLevel == 1 && stack.isOf(ModItems.DUNGEON1_KEY)) {
                    pool = POOL1;
                } else if (dungeonLevel == 2 && stack.isOf(ModItems.DUNGEON2_KEY)) {
                    pool = POOL2;
                } else if (dungeonLevel == 3 && stack.isOf(ModItems.DUNGEON3_KEY)) {
                    pool = POOL3;
                } else if (dungeonLevel == 4 && stack.isOf(ModItems.DUNGEON4_KEY)) {
                    pool = POOL4;
                } else if (dungeonLevel == 5 && stack.isOf(ModItems.DUNGEON5_KEY)) {
                    pool = POOL5;
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "vault_high");
                } else {
                    return ActionResult.PASS;
                }
                AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "vault");
                player.getMainHandStack().decrementUnlessCreative(1, player);
                vault.uuid = player.getUuid();
                vault.soundPitch = 0;
                vault.initPool(pool);
                vault.state = 1;
                vault.markDirtyAndSync();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.SUCCESS;
    }
}

