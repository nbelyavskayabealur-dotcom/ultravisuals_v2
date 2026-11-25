package dev.visuals.module;

import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn extends Module {
    public AutoRespawn() { super("AutoRespawn", false); }

    @Override public void onTick() {
        if (mc.currentScreen instanceof DeathScreen) mc.player.requestRespawn();
    }
}
