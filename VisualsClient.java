package dev.visuals;

import dev.visuals.gui.ClickGuiScreen;
import dev.visuals.module.Module;
import dev.visuals.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class VisualsClient implements ClientModInitializer {
    private static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(Identifier.of("clientvisuals", "main"));
    private static KeyBinding guiKey, swapKey, tapeKey;

    private static KeyBinding key(String id, int code) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("key.clientvisuals." + id,
                InputUtil.Type.KEYSYM, code, CATEGORY));
    }

    @Override public void onInitializeClient() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ModuleManager.init();

        guiKey = key("gui", GLFW.GLFW_KEY_RIGHT_SHIFT);
        swapKey = key("swap", GLFW.GLFW_KEY_G);
        tapeKey = key("tape", GLFW.GLFW_KEY_H);

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            while (guiKey.wasPressed()) c.setScreen(new ClickGuiScreen());
            if (c.player == null || c.world == null) return;
            while (swapKey.wasPressed()) ModuleManager.FASTSWAP.swap();
            while (tapeKey.wasPressed()) ModuleManager.TAPE.pressKey();
            for (Module m : ModuleManager.MODULES) if (m.enabled) m.onTick();
        });

        HudRenderCallback.EVENT.register((ctx, tickCounter) -> {
            if (mc.player == null || mc.options.hudHidden) return;
            for (Module m : ModuleManager.MODULES) if (m.enabled) m.onHud(ctx);
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (world.isClient()) {
                TargetTracker.set(entity);
                for (Module m : ModuleManager.MODULES) if (m.enabled) m.onAttack(entity);
            }
            return ActionResult.PASS;
        });
    }
}
