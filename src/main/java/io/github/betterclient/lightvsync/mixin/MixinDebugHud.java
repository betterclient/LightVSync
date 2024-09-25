package io.github.betterclient.lightvsync.mixin;

import io.github.betterclient.lightvsync.LightVSync;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    @Inject(method = "getLeftText", at = @At("RETURN"))
    public void onGetLeftText(CallbackInfoReturnable<List<String>> cir) {
        cir.getReturnValue().add(2, LightVSync.cancelledSwapFPS + " swaps cancelled");
    }
}