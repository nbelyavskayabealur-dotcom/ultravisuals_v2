package dev.visuals.module;

import dev.visuals.Theme;
import net.minecraft.client.gui.DrawContext;

public class Watermark extends Module {
    public Watermark() { super("Watermark", true); }

    @Override public void onHud(DrawContext ctx) {
        String name = "ClientVisuals";
        String fps = " | " + mc.getCurrentFps() + " fps";
        int x = 6, y = 6, w = mc.textRenderer.getWidth(name + fps) + 8;
        ctx.fill(x, y, x + w, y + 14, 0x90000000);
        ctx.fill(x, y, x + w, y + 1, Theme.argb(0));
        int cx = x + 4;
        for (int i = 0; i < name.length(); i++) {
            String c = String.valueOf(name.charAt(i));
            ctx.drawTextWithShadow(mc.textRenderer, c, cx, y + 3, Theme.argb(i * 12));
            cx += mc.textRenderer.getWidth(c);
        }
        ctx.drawTextWithShadow(mc.textRenderer, fps, cx, y + 3, 0xFFAAAAAA);
    }
}
