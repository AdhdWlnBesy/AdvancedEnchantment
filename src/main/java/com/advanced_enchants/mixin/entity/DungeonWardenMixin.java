package com.advanced_enchants.mixin.entity;

import com.advanced_enchants.entity.duckInterface.DungeonEntityInterface;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 地牢监守者行为的 Mixin。
 *
 * @see DungeonEntityMixin
 */
@Mixin(WardenEntity.class)
public abstract class DungeonWardenMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void advancedEnchantment$keepDigCooldown(CallbackInfo ci) {
        WardenEntity warden = (WardenEntity) (Object) this;
        if (warden.getWorld().isClient || (warden.age & 31) != 0) return;
        if (!(warden instanceof DungeonEntityInterface dungeon) || !dungeon.advancedEnchantment$isDungeonEntity())
            return;
        warden.getBrain().remember(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE);
    }
}
