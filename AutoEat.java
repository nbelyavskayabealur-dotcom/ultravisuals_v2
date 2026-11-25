package dev.visuals.module;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.Set;

/** Eats plain food from the hotbar when hunger is low, then returns to your slot. */
public class AutoEat extends Module {
    private int t = 1;
    private static final int[] TH = {16, 14, 10, 6};
    private static final Set<Item> SKIP = Set.of(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.ROTTEN_FLESH,
            Items.SPIDER_EYE, Items.PUFFERFISH, Items.POISONOUS_POTATO, Items.CHORUS_FRUIT, Items.SUSPICIOUS_STEW);
    private boolean eating;
    private int prevSlot;

    public AutoEat() { super("AutoEat", false); }
    @Override public void cycle() { t = (t + 1) % TH.length; }
    @Override public String info() { return "<= " + TH[t] / 2f + " bars"; }

    @Override public void toggle() { super.toggle(); if (!enabled) stop(); }

    private boolean isPlainFood(ItemStack s) {
        return !s.isEmpty() && s.contains(DataComponentTypes.FOOD) && !SKIP.contains(s.getItem());
    }

    private int findFood() {
        for (int i = 0; i < 9; i++) if (isPlainFood(mc.player.getInventory().getStack(i))) return i;
        return -1;
    }

    private void stop() {
        if (eating) {
            mc.options.useKey.setPressed(false);
            mc.player.getInventory().setSelectedSlot(prevSlot);
        }
        eating = false;
    }

    @Override public void onTick() {
        if (mc.currentScreen != null) { stop(); return; }
        int food = mc.player.getHungerManager().getFoodLevel();
        if (!eating) {
            if (food <= TH[t]) {
                int s = findFood();
                if (s >= 0) {
                    prevSlot = mc.player.getInventory().getSelectedSlot();
                    mc.player.getInventory().setSelectedSlot(s);
                    eating = true;
                }
            }
            return;
        }
        if (food >= 20 || !isPlainFood(mc.player.getInventory().getStack(mc.player.getInventory().getSelectedSlot()))) { stop(); return; }
        mc.options.useKey.setPressed(true);
    }
}
