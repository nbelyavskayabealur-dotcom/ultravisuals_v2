package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickupLogger extends Module {
    private Map<Item, Integer> snap;
    private final List<Object[]> log = new ArrayList<>();

    public PickupLogger() { super("PickupLogger", false); }

    @Override public void toggle() { super.toggle(); snap = null; log.clear(); }

    @Override public void onTick() {
        Map<Item, Integer> now = new HashMap<>();
        var inv = mc.player.getInventory();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack s = inv.getStack(i);
            if (!s.isEmpty()) now.merge(s.getItem(), s.getCount(), Integer::sum);
        }
        if (snap != null) {
            for (var en : now.entrySet()) {
                int d = en.getValue() - snap.getOrDefault(en.getKey(), 0);
                if (d > 0) log.add(new Object[]{"+" + d + " " + en.getKey().getName().getString(), System.currentTimeMillis()});
            }
        }
        snap = now;
    }

    @Override public void onHud(DrawContext ctx) {
        long now = System.currentTimeMillis();
        log.removeIf(l -> now - (long) l[1] > 4000);
        int y = ctx.getScaledWindowHeight() - 70;
        for (int i = log.size() - 1; i >= 0; i--) {
            String s = (String) log.get(i)[0];
            int a = Math.max(20, (int) (255 * (1 - (now - (long) log.get(i)[1]) / 4000f)));
            ctx.drawTextWithShadow(mc.textRenderer, s, ctx.getScaledWindowWidth() - 8 - mc.textRenderer.getWidth(s), y, (a << 24) | 0x7CFF7C);
            y -= 11;
        }
    }
}
