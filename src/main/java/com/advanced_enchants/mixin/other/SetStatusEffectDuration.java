package com.advanced_enchants.mixin.other;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * 修改状态效果时长的 Mixin。
 *
 * @see DungeonEntityMixin
 */
@Mixin(StatusEffectInstance.class)
public interface SetStatusEffectDuration {
    @Accessor
    void setDuration(int duration);
}
