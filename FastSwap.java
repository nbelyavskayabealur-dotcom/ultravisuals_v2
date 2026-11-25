package dev.visuals.module;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

/** Press the Fast swap key: moves the chosen item from your inventory to the offhand. */
public class FastSwap extends Module {
    private int i = 0;
    private static final String[] N = {"Totem", "Golden apple", "Shield", "Ender pearl"};
    private static final Item[] I = {Items.TOTEM_OF_UNDYING, Items.GOLDEN_APPLE, Items.SHIELD, Items.ENDER_PEARL};

    public FastSwap() { super("FastSwap", true); }
    @Override public void cycle() { i = (i + 1) % N.length; }
    @Override public String info() { return N[i]; }

    public void swap() {
        if (!enabled || mc.player == null || mc.interactionManager == null) return;
        var player = mc.player;
        if (player.getOffHandStack().isOf(I[i])) return;
        var inv = player.getInventory();
        for (int idx = 0; idx < 36; idx++) {
            if (inv.getStack(idx).isOf(I[i])) {
                int slotId = idx < 9 ? 36 + idx : idx;
                mc.interactionManager.clickSlot(player.playerScreenHandler.syncId, slotId, 40, SlotActionType.SWAP, player);
                return;
            }
        }
    }
}
