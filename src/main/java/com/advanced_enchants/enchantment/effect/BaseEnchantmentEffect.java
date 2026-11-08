package com.advanced_enchants.enchantment.effect;

import com.advanced_enchants.event.EnchantTrigNetWorking;
import com.advanced_enchants.register.ModAPI;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

/**
 * 一个高级附魔效果接口必须实现的接口,
 * 实现这个接口还可以显示在图鉴上
 */
public interface BaseEnchantmentEffect {
    /**
     * 获取附魔ID(key)
     *
     * @return 附魔ID(RegistryKey)
     * @apiNote 重写此接口需要返回应用附魔key
     */
    RegistryKey<Enchantment> getEnchantmentId();

    /**
     * 获取附魔触发概率
     *
     * @param level 附魔等级
     * @return 附魔触发概率
     * @see com.advanced_enchants.enchantment.effect.BaseEnchantmentEffect.Chance
     */
    @NotNull Chance getChance(int level);

    /**
     * 当此值为true，说明此附魔触发时应该在屏幕左上角显示提示
     * 可重写为false
     *
     * @return 是否发包
     * @see com.advanced_enchants.event.EnchantTrigNetWorking
     * @see com.advanced_enchants.event.EnchantTrigRenderer
     */
    default boolean shouldSendPacket() {
        return true;
    }

    /**
     * 通过server和getEnchantmentId()获得entry对象
     *
     * @param server 服务器，如{@code attacker.getServer()}
     * @return entry对象
     */
    default RegistryEntry<Enchantment> getEnchantment(MinecraftServer server) {
        return ModAPI.keyToEntry(getEnchantmentId(), server);
    }

    /**
     * 手动发包，用于mixin和handler
     *
     * @param player 发送玩家
     * @param entry  发送的附魔
     * @param level  发送附魔的等级
     */
    default void sendPacket(ServerPlayerEntity player, RegistryEntry<Enchantment> entry, int level) {
        if (shouldSendPacket()) {
            EnchantTrigNetWorking.sendPacket(player, entry.value().description(), level);
        }
    }

    /**
     * 创建一个没有附魔，概率为100%的空实现，用于占位。
     * <b>不要拿他getEnchantment！</b>
     *
     * @return 空BaseEnchantmentEffect实现
     */
    static BaseEnchantmentEffect empty() {
        return new BaseEnchantmentEffect() {
            @Override
            public RegistryKey<Enchantment> getEnchantmentId() {
                return null;
            }

            @Override
            public @NotNull Chance getChance(int level) {
                return new Chance(ChanceType.ALWAYS, 1);
            }
        };
    }

    /**
     * 概率对象，用于方便图鉴绘制
     */
    record Chance(ChanceType type, float chance) {

        /**
         * 获取真实概率
         *
         * @param level 附魔等级
         */
        public float getChance(int level) {
            if (type == ChanceType.ALWAYS) return 1;
            if (type == ChanceType.LINEAR) return level * chance;
            if (type == ChanceType.SWITCH || type == ChanceType.FINAL) return chance;
            return 0;
        }
    }

    /**
     * 概率类型
     */
    enum ChanceType {
        /**
         * switch(level)决定，无规律
         */
        SWITCH,
        /**
         * level * chance决定，线性
         */
        LINEAR,
        /**
         * 一定触发或常驻
         */
        ALWAYS,
        /**
         * 一个常量
         */
        FINAL
    }
}
