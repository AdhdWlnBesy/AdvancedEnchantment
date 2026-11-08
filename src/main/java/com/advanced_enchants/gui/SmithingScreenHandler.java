package com.advanced_enchants.gui;

import com.advanced_enchants.datagen.AdvancementProvider;
import com.advanced_enchants.enchantment.EnchantmentTypes;
import com.advanced_enchants.entity.duckInterface.PlayerInterface;
import com.advanced_enchants.event.SmithingNetWorking;
import com.advanced_enchants.register.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;

import java.util.Objects;

/**
 * 高级锻造台界面逻辑。
 *
 * @see com.advanced_enchants.gui.SmithingScreen
 */
public class SmithingScreenHandler extends ScreenHandler {
    public final Inventory inventory;
    public boolean isFail;
    public final PlayerEntity player;
    private ItemStack finishedProduct = ItemStack.EMPTY;

    public SmithingScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandler.SMITHING_TYPE, syncId);
        inventory = new SimpleInventory(4);
        player = playerInventory.player;
        inventory.onOpen(playerInventory.player);
        addSlot(new Slot(inventory, 0, 27, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return canEnchanted(stack);
            }

            @Override
            public void markDirty() {
                super.markDirty();
                update();
            }
        });

        addSlot(new Slot(inventory, 1, 51, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ModTags.MINERAL) || stack.isOf(Items.NETHERITE_INGOT);
            }

            @Override
            public void markDirty() {
                super.markDirty();
                update();
            }
        });

        addSlot(new Slot(inventory, 2, 76, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ModTags.ADVANCED_ENCHANTS_SCROLL);
            }

            @Override
            public void markDirty() {
                super.markDirty();
                update();
            }
        });

        addSlot(new Slot(inventory, 3, 134, 47) {
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
        if (player.getWorld().isClient()) return;
        if (notCanCraft()) {
            inventory.setStack(3, ItemStack.EMPTY);
            return;
        }
        ItemStack oldItem = inventory.getStack(0);
        ItemStack scroll = inventory.getStack(2);
        var enchantments = scroll.getEnchantments().getEnchantments();
        if (enchantments.isEmpty()) return;
        ItemStack result = oldItem.copy();
        for (var enchantment : enchantments) {
            if (Objects.equals(enchantment.getKey().orElse(null), ModEnchantments.FREEZE) && EnchantmentHelper.getLevel(enchantment, scroll) > 1) {
                if (player.getServer() == null) continue;
                if (ModAPI.hasEnchantment(Enchantments.KNOCKBACK, player.getServer(), oldItem)) {
                    ModAPI.removeEnchantment(ModAPI.keyToEntry(Enchantments.KNOCKBACK, player.getServer()), result);
                } else if (ModAPI.hasEnchantment(ModEnchantments.SHOVE, player.getServer(), oldItem)) {
                    ModAPI.removeEnchantment(ModAPI.keyToEntry(ModEnchantments.SHOVE, player.getServer()), result);
                }
            } else {
                addEnchantment(result, enchantment, scroll.getEnchantments().getLevel(enchantment));
            }
        }
        result.set(ModDataComponentTypes.ADVANCED_SLOTS, ModDataComponentTypes.getSlots(result) - ModEnchantments.getAdvancedEnchantmentsCount(enchantments));
        inventory.setStack(3, result);
        finishedProduct = result.copy();
    }

    // 输入不满足配方时禁止取出。
    private boolean notCanCraft() {
        ItemStack oldItem = inventory.getStack(0);
        ItemStack mineral = inventory.getStack(1);
        ItemStack scroll = inventory.getStack(2);
        if (oldItem.isEmpty() || scroll.isEmpty()) return true;
        var enchantments = scroll.getEnchantments().getEnchantments();

        if (enchantments.isEmpty()) return true;
        if (ModDataComponentTypes.getSlots(oldItem) == 0
                || ModDataComponentTypes.getSlots(oldItem) < ModEnchantments.getAdvancedEnchantmentsCount(enchantments)) {
            return true;
        }
        boolean canApply = true;
        int level = 0;
        for (var enchantment : enchantments) {
            if (!enchantment.value().isAcceptableItem(oldItem)) canApply = false;
            level += scroll.getEnchantments().getLevel(enchantment);
        }
        if (player.isCreative()) return !canApply || player.getWorld().isClient();

        if (mineral.isEmpty()) return true;
        if (scroll.isOf(ModItems.ORDINARY_ENCHANTS_SCROLL) && !mineral.isOf(Items.IRON_INGOT)) return false;
        if (scroll.isOf(ModItems.ADVANCED_ENCHANTS_SCROLL) && !mineral.isOf(Items.GOLD_INGOT)) return true;
        if (scroll.isOf(ModItems.RARE_ENCHANTS_SCROLL) && !mineral.isOf(Items.EMERALD)) return true;
        if (scroll.isOf(ModItems.PRECIOUS_ENCHANTS_SCROLL) && !mineral.isOf(Items.DIAMOND)) return true;
        if (scroll.isOf(ModItems.ENCHANTED_SCROLL) && !mineral.isOf(Items.NETHERITE_INGOT)) return true;

        boolean hasEnoughMineral = mineral.getCount() >= level || scroll.isOf(ModItems.ENCHANTED_SCROLL);
        return !canApply || !hasEnoughMineral || player.getWorld().isClient();
    }

    // 取出结果并消耗输入。
    private void takeItem() {
        if (notCanCraft()) return;
        float chance = inventory.getStack(2).isOf(ModItems.ENCHANTED_SCROLL) ? 0.85f : 0.65f;
        boolean susses = player.getWorld().getRandom().nextFloat() < chance || player.isCreative();
        if (susses) {
            inventory.removeStack(0, 1);
            ModAPI.playSoundsToPlayer(SoundEvents.BLOCK_ANVIL_USE, player);
            if (player instanceof ServerPlayerEntity serverPlayer) {
                AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "smithing_success");
                if (ModDataComponentTypes.getSlots(finishedProduct) == 0) {
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "smithing_success_full");
                }
                int p = 0, r = 0;
                for (RegistryEntry<Enchantment> entry : finishedProduct.getEnchantments().getEnchantments()) {
                    EnchantmentTypes types = ModEnchantments.getEnchantmentLevelType(entry.getKey().orElse(null));
                    if (types.equals(EnchantmentTypes.PRECIOUS)) {
                        p++;
                    } else if (types.equals(EnchantmentTypes.RARE)) {
                        r++;
                    }
                }
                if (p >= 4 && r >= 3) {
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "smithing_success_all_full");
                }
                if (inventory.getStack(2).isOf(ModItems.ENCHANTED_SCROLL)) {
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "enchanted_enchants_scroll");
                }
            }
        } else {
            ModAPI.playSoundsToPlayer(SoundEvents.BLOCK_ANVIL_DESTROY, player);
            player.currentScreenHandler.setCursorStack(ItemStack.EMPTY);
            ((PlayerInterface) player).advancedEnchantment$addSmithingCount();
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack s = player.getInventory().getStack(i);
                if (ItemStack.areEqual(s, inventory.getStack(0))) s.setCount(0);
            }
            if (player instanceof ServerPlayerEntity serverPlayer) {
                AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "smithing_fail");
                if (inventory.getStack(2).isOf(ModItems.PRECIOUS_ENCHANTS_SCROLL)) {
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "smithing_precious_fail");
                }
                if (((PlayerInterface) player).advancedEnchantment$getSmithingCount() >= 10) {
                    AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "smithing_multiple_fail");
                }
            }
        }
        inventory.removeStack(1, ModAPI.getEnchantmentsLevel(inventory.getStack(2)));
        inventory.removeStack(2, 1);
        isFail = !susses;
        SmithingNetWorking.sendSmithStatus(player, susses, player.getWorld().getTime());
        inventory.setStack(3, ItemStack.EMPTY);
    }

    @Override
// 处理 Shift 点击的槽位移动。
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack returnItemStack = ItemStack.EMPTY;
        Slot slot = slots.get(slotIndex);
        if (slot.hasStack()) {
            ItemStack itemStack = slot.getStack();
            returnItemStack = itemStack.copy();
            if (slotIndex < 3) {
                if (!this.insertItem(itemStack, 3, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex == 3) {
                takeItem();
                if (isFail) {
                    return ItemStack.EMPTY;
                }
                if (!this.insertItem(itemStack, 4, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.insertItem(itemStack, 0, 3, false)) {
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
        for (int i = 0; i < 3; i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (!player.giveItemStack(stack)) {
                    player.dropItem(stack, false);
                }
                inventory.setStack(i, ItemStack.EMPTY);
            }
        }
    }

    // 判断物品是否可以接受附魔。
    public static boolean canEnchanted(ItemStack stack) {
        boolean bl = stack.isIn(ItemTags.TRIMMABLE_ARMOR) || stack.isIn(ModTags.ENCHANTABLE_BOW)
                || stack.isIn(ItemTags.MINING_ENCHANTABLE) || stack.isIn(ItemTags.SHARP_WEAPON_ENCHANTABLE);
        if (stack.isOf(Items.FISHING_ROD) || stack.isOf(Items.SHIELD) || stack.isOf(Items.ELYTRA)) return true;
        return bl && !stack.isIn(ModTags.ADVANCED_ENCHANTS_SCROLL);
    }

    // 给物品追加一条附魔。
    public static ItemStack addEnchantment(ItemStack stack, RegistryEntry<Enchantment> entry, int level) {
        EnchantmentHelper.apply(stack, builder -> builder.add(entry, builder.getLevel(entry) + level));
        return stack;
    }
}
