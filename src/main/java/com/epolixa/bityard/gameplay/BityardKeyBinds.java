package com.epolixa.bityard.gameplay;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class BityardKeyBinds
{
    public static KeyBinding SIT;

    public static void init()
    {
        SIT = new KeyBinding("key.sit.desc", Keyboard.KEY_V, "key.bityard.category");

        ClientRegistry.registerKeyBinding(SIT);
    }
}
