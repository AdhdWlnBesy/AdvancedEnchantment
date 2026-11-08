package com.advanced_enchants.mixin.entity;

import com.advanced_enchants.entity.duckInterface.DungeonEntityInterface;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 地牢实体通用行为的 Mixin。
 *
 * @see com.advanced_enchants.entity.duckInterface.DungeonEntityInterface
 */
@Mixin(MobEntity.class)
public abstract class DungeonEntityMixin implements DungeonEntityInterface {
    @Shadow
    @Final
    protected GoalSelector targetSelector;
    @Unique
    private boolean isDungeonEntity;

    @Override
    public void advancedEnchantment$setIsDungeonEntity() {
        isDungeonEntity = true;
        //只换"选谁当目标"：RevengeGoal（被谁打就恨谁）、TrackIronGolemTargetGoal、UniversalAngerGoal
        //这些才是怪互殴的来源，全清掉，只留玩家。
        //goalSelector 不动，每只怪才留着自己的攻击方式（弓/风弹/召唤/近战）和游走、游泳、看人。
        targetSelector.clear(goal -> true);
        targetSelector.add(1, new ActiveTargetGoal<>(get(), PlayerEntity.class, true));
    }

    @Override
    public boolean advancedEnchantment$isDungeonEntity() {
        return isDungeonEntity;
    }

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    public void dropLoot(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
        if (isDungeonEntity) ci.cancel();
    }

    @Inject(method = "dropEquipment(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;Z)V", at = @At("HEAD"), cancellable = true)
    public void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (isDungeonEntity) ci.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (isDungeonEntity && get().getTarget() != null && !(get().getTarget() instanceof PlayerEntity)) {
            get().setTarget(null);
        }
    }

    @Unique
    public MobEntity get() {
        return (MobEntity) (Object) this;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.getBoolean("isDungeonEntity")) advancedEnchantment$setIsDungeonEntity();
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("isDungeonEntity", isDungeonEntity);
    }
}
