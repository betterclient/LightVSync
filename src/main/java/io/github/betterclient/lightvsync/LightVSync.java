package io.github.betterclient.lightvsync;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LightVSync implements ModInitializer {
    public static int fps = 60;
    public static final Logger LOGGER = LogManager.getLogger(LightVSync.class);

    private static int timeForFrame = 0;
    private static long lastRender = 0;

    public static boolean run(MinecraftClient instance) {
        if (lastRender == 0) {
            timeForFrame = 1000 / (LightVSync.fps * 3);
            lastRender = System.currentTimeMillis() + timeForFrame;
        } else {
            if (lastRender <= System.currentTimeMillis()) {
                lastRender = System.currentTimeMillis() + timeForFrame;
            } else {
                //Skipped.
                return true;
            }
        }

        return instance.skipGameRender;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("LightVSync is alive and working!");
    }
}