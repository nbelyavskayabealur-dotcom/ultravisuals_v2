package dev.visuals.module;

import dev.visuals.TargetTracker;
import dev.visuals.Theme;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class TargetHud extends Module {
    private float shown = 1f;

    public TargetHud() { super("TargetHud", true); }

    @Override public void onHud(DrawContext ctx) {
        LivingEntity t = TargetTracker.get();
        if (t == null) return;
        int x = ctx.getScaledWindowWidth() / 2 + 24, y = ctx.getScaledWindowHeight() / 2 + 24, w = 120;
        float frac = MathHelper.clamp(t.getHealth() / t.getMaxHealth(), 0f, 1f);
        shown += (frac - shown) * 0.15f;
        ctx.fill(x, y, x + w, y + 32, 0xA0000000);
        ctx.fill(x, y, x + 2, y + 32, Theme.argb(0));
        ctx.drawTextWithShadow(mc.textRenderer, t.getName().getString(), x + 7, y + 5, 0xFFFFFFFF);
        ctx.fill(x + 7, y + 18, x + w - 7, y + 24, 0xFF202020);
        ctx.fill(x + 7, y + 18, x + 7 + (int) ((w - 14) * shown), y + 24, Theme.argb(0));
        String hp = String.format("%.1f", t.getHealth() + t.getAbsorptionAmount());
        ctx.drawTextWithShadow(mc.textRenderer, hp, x + w - 7 - mc.textRenderer.getWidth(hp), y + 5, 0xFFFF6B6B);
    }
}
