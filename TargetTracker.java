package dev.visuals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public final class TargetTracker {
    private static LivingEntity target;
    private static long time;

    public static void set(Entity e) {
        if (e instanceof LivingEntity l) { target = l; time = System.currentTimeMillis(); }
    }
    public static LivingEntity get() {
        if (target != null && (System.currentTimeMillis() - time > 4000 || !target.isAlive())) target = null;
        return target;
    }
}
