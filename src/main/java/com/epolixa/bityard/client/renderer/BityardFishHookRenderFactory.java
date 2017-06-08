package com.epolixa.bityard.client.renderer;

import com.epolixa.bityard.entity.EntityBityardFishHook;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class BityardFishHookRenderFactory implements IRenderFactory<EntityBityardFishHook>
{
    @Override
    public Render<? super EntityBityardFishHook> createRenderFor(RenderManager manager) {
        return new RenderBityardFishHook(manager);
    }
}
