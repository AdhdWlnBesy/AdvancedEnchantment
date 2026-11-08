package com.advanced_enchants.enchantment.armor;

import com.advanced_enchants.enchantment.effect.ArmorHitEnchantmentEffect;
import com.advanced_enchants.entity.FriendlySkeleton;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record SkeletonGuard() implements ArmorHitEnchantmentEffect {
    @Override
    public @NotNull RegistryKey<Enchantment> getEnchantmentId() {
        return ModEnchantments.SKELETON_GUARD;
    }

    @Override
    public @NotNull Chance getChance(int level) {
        return new Chance(ChanceType.SWITCH, (level == 1) ? 0.1f : 0.15f);
    }

    @Override
    public void apply(LivingEntity attacker, PlayerEntity player, int level, EquipmentSlot s, float damage) {
        if (player.getHealth() < 8) return;
        for (int i = 0; i < ((level > 1) ? 2 : 1); i++) {
            FriendlySkeleton skeleton = new FriendlySkeleton(player.getWorld(), attacker);
            skeleton.refreshPositionAndAngles(attacker.getPos(), 0, 0);
            skeleton.setCanPickUpLoot(false);
            skeleton.setHealth(player.getHealth() - 8);
            skeleton.setCustomName(Objects.requireNonNull(player.getDisplayName()).copy().append(Text.translatable("chat.skeleton_guard")));
            skeleton.setCustomNameVisible(true);
            ItemStack stack = new ItemStack(Items.BOW);
            stack.addEnchantment(ModAPI.keyToEntry(Enchantments.POWER, Objects.requireNonNull(player.getServer())), 1);
            skeleton.equipStack(EquipmentSlot.MAINHAND, stack);
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                skeleton.setEquipmentDropChance(slot, 0.0F);
            }
            player.getWorld().spawnEntity(skeleton);
        }
    }
}
