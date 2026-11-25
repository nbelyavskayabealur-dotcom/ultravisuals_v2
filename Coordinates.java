package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;

public class Coordinates extends Module {
    public Coordinates() { super("Coordinates", true); }

    @Override public void onHud(DrawContext ctx) {
        String s = String.format("XYZ  %.1f  %.1f  %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
        ctx.drawTextWithShadow(mc.textRenderer, s, 6, ctx.getScaledWindowHeight() - 14, 0xFFFFFFFF);
    }
}
