package io.github.betterclient.lightvsync.mixin;

import io.github.betterclient.lightvsync.LightVSync;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Unique
    public boolean lv_first = true;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onRender(boolean tick, CallbackInfo ci) {
        if(LightVSync.run((MinecraftClient) (Object)this)) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void onFirstRender(boolean tick, CallbackInfo ci) {
        if(!lv_first)
            return;

        PointerBuffer buf = Objects.requireNonNull(GLFW.glfwGetMonitors()).duplicate();
        for (int i = 0; i < buf.remaining(); i++) {
            long monitor = buf.get(i);
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);

            if (vidMode != null && vidMode.refreshRate() > LightVSync.fps) {
                LightVSync.fps = vidMode.refreshRate();
            }
        }

        LightVSync.LOGGER.info("Set LightVSync fps to {}", LightVSync.fps);
        lv_first = false;
    }
}
