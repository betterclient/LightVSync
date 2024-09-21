package io.github.betterclient.lightvsync.mixin;

import io.github.betterclient.lightvsync.LightVSync;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class MixinGameRendererBefore121 {
    @Dynamic("Minecraft Version")
    @Redirect(method = "method_3192(FJZ)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z", ordinal = 0))
    public boolean cancelRenderIf(MinecraftClient instance) {
        return LightVSync.run(instance);
    }
}