package dev.visuals;

import net.minecraft.util.math.MathHelper;

public final class Theme {
    public static int mode = 0; // 0 purple, 1 cyan, 2 rainbow
    private static final int[] BASE = {0xB06CFF, 0x39D5FF};
    public static final String[] NAMES = {"Purple", "Cyan", "Rainbow"};

    public static int color(double offset) {
        if (mode == 2) {
            float h = (float) (((System.currentTimeMillis() / 20.0 + offset) % 360) / 360.0);
            return MathHelper.hsvToRgb(h, 0.65f, 1f);
        }
        return BASE[mode];
    }
    public static int argb(double offset) { return 0xFF000000 | color(offset); }
    public static void next() { mode = (mode + 1) % 3; }
}
