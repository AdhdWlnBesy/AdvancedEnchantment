package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class SoulBind {
    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("TAIL"))
    private void mainEffect(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        MinecraftServer server = player.getServer();
        if (server != null) {
            ItemEntity item = cir.getReturnValue();
            if (item == null) return;
            if (ModAPI.hasEnchantment(ModEnchantments.SOUL_BIND, server, item.getStack())) {
                if (player.giveItemStack(item.getStack())) {
                    item.discard();
                }
            }
        }
    }
}
