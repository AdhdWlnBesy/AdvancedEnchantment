package com.advanced_enchants.gui;

import com.advanced_enchants.datagen.AdvancementProvider;
import com.advanced_enchants.register.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;

/**
 * 高级砂轮界面逻辑。
 *
 * @see GrindstoneScreen
 */
public class GrindstoneScreenHandler extends ScreenHandler {
    public final Inventory inventory;
    private final PlayerEntity player;

    public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandler.GRINDSTONE_TYPE, syncId);
        inventory = new SimpleInventory(3);
        player = playerInventory.player;
        inventory.onOpen(playerInventory.player);
        addSlot(new Slot(inventory, 0, 49, 19) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return SmithingScreenHandler.canEnchanted(stack) || stack.isIn(ModTags.ADVANCED_ENCHANTS_SCROLL);
            }

            @Override
            public void markDirty() {
                update();
                super.markDirty();
            }
        });

        addSlot(new Slot(inventory, 1, 49, 40) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.EMPTY_SCROLL) || stack.isIn(ModTags.MINERAL);
            }

            @Override
            public void markDirty() {
                update();
                super.markDirty();
            }
        });

        addSlot(new Slot(inventory, 2, 129, 34) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                takeItem();
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
        if (slot1.isOf(ModItems.ORDINARY_ENCHANTS_SCROLL))
            inventory.setStack(2, ModItems.ORDINARY_ENCHANTS_BOOK.getDefaultStack());
        if (slot1.isOf(ModItems.ADVANCED_ENCHANTS_SCROLL))
            inventory.setStack(2, ModItems.ADVANCED_ENCHANTS_BOOK.getDefaultStack());
        if (slot1.isOf(ModItems.RARE_ENCHANTS_SCROLL))
            inventory.setStack(2, ModItems.RARE_ENCHANTS_BOOK.getDefaultStack());
        if (slot1.isOf(ModItems.PRECIOUS_ENCHANTS_SCROLL))
            inventory.setStack(2, ModItems.PRECIOUS_ENCHANTS_BOOK.getDefaultStack());
        if (inventory.getStack(1).isOf(ModItems.EMPTY_SCROLL)) {
            ItemStack returnValue = ModItems.ENCHANTED_SCROLL.getDefaultStack();
            slot1.getEnchantments().getEnchantments().forEach(enchantment ->
                    returnValue.addEnchantment(enchantment, slot1.getEnchantments().getLevel(enchantment)));
            inventory.setStack(2, returnValue);
        }
    }

    // 输入不满足配方时禁止取出。
    private boolean notCanCraft() {
        if (player.getWorld().isClient()) return true;
        if (player.experienceLevel < 3) return true;
        ItemStack slot1 = inventory.getStack(0);
        ItemStack slot2 = inventory.getStack(1);
        if (slot1.isEmpty() || slot2.isEmpty()) return true;
        if (slot1.isIn(ModTags.ADVANCED_ENCHANTS_SCROLL) && slot2.isIn(ModTags.MINERAL)) {
            boolean match = slot1.isOf(ModItems.ORDINARY_ENCHANTS_SCROLL) && slot2.isOf(Items.IRON_INGOT);
            if (slot1.isOf(ModItems.ADVANCED_ENCHANTS_SCROLL) && slot2.isOf(Items.GOLD_INGOT)) match = true;
            if (slot1.isOf(ModItems.RARE_ENCHANTS_SCROLL) && slot2.isOf(Items.EMERALD)) match = true;
            if (slot1.isOf(ModItems.PRECIOUS_ENCHANTS_SCROLL) && slot2.isOf(Items.DIAMOND)) match = true;
            if (match) {
                return Math.min(ModAPI.getEnchantmentsLevel(slot1) * 3, 64) > slot2.getCount();
            }
        } else if (slot2.isOf(ModItems.EMPTY_SCROLL)) {
            return !slot1.hasEnchantments();
        }
        return true;
    }

    // 取出结果并消耗输入。
    private void takeItem() {
        if (notCanCraft()) return;
        ItemStack slot0 = inventory.getStack(0);
        if (SmithingScreenHandler.canEnchanted(slot0)) {
            inventory.removeStack(1, 1);
        } else {
            int i = ModEnchantments.getEnchantmentLevelType(new ArrayList<>(slot0.getEnchantments().getEnchantments()).getFirst().getKey().orElseThrow()).level;
            inventory.removeStack(1, Math.min(ModAPI.getEnchantmentsLevel(slot0) * i, 64));
            if (player instanceof ServerPlayerEntity serverPlayer) {
                AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "reset_enchants_scroll");
            }
        }
        if (player instanceof ServerPlayerEntity serverPlayer)
            serverPlayer.setExperienceLevel(player.experienceLevel - 2);
        inventory.removeStack(0, 1);
        inventory.setStack(2, ItemStack.EMPTY);
        ModAPI.playSoundsToPlayer(SoundEvents.BLOCK_GRINDSTONE_USE, player);
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
                takeItem();
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
