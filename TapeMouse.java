package dev.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import java.util.ArrayList;
import java.util.List;

/** Records movement keys, clicks, camera and hotbar slot, then plays it in a loop. Key H: record -> loop -> stop. */
public class TapeMouse extends Module {
    private record Frame(boolean atk, boolean use, boolean fwd, boolean back, boolean left, boolean right,
                         boolean jump, boolean sneak, boolean sprint, float yaw, float pitch, int slot) {}

    private static final int MAX = 1200;
    private final List<Frame> frames = new ArrayList<>();
    private int state, idx;
    private Frame prev;

    public TapeMouse() { super("TapeMouse", true); }

    @Override public String info() { return state == 1 ? "REC" : state == 2 ? "PLAY" : "key H"; }

    @Override public void toggle() { super.toggle(); if (!enabled) { state = 0; resync(); } }

    public void pressKey() {
        if (!enabled || mc.player == null) return;
        switch (state) {
            case 0 -> { frames.clear(); state = 1; }
            case 1 -> { if (frames.isEmpty()) state = 0; else { state = 2; idx = 0; prev = null; } }
            default -> { state = 0; resync(); }
        }
    }

    private void resync() { KeyBinding.updatePressedStates(); }

    private static void edge(KeyBinding k, boolean now, boolean before) {
        k.setPressed(now);
        if (now && !before) KeyBinding.onKeyPressed(InputUtil.fromTranslationKey(k.getBoundKeyTranslationKey()));
    }

    @Override public void onTick() {
        if (mc.currentScreen != null) { if (state == 2) { state = 0; resync(); } return; }
        var o = mc.options;
        if (state == 1) {
            if (frames.size() >= MAX) { pressKey(); return; }
            frames.add(new Frame(o.attackKey.isPressed(), o.useKey.isPressed(), o.forwardKey.isPressed(),
                    o.backKey.isPressed(), o.leftKey.isPressed(), o.rightKey.isPressed(), o.jumpKey.isPressed(),
                    o.sneakKey.isPressed(), o.sprintKey.isPressed(), mc.player.getYaw(), mc.player.getPitch(),
                    mc.player.getInventory().getSelectedSlot()));
        } else if (state == 2) {
            Frame f = frames.get(idx);
            edge(o.attackKey, f.atk(), prev != null && prev.atk());
            edge(o.useKey, f.use(), prev != null && prev.use());
            o.forwardKey.setPressed(f.fwd());
            o.backKey.setPressed(f.back());
            o.leftKey.setPressed(f.left());
            o.rightKey.setPressed(f.right());
            o.jumpKey.setPressed(f.jump());
            o.sneakKey.setPressed(f.sneak());
            o.sprintKey.setPressed(f.sprint());
            mc.player.setYaw(f.yaw());
            mc.player.setPitch(f.pitch());
            mc.player.getInventory().setSelectedSlot(f.slot());
            prev = f;
            idx = (idx + 1) % frames.size();
        }
    }

    @Override public void onHud(DrawContext ctx) {
        if (state == 0) return;
        String s = state == 1 ? String.format("TapeMouse  REC  %.1fs  (H to stop and loop)", frames.size() / 20f)
                : String.format("TapeMouse  PLAY  %.1f / %.1fs  (H to stop)", idx / 20f, frames.size() / 20f);
        ctx.drawCenteredTextWithShadow(mc.textRenderer, s, ctx.getScaledWindowWidth() / 2, 14, state == 1 ? 0xFFFF5555 : 0xFF55FF55);
    }
}
