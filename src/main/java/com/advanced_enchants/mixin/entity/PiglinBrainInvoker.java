package com.advanced_enchants.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * 猪灵 AI 的访问器。
 *
 * @see FriendlyPiglin
 */
@Mixin(PiglinBrain.class)
public interface PiglinBrainInvoker {
    @Invoker("onAttacked")
    static void onAttacked(PiglinEntity piglin, LivingEntity target) {
        throw new AssertionError();
    }
}
