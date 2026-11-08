package com.advanced_enchants.mixin.entity;

import com.advanced_enchants.block.spawner.DungeonSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 世界怪物等级成长的 Mixin。
 *
 * @see com.advanced_enchants.event.MobLevelupAdvancement
 */
@Mixin(MobEntity.class)
public class LevelupWorldMob {
    @Unique
    int miningFatigue = 0;
    @Unique
    int slowness = 0;
    @Unique
    int wither = 0;
    @Unique
    int poison = 0;
    @Unique
    int weakness = 0;

    @Inject(method = "initialize", at = @At("TAIL"))
    private void levelupWorldMob(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        if (spawnReason != SpawnReason.NATURAL) return;
        MobEntity mob = (MobEntity) (Object) this;
        int[] array = new int[9];
        for (int i = 0; i < Math.ceil(Math.min(mob.getWorld().getTimeOfDay() / 192000L, 10) * 2.5); i++) {
            int t = mob.getRandom().nextInt(array.length);
            array[t] = array[t] + 1;
        }
        if (array[0] > 0)
            mob.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, -1, Math.min(array[0] - 1, 3)));
        if (array[1] > 0)
            mob.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, -1, Math.min(array[1] - 1, 2)));
        if (array[2] > 0)
            mob.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, -1, Math.min(array[2] - 1, 3)));
        if (array[3] > 0)
            DungeonSpawnerBlockEntity.setRandomHealth(mob, (int) (mob.getMaxHealth() * array[3]) / 2, (int) (mob.getMaxHealth() * (array[3] + 1)) / 2);
        miningFatigue = array[4];
        slowness = array[5];
        wither = array[6];
        poison = array[7];
        weakness = array[8];
    }

    @Inject(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;playAttackSound()V"))
    private void mainEffect(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof LivingEntity entity) {
            if (miningFatigue > 0)
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, Math.min(miningFatigue - 1, 2)));
            if (slowness > 0)
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, Math.min(slowness - 1, 2)));
            if (wither > 0)
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, Math.min(wither - 1, 2)));
            if (poison > 0)
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, Math.min(poison - 1, 2)));
            if (weakness > 0)
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 80, Math.min(weakness - 1, 2)));
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("AttackMiningFatigue", miningFatigue);
        nbt.putInt("AttackSlowness", slowness);
        nbt.putInt("AttackWither", wither);
        nbt.putInt("AttackPoison", poison);
        nbt.putInt("AttackWeakness", weakness);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        miningFatigue = nbt.getInt("AttackMiningFatigue");
        slowness = nbt.getInt("AttackSlowness");
        wither = nbt.getInt("AttackWither");
        poison = nbt.getInt("AttackPoison");
        weakness = nbt.getInt("AttackWeakness");
    }
}
