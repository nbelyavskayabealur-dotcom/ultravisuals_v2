package dev.visuals.module;

import dev.visuals.Theme;
import net.minecraft.client.gui.DrawContext;
import java.util.Comparator;
import java.util.List;

public class ArrayListHud extends Module {
    public ArrayListHud() { super("ArrayList", true); }

    @Override public void onHud(DrawContext ctx) {
        List<Module> list = ModuleManager.MODULES.stream()
                .filter(m -> m.enabled && m != this)
                .sorted(Comparator.comparingInt((Module m) -> mc.textRenderer.getWidth(m.name)).reversed())
                .toList();
        int sw = ctx.getScaledWindowWidth(), y = 4, i = 0;
        for (Module m : list) {
            int w = mc.textRenderer.getWidth(m.name);
            int x = sw - w - 6;
            ctx.fill(x - 2, y, sw, y + 11, 0x80000000);
            ctx.fill(sw - 1, y, sw, y + 11, Theme.argb(i * 15));
            ctx.drawTextWithShadow(mc.textRenderer, m.name, x, y + 2, Theme.argb(i * 15));
            y += 11; i++;
        }
    }
}
