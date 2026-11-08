package com.advanced_enchants.event;

import com.advanced_enchants.entity.duckInterface.PlayerInterface;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import com.advanced_enchants.register.ModItems;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;

import java.util.List;
import java.util.Random;

/**
 * 附魔书转卷轴的交互逻辑。
 *
 * @see com.advanced_enchants.register.ModItems
 */
public class BookToScroll {//将附魔书转换为卷轴

    // 注册附魔书转卷轴的交互。
    public static void mainEvent() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack held = player.getStackInHand(hand);
            if (!world.isClient()) {//仅在服务端执行逻辑
                if (player.getItemCooldownManager().isCoolingDown(held.getItem())) {
                    return TypedActionResult.pass(held); //如果物品处于冷却状态不执行内容
                }

                Item scroll = null;//变量对应转换卷轴
                Formatting color = null;//变量对应文本输出颜色
                List<RegistryKey<Enchantment>> enchantment_list = List.of();

                //判断点击是什么附魔书，并设置变量值
                if (held.isOf(ModItems.ORDINARY_ENCHANTS_BOOK)) {
                    scroll = ModItems.ORDINARY_ENCHANTS_SCROLL;
                    enchantment_list = ModEnchantments.ORDINARY_LIST;
                    color = Formatting.GREEN;
                } else if (held.isOf(ModItems.ADVANCED_ENCHANTS_BOOK)) {
                    scroll = ModItems.ADVANCED_ENCHANTS_SCROLL;
                    enchantment_list = ModEnchantments.ADVANCED_LIST;
                    color = Formatting.YELLOW;
                } else if (held.isOf(ModItems.RARE_ENCHANTS_BOOK)) {
                    scroll = ModItems.RARE_ENCHANTS_SCROLL;
                    enchantment_list = ModEnchantments.RARE_LIST;
                    color = Formatting.AQUA;
                } else if (held.isOf(ModItems.PRECIOUS_ENCHANTS_BOOK)) {
                    scroll = ModItems.PRECIOUS_ENCHANTS_SCROLL;
                    enchantment_list = ModEnchantments.PRECIOUS_LIST;
                    color = Formatting.DARK_PURPLE;
                }

                //如果需要转换的卷轴有目标，执行转换程序
                if (scroll != null && player.getServer() != null) {
                    held.decrement(1);//摧毁附魔书
                    ItemStack scrollStack = new ItemStack(scroll);//创建物品夹（卷轴）
                    RegistryKey<Enchantment> enchantKey = enchantment_list.get(player.getWorld().random.nextInt(enchantment_list.size()));
                    while (((PlayerInterface) player).advancedEnchantment$getEnchantmentKey() == enchantKey) {
                        enchantKey = enchantment_list.get(player.getWorld().random.nextInt(enchantment_list.size()));
                    }
                    RegistryEntry<Enchantment> enchantEntry = ModAPI.keyToEntry(enchantKey, player.getServer());
                    int maxLevel = ModAPI.getEnchantmentMaxLevel(enchantKey, world);
                    int level = 1 + player.getWorld().random.nextInt(maxLevel);

                    //大保底，50%如果附魔等于2 3 4则直接满级，以上则满级的-2和满级随机
                    if (player.getWorld().random.nextBoolean()) {
                        if (maxLevel == 2 || maxLevel == 3 || maxLevel == 4) {
                            level = maxLevel;
                        } else {
                            level = new Random().nextInt(maxLevel - 2, maxLevel);
                        }
                    }
                    scrollStack.addEnchantment(enchantEntry, level);
                    ((PlayerInterface) player).advancedEnchantment$setEnchantmentKey(enchantKey);

                    if (!player.giveItemStack(scrollStack)) {
                        player.dropItem(scrollStack, false);
                    }//放入卷轴，如果玩家背包满了，就把此物体丢出体外

                    //执行音效，翻书声和升级声
                    ModAPI.playSoundsToPlayer(SoundEvents.ITEM_BOOK_PAGE_TURN, player);
                    ModAPI.playSoundsToPlayer(SoundEvents.ENTITY_PLAYER_LEVELUP, player);

                    //在物品栏上输出消息
                    String number = switch (level) {
                        case 1 -> "I";
                        case 2 -> "II";
                        case 3 -> "III";
                        case 4 -> "IV";
                        case 5 -> "V";
                        case 6 -> "VI";
                        case 7 -> "VII";
                        case 8 -> "VIII";
                        case 9 -> "IX";
                        case 10 -> "X";
                        default -> "";
                    };

                    Text message = Text.translatable("chat.you_got").append(enchantEntry.value().description()).append(number).formatted(color); //添加语言适配
                    player.sendMessage(message, true);//设置字段仅自己可见
                    player.getItemCooldownManager().set(held.getItem(), 15);//设置转换间隔为15tick(0.75s)
                }
            }
            return TypedActionResult.pass(held);//输出执行成功
        });
    }
}