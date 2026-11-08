package com.advanced_enchants.mixin.entity;

import com.advanced_enchants.entity.duckInterface.DungeonTeleportInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 阻止地牢末影人瞬移。
 *
 * @see com.advanced_enchants.entity.duckInterface.DungeonTeleportInterface
 */
@Mixin(EndermanEntity.class)
public abstract class DungeonEndermanMixin implements DungeonTeleportInterface {
    @Unique
    private boolean noTeleport;

    @Override
    public void advancedEnchantment$blockTeleport() {
        this.noTeleport = true;
    }

    @Inject(method = "teleportRandomly", at = @At("HEAD"), cancellable = true)
    private void advancedEnchantment$cancelRandomTeleport(CallbackInfoReturnable<Boolean> cir) {
        if (this.noTeleport) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "teleportTo(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void advancedEnchantment$cancelTeleportToEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (this.noTeleport) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void advancedEnchantment$readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.noTeleport = nbt.getBoolean(NO_TELEPORT_NBT_KEY);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void advancedEnchantment$writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean(NO_TELEPORT_NBT_KEY, this.noTeleport);
    }
}
