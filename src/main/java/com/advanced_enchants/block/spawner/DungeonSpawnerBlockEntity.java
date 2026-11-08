package com.advanced_enchants.block.spawner;

import com.advanced_enchants.block.DungeonBlock;
import com.advanced_enchants.entity.DungeonCreeper;
import com.advanced_enchants.entity.DungeonEnderman;
import com.advanced_enchants.entity.DungeonShulker;
import com.advanced_enchants.entity.duckInterface.DungeonEntityInterface;
import com.advanced_enchants.entity.duckInterface.DungeonTeleportInterface;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModBlockEntityType;
import com.advanced_enchants.register.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 地牢刷怪笼方块实体，负责刷怪与波次。
 *
 * @see com.advanced_enchants.block.spawner.DungeonSpawnerBlock
 */

public class DungeonSpawnerBlockEntity extends BlockEntity {
    public DungeonSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.DUNGEON_SPAWNER, pos, state);
    }

    public boolean isBooting;   //是否启动
    public boolean cooling;     //一个锁，让刷怪笼只弹出一个钥匙
    public byte spawnWaveNumber; //刷怪波数
    public List<UUID> livingEntities = new ArrayList<>();//存储刷的怪，自动更新
    private short tick; //控制刷怪时间，一个计时器

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putBoolean("IsBooting", isBooting);
        nbt.putByte("SpawnWaveNumber", spawnWaveNumber);
        nbt.putBoolean("Cooling", cooling);
        nbt.putInt("Tick", tick);

        if (livingEntities != null) {
            NbtList uuidList = new NbtList();
            for (UUID uuid : livingEntities) {
                NbtCompound uuidTag = new NbtCompound();
                uuidTag.putUuid("Id", uuid);
                uuidList.add(uuidTag);
            }
            nbt.put("MobUuids", uuidList);
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        isBooting = nbt.getBoolean("IsBooting");
        spawnWaveNumber = nbt.getByte("SpawnWaveNumber");
        cooling = nbt.getBoolean("Cooling");
        tick = nbt.getShort("Tick");

        List<UUID> newLivingEntities = new ArrayList<>();
        NbtList uuidList = nbt.getList("MobUuids", NbtCompound.COMPOUND_TYPE);
        for (int i = 0; i < uuidList.size(); i++) {
            newLivingEntities.add(uuidList.getCompound(i).getUuid("Id"));
        }
        livingEntities = newLivingEntities;
    }

    private BlockPos getSafeSpawnPos(World world) {
        Block block1 = world.getBlockState(pos.down()).getBlock();
        Block block2 = world.getBlockState(pos.down(2)).getBlock();
        for (int i = 0; i < 5; i++) {
            int x = pos.getX() + world.random.nextBetween(-4, 4);
            int z = pos.getZ() + world.random.nextBetween(-4, 4);
            for (int y = pos.getY() - 1; y > pos.getY() - 5; y--) {
                BlockPos blockPos = new BlockPos(x, y, z);
                BlockState blockState = world.getBlockState(blockPos);
                if ((blockState.isOf(block1) || blockState.isOf(block2)) && world.getBlockState(blockPos.up()).isAir()) {
                    return blockPos.up();
                }
            }
        }
        return pos.up();
    }

    // 按地牢层数和刷怪笼类型创建怪物，再统一加装备和属性。
    private MobEntity createSpawnerEntity(BlockState state) {
        if (world == null || world.getServer() == null) return null;
        MobEntity entity = null;
        int speed = 0;
        int resistance = 0;
        int strength = 0;

        switch (state.get(DungeonBlock.DUNGEON_LEVEL)) {
            case 1 -> {
                // 第一层：僵尸、骷髅或苦力怕。
                switch (state.get(DungeonSpawnerBlock.SPAWNER_TYPE)) {
                    case 1 -> {
                        entity = new ZombieEntity(EntityType.ZOMBIE, world);
                        ItemStack weapon = new ItemStack(Items.STONE_SWORD);
                        if (entity.getRandom().nextBoolean()) {
                            weapon = new ItemStack(Items.GOLDEN_SWORD);
                        }
                        weapon.addEnchantment(ModAPI.keyToEntry(Enchantments.SHARPNESS, world.getServer()), 1);
                        entity.equipStack(EquipmentSlot.MAINHAND, weapon);
                        if (entity.getRandom().nextBoolean()) {
                            entity.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
                        } else {
                            entity.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
                        }
                        if (entity.getRandom().nextBoolean()) {
                            entity.equipStack(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
                        } else {
                            entity.equipStack(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
                        }
                        setRandomHealth(entity, 20, 50);
                        speed = entity.getRandom().nextFloat() < 0.4 ? 2 : 1;
                    }
                    case 2 -> {
                        entity = new SkeletonEntity(EntityType.SKELETON, world);
                        ItemStack weapon = new ItemStack(Items.BOW);
                        weapon.addEnchantment(ModAPI.keyToEntry(Enchantments.POWER, world.getServer()), 1);
                        entity.equipStack(EquipmentSlot.MAINHAND, weapon);
                        if (entity.getRandom().nextBoolean()) {
                            entity.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
                        } else {
                            entity.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
                        }
                        if (entity.getRandom().nextBoolean()) {
                            entity.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
                        } else {
                            entity.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
                        }
                        setRandomHealth(entity, 25, 45);
                    }
                    default -> {
                        entity = new DungeonCreeper(EntityType.CREEPER, world);
                        resistance = 1;
                    }
                }
            }
            case 2 -> {
                // 第二层：蜘蛛、掠夺者或唤魔者。
                switch (state.get(DungeonSpawnerBlock.SPAWNER_TYPE)) {
                    case 1 -> {
                        entity = new SpiderEntity(EntityType.SPIDER, world);
                        if (entity.getRandom().nextBoolean()) {
                            entity = new CaveSpiderEntity(EntityType.CAVE_SPIDER, world);
                        }
                        setRandomHealth(entity, 36, 55);
                        strength = 3;
                        speed = 1;
                    }
                    case 2 -> {
                        entity = new PillagerEntity(EntityType.PILLAGER, world);
                        ItemStack tool = new ItemStack(Items.CROSSBOW);
                        tool.addEnchantment(ModAPI.keyToEntry(Enchantments.QUICK_CHARGE, world.getServer()), 3);
                        entity.setStackInHand(Hand.MAIN_HAND, tool);
                        speed = 1;
                    }
                    default -> {
                        entity = new EvokerEntity(EntityType.EVOKER, world);
                        setRandomHealth(entity, 30, 45);
                    }
                }
                resistance = 2;
            }
            case 3 -> {
                // 第三层：铁傀儡、卫道士，或流浪者/微风。
                switch (state.get(DungeonSpawnerBlock.SPAWNER_TYPE)) {
                    case 1 -> {
                        entity = new IronGolemEntity(EntityType.IRON_GOLEM, world);
                        speed = 1;
                    }
                    case 2 -> {
                        entity = new VindicatorEntity(EntityType.VINDICATOR, world);
                        speed = 1;
                        resistance = 1;
                        strength = world.random.nextBoolean() ? 1 : 0;
                        ItemStack weapon = new ItemStack(Items.IRON_AXE);
                        weapon.addEnchantment(ModAPI.keyToEntry(Enchantments.SHARPNESS, world.getServer()), 2);
                        entity.setStackInHand(Hand.MAIN_HAND, weapon);
                        setRandomHealth(entity, 50, 60);
                    }
                    case 3 -> {
                        if (world.random.nextBoolean()) {
                            entity = new StrayEntity(EntityType.STRAY, world);
                            ItemStack bow = Items.BOW.getDefaultStack();
                            bow.addEnchantment(ModAPI.keyToEntry(Enchantments.POWER, world.getServer()), 5);
                            bow.addEnchantment(ModAPI.keyToEntry(Enchantments.FLAME, world.getServer()), 1);
                            bow.addEnchantment(ModAPI.keyToEntry(Enchantments.PUNCH, world.getServer()), 1);
                            entity.setStackInHand(Hand.MAIN_HAND, bow);
                            speed = 1;
                            resistance = 2;
                            setRandomHealth(entity, 45, 70);
                        } else {
                            entity = new BreezeEntity(EntityType.BREEZE, world);
                            speed = 1;
                            resistance = 2;
                            setRandomHealth(entity, 45, 56);
                        }
                    }
                }
            }
            case 4 -> {
                // 第四层：凋灵骷髅、猪灵蛮兵/疣猪兽，或烈焰人。
                switch (state.get(DungeonSpawnerBlock.SPAWNER_TYPE)) {
                    case 1 -> {
                        entity = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);
                        ItemStack sword = Items.NETHERITE_SWORD.getDefaultStack();
                        sword.addEnchantment(ModAPI.keyToEntry(Enchantments.SHARPNESS, world.getServer()), 5);
                        sword.addEnchantment(ModAPI.keyToEntry(Enchantments.FIRE_ASPECT, world.getServer()), 2);
                        sword.addEnchantment(ModAPI.keyToEntry(Enchantments.KNOCKBACK, world.getServer()), 2);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, -1, 0));
                        entity.setStackInHand(Hand.MAIN_HAND, sword);
                        speed = 2;
                        resistance = 2;
                        strength = 3;
                        setRandomHealth(entity, 60, 80);
                    }
                    case 2 -> {
                        if (world.random.nextBoolean()) {
                            entity = new PiglinBruteEntity(EntityType.PIGLIN_BRUTE, world);
                            ItemStack axe = Items.DIAMOND_AXE.getDefaultStack();
                            axe.addEnchantment(ModAPI.keyToEntry(Enchantments.SHARPNESS, world.getServer()), 3);
                            axe.addEnchantment(ModAPI.keyToEntry(Enchantments.FIRE_ASPECT, world.getServer()), 2);
                            axe.addEnchantment(ModAPI.keyToEntry(Enchantments.KNOCKBACK, world.getServer()), 2);
                            entity.setStackInHand(Hand.MAIN_HAND, axe);
                            speed = 1;
                            resistance = 3;
                            strength = 2;
                            setRandomHealth(entity, 50, 70);
                        } else {
                            entity = new HoglinEntity(EntityType.HOGLIN, world);
                            speed = 1;
                            resistance = 3;
                            strength = 3;
                            setRandomHealth(entity, 50, 80);
                        }
                    }
                    default -> {
                        entity = new BlazeEntity(EntityType.BLAZE, world);
                        resistance = 2;
                        setRandomHealth(entity, 60, 90);
                    }
                }
            }
            case 5 -> {
                // 第五层：潜影贝、末影人或监守者。
                switch (state.get(DungeonSpawnerBlock.SPAWNER_TYPE)) {
                    case 1 -> {
                        entity = new DungeonShulker(world);
                        setRandomHealth(entity, 100, 150);
                        resistance = 2;
                    }
                    case 2 -> {
                        entity = new DungeonEnderman(world);
                        setRandomHealth(entity, 90, 140);
                        resistance = 3;
                        speed = 3;
                        strength = 3;
                    }
                    default -> {
                        WardenEntity warden = new WardenEntity(EntityType.WARDEN, world);
                        warden.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE);
                        setRandomHealth(warden, 200, 260);
                        resistance = 2;
                        entity = warden;
                    }
                }
            }
        }

        if (entity == null) return null;
        // 统一加状态效果、掉落和地牢标记。
        if (speed > 0) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, speed - 1));
        }
        if (resistance > 0) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, resistance - 1));
        }
        if (strength > 0) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, strength - 1));
        }
        entity.setCanPickUpLoot(false);
        entity.setPersistent();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            entity.setEquipmentDropChance(slot, 0);
        }
        if (entity instanceof DungeonEntityInterface dungeonEntity) {
            dungeonEntity.advancedEnchantment$setIsDungeonEntity();
        }
        if (entity instanceof DungeonTeleportInterface teleportEntity) {
            teleportEntity.advancedEnchantment$blockTeleport();
        }
        return entity;
    }

    public static void setRandomHealth(LivingEntity entity, int min, int max) {
        ModAPI.setMaxHealth(entity, entity.getWorld().getRandom().nextBetween(min, max));
    }

    public void reset() { //重置
        isBooting = false;
        cooling = false;
        spawnWaveNumber = 0;
        tick = 0;
        livingEntities.clear();
        markDirty();
    }

    public List<LivingEntity> refreshLivingEntities() { //刷新怪，剔除死掉的
        List<LivingEntity> returnLivingEntities = new ArrayList<>();
        if (!(world instanceof ServerWorld serverWorld)) return returnLivingEntities;
        livingEntities.removeIf(uuid -> {
            if (serverWorld.getEntity(uuid) instanceof LivingEntity living && living.isAlive() && !living.isRemoved()) {
                returnLivingEntities.add(living);
                return false;
            }
            return true;
        });
        markDirty();
        return returnLivingEntities;
    }

    private int getSpawnCount(BlockState state, World world) { //获取单次刷怪量
        int type = state.get(DungeonSpawnerBlock.SPAWNER_TYPE);
        return switch (state.get(DungeonBlock.DUNGEON_LEVEL)) {
            case 1 -> switch (type) {
                case 2, 4 -> 2;
                default -> 1;
            };
            case 2 -> switch (type) {
                case 1, 2 -> 2;
                default -> 1;
            };
            case 3, 4 -> world.random.nextFloat() > 0.3f ? 1 : 2;
            case 5 -> type == 2 ? 2 : 1;
            default -> 1;
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, DungeonSpawnerBlockEntity spawner) {
        if (spawner.isBooting) {
            int dungeonLevel = state.get(DungeonBlock.DUNGEON_LEVEL);
            if ((spawner.tick % 900 == 0 || spawner.spawnWaveNumber == 0) && spawner.spawnWaveNumber < 10) {
                for (int i = 0; i < spawner.getSpawnCount(state, world); i++) {
                    MobEntity spawnerEntity = spawner.createSpawnerEntity(state);
                    if (spawnerEntity == null) return;
                    BlockPos safePos = spawner.getSafeSpawnPos(world);
                    spawnerEntity.refreshPositionAndAngles(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5, 0, 0);
                    world.spawnEntity(spawnerEntity);
                    spawner.livingEntities.add(spawnerEntity.getUuid());
                }
                spawner.spawnWaveNumber++;
                spawner.markDirty();
            }

            if (spawner.tick % 10 == 0) {
                spawner.refreshLivingEntities();
            }
            //弹出钥匙
            if (spawner.livingEntities.isEmpty() && spawner.spawnWaveNumber >= 10 && !spawner.cooling) {
                for (int i = 0; i < ((dungeonLevel) == 2 ? 2 : 1); i++) {
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), switch (dungeonLevel) {
                        case 1 -> ModItems.DUNGEON1_KEY.getDefaultStack();
                        case 2 -> ModItems.DUNGEON2_KEY.getDefaultStack();
                        case 3 -> ModItems.DUNGEON3_KEY.getDefaultStack();
                        case 4 -> ModItems.DUNGEON4_KEY.getDefaultStack();
                        case 5 -> ModItems.DUNGEON5_KEY.getDefaultStack();
                        default -> ItemStack.EMPTY;
                    }));
                }
                ModAPI.playSoundsToBlock(SoundEvents.BLOCK_VAULT_EJECT_ITEM, world, pos.up(), 1);
                spawner.cooling = true;
            }
            spawner.tick++;
        }
    }
}
