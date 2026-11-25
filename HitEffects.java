package dev.visuals.module;

import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public class HitEffects extends Module {
    private int style = 0;
    private static final String[] N = {"Magic", "Crit", "Hearts"};

    public HitEffects() { super("HitEffects", true); }
    @Override public void cycle() { style = (style + 1) % N.length; }
    @Override public String info() { return N[style]; }

    @Override public void onAttack(Entity t) {
        if (mc.world == null) return;
        double x = t.getX(), y = t.getY() + t.getHeight() * 0.5, z = t.getZ();
        ParticleEffect p = style == 0 ? ParticleTypes.ENCHANTED_HIT : style == 1 ? ParticleTypes.CRIT : ParticleTypes.HEART;
        for (int i = 0; i < 20; i++) {
            double vx = (Math.random() - 0.5) * 0.5, vy = Math.random() * 0.4, vz = (Math.random() - 0.5) * 0.5;
            if (style == 2 && i % 4 != 0) continue;
            mc.world.addParticle(p, x, y, z, vx, vy, vz);
        }
    }
}
