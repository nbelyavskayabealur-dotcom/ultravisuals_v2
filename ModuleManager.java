package dev.visuals.module;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {
    public static final List<Module> MODULES = new ArrayList<>();
    public static FastSwap FASTSWAP;
    public static TapeMouse TAPE;

    public static void init() {
        add(new Watermark());
        add(new ArrayListHud());
        add(new Coordinates());
        add(new Keystrokes());
        add(new ArmorHud());
        add(new PotionHud());
        add(new HealInfo());
        add(new PickupLogger());
        add(new TargetHud());
        add(new HitEffects());
        add(new HitSound());
        add(new Trails());
        add(new WorldParticles());
        add(new AutoRespawn());
        FASTSWAP = add(new FastSwap());
        add(new AutoEat());
        add(new MouseRepeat());
        TAPE = add(new TapeMouse());
    }

    private static <T extends Module> T add(T m) { MODULES.add(m); return m; }
}
