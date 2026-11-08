package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.enchantment.arrow.ArrowRain;
import com.advanced_enchants.enchantment.arrow.Reforge;
import com.advanced_enchants.entity.duckInterface.TempArrow;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(PersistentProjectileEntity.class)
public abstract class ArrowRainAndReforge extends ProjectileEntity implements TempArrow {
    @Shadow
    private @Nullable ItemStack weapon;
    @Shadow
    private ItemStack stack;

    @Unique
    private boolean isTemp;

    public ArrowRainAndReforge(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "onBlockHit", at = @At("TAIL"))
    private void mainEffect(BlockHitResult blockHitResult, CallbackInfo ci) {
        if (((Object) this) instanceof ArrowEntity arrow) {
            if (isTemp) arrow.discard();
            if (arrow.getServer() == null) return;
            RegistryEntry<Enchantment> entry = new ArrowRain().getEnchantment(arrow.getServer());
            int level = EnchantmentHelper.getLevel(entry, weapon);
            if (level > 0) {
                int range = (level > 2) ? 3 : 2;
                for (int x = -range; x < range; x++) {
                    for (int z = -range; z < range; z++) {
                        ItemStack bow = new ItemStack(Items.BOW);
                        for (RegistryEntry<Enchantment> enchantment : new ArrayList<>(weapon.getEnchantments().getEnchantments())) {
                            if (!ModEnchantments.ADVANCED_ENCHANTMENTS.contains(enchantment.getKey().get())) {
                                bow.addEnchantment(enchantment, weapon.getEnchantments().getLevel(enchantment));
                            }
                        }
                        ArrowEntity arrowEntity = new ArrowEntity(
                                arrow.getWorld(), arrow.getX() + x, arrow.getY() + 4, arrow.getZ() + z, this.stack, bow);
                        ((TempArrow) arrowEntity).advancedEnchantment$setTemp();
                        arrow.getWorld().spawnEntity(arrowEntity);
                        if (arrow.getOwner() instanceof ServerPlayerEntity player) {
                            new ArrowRain().sendPacket(player, entry, level);
                        }
                    }
                }
            }
            int level1 = EnchantmentHelper.getLevel(new Reforge().getEnchantment(arrow.getServer()), weapon);
            if (level1 > 1 && getOwner() instanceof LivingEntity entity && level1 * 0.2 > new Reforge().getChance(level).getChance(level)) {
                ItemStack stack = entity.getMainHandStack();
                stack.setDamage(Math.max(0, stack.getDamage() - 2));
                entity.setStackInHand(Hand.MAIN_HAND, stack);
            }
        }
    }

    @Override
    public void advancedEnchantment$setTemp() {
        isTemp = true;
    }
}
