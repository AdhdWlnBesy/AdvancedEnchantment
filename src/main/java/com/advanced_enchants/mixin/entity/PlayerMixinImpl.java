package com.advanced_enchants.mixin.entity;

import com.advanced_enchants.entity.duckInterface.PlayerInterface;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 玩家扩展接口的实现。
 *
 * @see com.advanced_enchants.entity.duckInterface.PlayerInterface
 */
@Mixin(PlayerEntity.class)
public class PlayerMixinImpl implements PlayerInterface {
    @Unique
    private RegistryKey<Enchantment> key;
    @Unique
    private int smithingUseCount;

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        key = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(nbt.getString("key")));
        smithingUseCount = nbt.getInt("smithingUseCount");
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (key != null) {
            nbt.putString("key", key.getValue().toString());
        }
        nbt.putInt("smithingUseCount", smithingUseCount);
    }

    @Override
    public void advancedEnchantment$setEnchantmentKey(RegistryKey<Enchantment> enchantment) {
        key = enchantment;
    }

    @Override
    public RegistryKey<Enchantment> advancedEnchantment$getEnchantmentKey() {
        return key;
    }

    @Override
    public void advancedEnchantment$addSmithingCount() {
        smithingUseCount++;
    }

    @Override
    public int advancedEnchantment$getSmithingCount() {
        return smithingUseCount;
    }
}
