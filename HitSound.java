package dev.visuals.module;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class HitSound extends Module {
    private int i = 0;
    private static final String[] N = {"Orb", "Anvil", "Bell", "Arrow"};
    private static final SoundEvent[] S = {
            SoundEvent.of(Identifier.ofVanilla("entity.experience_orb.pickup")),
            SoundEvent.of(Identifier.ofVanilla("block.anvil.land")),
            SoundEvent.of(Identifier.ofVanilla("block.note_block.bell")),
            SoundEvent.of(Identifier.ofVanilla("entity.arrow.hit_player"))};

    public HitSound() { super("HitSound", false); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }

    @Override public void onAttack(Entity t) {
        mc.world.playSound(mc.player.getX(), mc.player.getY(), mc.player.getZ(), S[i], SoundCategory.PLAYERS, 0.8f, 1.2f, false);
    }
}
