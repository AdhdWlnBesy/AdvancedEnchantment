package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.block.broadcast.DungeonBroadcastBlockEntity;
import com.advanced_enchants.block.spawner.DungeonSpawnerBlockEntity;
import com.advanced_enchants.block.vault.DungeonVaultBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * 方块实体类型注册。
 *
 * @see DungeonVaultBlockEntity
 */
public class ModBlockEntityType {
    public static final BlockEntityType<DungeonVaultBlockEntity> DUNGEON_VAULT =
            register("dungeon_vault_be", BlockEntityType.Builder.create(DungeonVaultBlockEntity::new, ModBlocks.DUNGEON_VAULT));

    public static final BlockEntityType<DungeonSpawnerBlockEntity> DUNGEON_SPAWNER =
            register("dungeon_spawner_be", BlockEntityType.Builder.create(DungeonSpawnerBlockEntity::new, ModBlocks.DUNGEON_SPAWNER));

    public static final BlockEntityType<DungeonBroadcastBlockEntity> DUNGEON_BROADCAST =
            register("dungeon_broadcast_be", BlockEntityType.Builder.create(DungeonBroadcastBlockEntity::new, ModBlocks.DUNGEON_BROADCAST));

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.Builder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(AdvancedEnchantment.MOD_ID, id), builder.build());
    }

    public static void loadBlockEntityType() {
        AdvancedEnchantment.LOGGER.info("Loading block entity type successfully!");
    }
}
