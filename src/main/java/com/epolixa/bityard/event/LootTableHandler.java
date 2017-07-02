package com.epolixa.bityard.event;

import com.epolixa.bityard.Bityard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootTableHandler
{
    @SubscribeEvent
    public void lootTableLoad(LootTableLoadEvent event)
    {
        String name = event.getName().toString();

        Bityard.log("loading loot table " + name);

        if (name.startsWith("minecraft:"))
        {
            name = name.replaceFirst("minecraft:", "");
            event.getTable().addPool(makeInjectPool(name, 1));
            Bityard.log("injected loot table");
        }
    }

    private LootPool makeInjectPool(String name, int weight)
    {
        LootEntry entry = new LootEntryTable(new ResourceLocation("bityard:inject/" + name), weight, 0, new LootCondition[0], "bityard_inject_entry");
        return new LootPool(new LootEntry[] {entry}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bityard_inject_pool");
    }
}
