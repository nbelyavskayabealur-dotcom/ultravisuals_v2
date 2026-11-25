package dev.visuals.module;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public class Trails extends Module {
    private int style = 0;
    private static final String[] N = {"EndRod", "Flame", "Magic"};

    public Trails() { super("Trails", false); }
    @Override public void cycle() { style = (style + 1) % N.length; }
    @Override public String info() { return N[style]; }

    @Override public void onTick() {
        if (mc.world == null) return;
        if (mc.player.getVelocity().lengthSquared() < 0.0025 && mc.player.isOnGround()) return;
        ParticleEffect p = style == 0 ? ParticleTypes.END_ROD : style == 1 ? ParticleTypes.FLAME : ParticleTypes.ENCHANT;
        double x = mc.player.getX(), y = mc.player.getY() + 0.1, z = mc.player.getZ();
        for (int i = 0; i < 2; i++)
            mc.world.addParticle(p, x + (Math.random() - 0.5) * 0.3, y, z + (Math.random() - 0.5) * 0.3, 0, 0.01, 0);
    }
}
