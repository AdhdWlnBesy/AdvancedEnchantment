package com.advanced_enchants.mixin.enchantment;

import com.advanced_enchants.entity.duckInterface.NoFireThunder;
import net.minecraft.entity.LightningEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public class ThunderCall implements NoFireThunder {
    @Unique
    private boolean isNoFire = false;

    @Override
    public void advancedEnchantment$setNoFire() {
        isNoFire = true;
    }

    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    private void mainEffect(int spreadAttempts, CallbackInfo ci) {
        if (isNoFire) ci.cancel();
    }
}
