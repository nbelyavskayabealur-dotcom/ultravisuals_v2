package dev.visuals.module;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public class WorldParticles extends Module {
    private int style = 0;
    private static final String[] N = {"Snow", "Ash", "Sparks"};

    public WorldParticles() { super("WorldParticles", false); }
    @Override public void cycle() { style = (style + 1) % N.length; }
    @Override public String info() { return N[style]; }

    @Override public void onTick() {
        ParticleEffect t = style == 0 ? ParticleTypes.SNOWFLAKE : style == 1 ? ParticleTypes.WHITE_ASH : ParticleTypes.END_ROD;
        for (int i = 0; i < 3; i++) {
            double x = mc.player.getX() + (Math.random() - 0.5) * 24;
            double y = mc.player.getY() + Math.random() * 9 - 1;
            double z = mc.player.getZ() + (Math.random() - 0.5) * 24;
            mc.world.addParticle(t, x, y, z, 0, style == 2 ? 0.02 : -0.02, 0);
        }
    }
}
