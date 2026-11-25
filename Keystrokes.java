package dev.visuals.module;

import dev.visuals.Theme;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;

public class Keystrokes extends Module {
    public Keystrokes() { super("Keystrokes", true); }

    @Override public void onHud(DrawContext ctx) {
        int s = 22, g = 2, x = 8, y = ctx.getScaledWindowHeight() / 2 - 20;
        key(ctx, mc.options.forwardKey, "W", x + s + g, y, s);
        key(ctx, mc.options.leftKey, "A", x, y + s + g, s);
        key(ctx, mc.options.backKey, "S", x + s + g, y + s + g, s);
        key(ctx, mc.options.rightKey, "D", x + 2 * (s + g), y + s + g, s);
    }

    private void key(DrawContext ctx, KeyBinding k, String label, int x, int y, int s) {
        boolean down = k.isPressed();
        ctx.fill(x, y, x + s, y + s, down ? (0xB0000000 | Theme.color(0)) : 0x90000000);
        ctx.drawCenteredTextWithShadow(mc.textRenderer, label, x + s / 2, y + s / 2 - 4, 0xFFFFFFFF);
    }
}
