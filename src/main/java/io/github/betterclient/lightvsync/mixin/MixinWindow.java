package io.github.betterclient.lightvsync.mixin;

import io.github.betterclient.lightvsync.CancelledSwapFPSReducer;
import io.github.betterclient.lightvsync.LightVSync;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinWindow {
    @Inject(method = "swapBuffers", at = @At("HEAD"), cancellable = true)
    public void swapBuffers(CallbackInfo ci) {
        if (MinecraftClient.getInstance().world != null && LightVSync.run(MinecraftClient.getInstance())) {
            ci.cancel();
            ((CancelledSwapFPSReducer) MinecraftClient.getInstance()).lightVSync$reduce();
            LightVSync.cancelledSwapCounter++;
        }
    }
}
