package com.advanced_enchants.mixin.entity;

import com.advanced_enchants.entity.DungeonCreeper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 地牢苦力怕行为的 Mixin。
 *
 * @see com.advanced_enchants.entity.DungeonCreeper
 */
@Mixin(CreeperEntity.class)
public abstract class DungeonCreeperMixin {
    @Shadow
    public abstract boolean shouldRenderOverlay();

    @Unique
    private boolean isDungeonCreeper;

    @Unique
    private CreeperEntity get() {
        return (CreeperEntity) (Object) this;
    }

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    public void explode(CallbackInfo ci) {
        if (isDungeonCreeper && !get().getWorld().isClient()) {
            float power = 3 * (shouldRenderOverlay() ? 2.0F : 1.0F);
            Explosion explosion = get().getWorld().createExplosion(get(), Explosion.createDamageSource(get().getWorld(),
                    get()), DungeonCreeper.getExplosionBehavior(), get().getPos(), power, false, World.ExplosionSourceType.MOB);
            explosion.affectWorld(false);
            get().discard();
            ci.cancel();
        }
    }

    @Inject(method = "spawnEffectsCloud", at = @At("HEAD"), cancellable = true)
    public void spawnEffectsCloud(CallbackInfo ci) {
        for (StatusEffectInstance effect : get().getStatusEffects()) {
            if (effect.getDuration() == -1) {
                ci.cancel();
                return;
            }
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        isDungeonCreeper = nbt.getBoolean("isDungeonCreeper");
    }
}
