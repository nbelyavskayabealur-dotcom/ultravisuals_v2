package dev.visuals.module;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

/** Holds attack or use for you (AFK mining, farms, eating). */
public class MouseRepeat extends Module {
    private int mode = 0;
    private static final String[] N = {"Attack", "Mine (hold)", "Use (hold)"};

    public MouseRepeat() { super("MouseRepeat", false); }
    @Override public void cycle() { release(); mode = (mode + 1) % N.length; }
    @Override public String info() { return N[mode]; }

    @Override public void toggle() { super.toggle(); if (!enabled) release(); }

    private void release() {
        mc.options.attackKey.setPressed(false);
        mc.options.useKey.setPressed(false);
    }

    @Override public void onTick() {
        if (mc.currentScreen != null) { release(); return; }
        if (mode == 2) { mc.options.useKey.setPressed(true); return; }
        mc.options.attackKey.setPressed(true);
        if (mode == 0 && mc.player.getAttackCooldownProgress(0f) >= 1f) {
            KeyBinding.onKeyPressed(InputUtil.fromTranslationKey(mc.options.attackKey.getBoundKeyTranslationKey()));
        }
    }
}
