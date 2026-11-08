package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import net.minecraft.component.ComponentType;
import net.minecraft.item.*;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

/**
 * 模组数据组件注册。
 *
 * @see ModItems
 */
public class ModDataComponentTypes {
    public static final ComponentType<Integer> ADVANCED_SLOTS =
            Registry.register(Registries.DATA_COMPONENT_TYPE, "advanced_slots",
                    new ComponentType.Builder<Integer>().codec(Codecs.rangedInt(0, 12)).packetCodec(PacketCodecs.VAR_INT).build());

    public static int getSlots(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return -1;
        Integer value = stack.get(ADVANCED_SLOTS);
        if (value == null) return -1;
        return value;
    }

    public static int getAllSlots(ItemStack stack) {
        if (getSlots(stack) == -1) return -1;
        return ModEnchantments.getAdvancedEnchantmentsCount(stack.getEnchantments().getEnchantments()) + getSlots(stack);
    }

    public static int getMaxSlot(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof SwordItem) return 10;
        if (item instanceof AxeItem) return 12;
        if (item instanceof PickaxeItem) return 6;
        if (item instanceof ArmorItem) return 7;
        if (item instanceof BowItem || stack.isIn(ModTags.ENCHANTABLE_BOW)) return 8;
        if (stack.isOf(Items.SHIELD)) return 5;
        return 4;
    }

    public static void loadComponentTypes() {
        AdvancedEnchantment.LOGGER.info("Loading ComponentTypes successfully!");
    }
}
