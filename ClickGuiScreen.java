package dev.visuals.gui;

import dev.visuals.Theme;
import dev.visuals.module.Module;
import dev.visuals.module.ModuleManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/** Left button toggles a module, the small button next to it switches its style. */
public class ClickGuiScreen extends Screen {
    private static final int W = 122, ROW = 13, PER_COL = 10, GAP = 5;

    public ClickGuiScreen() { super(Text.literal("ClientVisuals")); }
    @Override public boolean shouldPause() { return false; }

    private static Text label(Module m) {
        String info = m.info();
        return Text.literal(m.name + (info.isEmpty() ? "" : " [" + info + "]"))
                .formatted(m.enabled ? Formatting.GREEN : Formatting.GRAY);
    }

    @Override protected void init() {
        int n = ModuleManager.MODULES.size();
        int cols = (n + PER_COL - 1) / PER_COL;
        int x0 = width / 2 - (cols * (W + GAP) - GAP) / 2;
        int y0 = height / 2 - (PER_COL * ROW) / 2;

        ButtonWidget theme = ButtonWidget.builder(Text.literal("Theme: " + Theme.NAMES[Theme.mode]), b -> {
            Theme.next();
            b.setMessage(Text.literal("Theme: " + Theme.NAMES[Theme.mode]));
        }).dimensions(width / 2 - 45, y0 - 17, 90, 13).build();
        addDrawableChild(theme);

        for (int i = 0; i < n; i++) {
            Module m = ModuleManager.MODULES.get(i);
            int x = x0 + (i / PER_COL) * (W + GAP);
            int y = y0 + (i % PER_COL) * ROW;
            ButtonWidget toggle = ButtonWidget.builder(label(m), b -> {
                m.toggle();
                b.setMessage(label(m));
            }).dimensions(x, y, W - 16, ROW - 1).build();
            addDrawableChild(toggle);
            addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> {
                m.cycle();
                toggle.setMessage(label(m));
            }).dimensions(x + W - 15, y, 15, ROW - 1).build());
        }
    }
}
