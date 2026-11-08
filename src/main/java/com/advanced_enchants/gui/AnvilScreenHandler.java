package com.advanced_enchants.gui;

import com.advanced_enchants.datagen.AdvancementProvider;
import com.advanced_enchants.register.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;

/**
 * 高级铁砧界面逻辑。
 *
 * @see AnvilScreen
 */
public class AnvilScreenHandler extends ScreenHandler {
    public final Inventory inventory;
    private final PlayerEntity player;
    private int levelCount;

    public AnvilScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandler.ANVIL_TYPE, syncId);
        inventory = new SimpleInventory(3);
        player = playerInventory.player;
        inventory.onOpen(playerInventory.player);
        addSlot(new Slot(inventory, 0, 27, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return SmithingScreenHandler.canEnchanted(stack) || stack.isIn(ModTags.ADVANCED_ENCHANTS_SCROLL);
            }

            @Override
            public void markDirty() {
                super.markDirty();
                update();
            }
        });

        addSlot(new Slot(inventory, 1, 76, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ModTags.LEVELUP_BALL) || stack.isOf(Items.ENDER_EYE) || stack.isIn(ModTags.SLOT_BUILDER);
            }

            @Override
            public void markDirty() {
                super.markDirty();
                update();
            }
        });

        addSlot(new Slot(inventory, 2, 134, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                takeItem(ItemStack.EMPTY);
                super.onTakeItem(player, stack);
            }
        });

        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    // 根据输入槽刷新预览结果。
    private void update() {
        if (notCanCraft()) {
            inventory.setStack(2, ItemStack.EMPTY);
            return;
        }
        ItemStack slot1 = inventory.getStack(0);
        ItemStack slot2 = inventory.getStack(1);
        ItemStack result = ItemStack.EMPTY;
        if (slot2.isIn(ModTags.LEVELUP_BALL) || slot2.isOf(Items.ENDER_EYE)) {
            boolean hasUpgrade = false;
            int levelCount;
            var enchantment = slot1.getEnchantments().getEnchantments();
            ItemStack output = slot1.copy();
            for (RegistryEntry<Enchantment> entry : enchantment) {
                if (!slot2.isOf(ModEnchantments.getEnchantmentLevelType(entry.getKey().orElse(null)).levelUpBall)) {
                    continue;
                }
                int enchantmentLevel = EnchantmentHelper.getLevel(entry, slot1);
                int level = enchantmentLevel + slot2.getCount();
                int maxLevel = entry.value().getMaxLevel();
                if (enchantmentLevel == maxLevel) {
                    continue;
                }
                if (level > maxLevel) {
                    levelCount = entry.value().getMaxLevel() - enchantmentLevel;
                    level = entry.value().getMaxLevel();
                } else {
                    levelCount = level;
                }
                if (this.levelCount < levelCount) {
                    this.levelCount = levelCount;
                }
                output.addEnchantment(entry, level);
                hasUpgrade = true;
            }
            result = (hasUpgrade) ? output : ItemStack.EMPTY;
        } else if (slot2.isIn(ModTags.SLOT_BUILDER)) {
            ItemStack output = slot1.copy();
            int slotCount = 0;
            int slot = ModDataComponentTypes.getSlots(slot1);
            int max = ModDataComponentTypes.getMaxSlot(slot1);
            if (slot >= max) {
                inventory.setStack(2, ItemStack.EMPTY);
                return;
            }
            if (slot2.isOf(ModItems.LEVEL1_SLOT_BUILDER)) slotCount = 1;
            else if (slot2.isOf(ModItems.LEVEL2_SLOT_BUILDER)) slotCount = 2;
            else if (slot2.isOf(ModItems.LEVEL3_SLOT_BUILDER)) slotCount = 3;
            output.set(ModDataComponentTypes.ADVANCED_SLOTS, Math.min(max, slot + slotCount));
            result = output;
        }
        inventory.setStack(2, result);
    }

    // 输入不满足配方时禁止取出。
    private boolean notCanCraft() {
        if (player.getWorld().isClient()) return true;
        ItemStack slot1 = inventory.getStack(0);
        ItemStack slot2 = inventory.getStack(1);
        if (slot1.isEmpty() || slot2.isEmpty()) return true;
        if (slot2.isIn(ModTags.LEVELUP_BALL) || slot2.isOf(Items.ENDER_EYE) || slot2.isIn(ModTags.SLOT_BUILDER)) {
            if (slot1.isOf(ModItems.ORDINARY_ENCHANTS_SCROLL) && slot2.isOf(ModItems.ORDINARY_LEVELUP_BALL))
                return false;
            else if (slot1.isOf(ModItems.ADVANCED_ENCHANTS_SCROLL) && slot2.isOf(ModItems.ADVANCED_LEVELUP_BALL))
                return false;
            else if (slot1.isOf(ModItems.RARE_ENCHANTS_SCROLL) && slot2.isOf(ModItems.RARE_LEVELUP_BALL)) return false;
            else if (slot1.isOf(ModItems.PRECIOUS_ENCHANTS_SCROLL) && slot2.isOf(ModItems.PRECIOUS_LEVELUP_BALL))
                return false;
            else if (slot1.isOf(Items.ENCHANTED_BOOK) && slot2.isOf(Items.ENDER_EYE)) return false;
            else return !SmithingScreenHandler.canEnchanted(slot1);
        }
        return true;
    }

    // 取出结果并消耗输入。
    private void takeItem(ItemStack stack) {
        if (notCanCraft()) return;
        if (inventory.getStack(1).isIn(ModTags.SLOT_BUILDER)) {
            inventory.removeStack(1, 1);
            if (player instanceof ServerPlayerEntity serverPlayer) {
                AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "slot_builder_levelup");
                if (stack.isEmpty()) stack = player.currentScreenHandler.getCursorStack();
                int max = ModDataComponentTypes.getMaxSlot(stack);
                if (ModDataComponentTypes.getAllSlots(stack) == max && max >= 6) {
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "slot_builder_levelup_full");
                }
            }
        } else {
            inventory.removeStack(1, levelCount);
            if (player instanceof ServerPlayerEntity serverPlayer) {
                AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "levelup_enchant");
            }
        }
        inventory.removeStack(0, 1);
        inventory.setStack(2, ItemStack.EMPTY);
        ModAPI.playSoundsToPlayer(SoundEvents.BLOCK_ANVIL_USE, player);
    }

    @Override
// 处理 Shift 点击的槽位移动。
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack returnItemStack = ItemStack.EMPTY;
        Slot slot = slots.get(slotIndex);
        if (slot.hasStack()) {
            ItemStack itemStack = slot.getStack();
            returnItemStack = itemStack.copy();
            if (slotIndex < 2) {
                if (!this.insertItem(itemStack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex == 2) {
                takeItem(itemStack);
                if (!this.insertItem(itemStack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.insertItem(itemStack, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (itemStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return returnItemStack;
    }

    @Override
// 玩家是否仍能使用该界面。
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
// 关闭界面时归还输入。
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        if (player.getWorld().isClient) {
            return;
        }
        for (int i = 0; i < 2; i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (!player.giveItemStack(stack)) {
                    player.dropItem(stack, false);
                }
                inventory.setStack(i, ItemStack.EMPTY);
            }
        }
    }
}
