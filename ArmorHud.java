package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ArmorHud extends Module {
    private static final EquipmentSlot[] SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public ArmorHud() { super("ArmorHud", true); }

    @Override public void onHud(DrawContext ctx) {
        int x = ctx.getScaledWindowWidth() / 2 - 40, y = ctx.getScaledWindowHeight() - 88;
        for (EquipmentSlot slot : SLOTS) {
            ItemStack s = mc.player.getEquippedStack(slot);
            if (!s.isEmpty()) {
                ctx.drawItem(s, x, y);
                if (s.isDamageable()) {
                    int pct = (s.getMaxDamage() - s.getDamage()) * 100 / s.getMaxDamage();
                    ctx.drawCenteredTextWithShadow(mc.textRenderer, pct + "%", x + 8, y - 9, 0xFFFFFFFF);
                }
            }
            x += 20;
        }
    }
}
