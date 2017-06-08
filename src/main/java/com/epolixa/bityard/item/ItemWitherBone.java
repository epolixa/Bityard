package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.gameplay.BityardLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;

import java.util.Random;

public class ItemWitherBone extends ItemBase implements ItemModelProvider
{
    public ItemWitherBone()
    {
        super("wither_bone");
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "wither_bone");
    }

}