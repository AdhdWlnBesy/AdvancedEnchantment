package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class Combo extends LivingEntity {
    @Unique
    boolean flag = true;

    protected Combo(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void mainEffect(Entity target, CallbackInfo ci) {
        if (!flag) {
            flag = true;
            return;
        }
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getServer() != null) {
            RegistryEntry<Enchantment> entry = ModAPI.keyToEntry(ModEnchantments.COMBO, player.getServer());
            int level = EnchantmentHelper.getLevel(entry, player.getMainHandStack());
            if (player.getRandom().nextFloat() < new com.advanced_enchants.enchantment.weapon.Combo().getChance(level).getChance(level)) {
                new com.advanced_enchants.enchantment.weapon.Combo().sendPacket((ServerPlayerEntity) player, entry, level);
                if (flag) {
                    target.timeUntilRegen = 0;
                    lastAttackedTicks = (int) player.getAttackCooldownProgressPerTick();
                    player.attack(target);
                    flag = false;
                }
            }
        }
    }
}
