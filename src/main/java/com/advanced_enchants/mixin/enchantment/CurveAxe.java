package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class CurveAxe extends LivingEntity {
    protected CurveAxe(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // 同上：NeoForge 下 boolean 局部变量没有 bl4 这个名字，用第 4 个 boolean 局部变量定位（bl4，是否横扫）
    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 3)
    private boolean mainEffect(boolean bl4) {
        ItemStack stack = this.getStackInHand(Hand.MAIN_HAND);
        if (stack.isIn(ItemTags.AXES) && getServer() != null) {
            if (ModAPI.hasEnchantment(ModEnchantments.CURVE_AXE, getServer(), stack)) {
                return true;
            }
        }
        return bl4;
    }
}
