package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;

public class HealInfo extends Module {
    public HealInfo() { super("HealInfo", true); }

    @Override public void onHud(DrawContext ctx) {
        var p = mc.player;
        var inv = p.getInventory();
        int y = ctx.getScaledWindowHeight() - 40;
        String hp = String.format("HP %.1f  +%.1f", p.getHealth(), p.getAbsorptionAmount());
        String items = "GApple " + inv.count(Items.GOLDEN_APPLE) + "  EGap " + inv.count(Items.ENCHANTED_GOLDEN_APPLE)
                + "  Totem " + inv.count(Items.TOTEM_OF_UNDYING) + "  Splash " + inv.count(Items.SPLASH_POTION);
        ctx.drawTextWithShadow(mc.textRenderer, hp, 6, y, 0xFFFF6B6B);
        ctx.drawTextWithShadow(mc.textRenderer, items, 6, y + 11, 0xFFFFFFFF);
    }
}
