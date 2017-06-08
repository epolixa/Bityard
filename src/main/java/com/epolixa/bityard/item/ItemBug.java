package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import com.google.common.collect.Maps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class ItemBug extends ItemBase implements ItemModelProvider
{
    public ItemBug()
    {
        super("bug");
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(1);
        setCreativeTab(BityardItems.TAB_COLLECTIBLES);
    }

    @Override
    public void registerItemModel(Item item)
    {
        for (int i = 0; i < BugType.values().length; i++)
        {
            Bityard.proxy.registerItemRenderer(item, i, "bug/bug_" + BugType.byMetadata(i).getUnlocalizedName());
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        for (ItemBug.BugType itembug$bugtype : ItemBug.BugType.values())
        {
            subItems.add(new ItemStack(this, 1, itembug$bugtype.getMetadata()));
        }
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        ItemBug.BugType itembug$bugtype = ItemBug.BugType.byItemStack(stack);
        return this.getUnlocalizedName() + "." + itembug$bugtype.getUnlocalizedName();
    }

    public static enum BugType
    {
        LADYBUG(0, "ladybug"),
        BEE(1, "bee"),
        FIREFLY(2, "firefly"),
        INCHWORM(3, "inchworm"),
        DRAGONFLY(4, "dragonfly"),
        MANTIS(5, "mantis"),
        BUTTERFLY(6, "butterfly"),
        GRASSHOPPER(7, "grasshopper"),
        RHINOBEETLE(8, "rhinobeetle"),
        SNAIL(9, "snail"),
        STICKBUG(10, "stickbug"),
        WATER_STRIDER(11, "water_strider"),
        IRIDESCENT_BEETLE(12, "iridescent_beetle"),
        CICADA(13, "cicada"),
        SCORPION(14, "scorpion");

        private static final Map<Integer, ItemBug.BugType> META_LOOKUP = Maps.<Integer, ItemBug.BugType>newHashMap();
        /** The item damage value on an ItemStack that represents this fish type */
        private final int meta;
        /**
         * The value that this fish type uses to replace "XYZ" in: "fish.XYZ.raw" / "fish.XYZ.cooked" for the
         * unlocalized name and "fish_XYZ_raw" / "fish_XYZ_cooked" for the icon string.
         */
        private final String unlocalizedName;

        private BugType(int meta, String unlocalizedName)
        {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
        }

        /**
         * Gets the item damage value on an ItemStack that represents this fish type
         */
        public int getMetadata()
        {
            return this.meta;
        }

        /**
         * Gets the value that this fish type uses to replace "XYZ" in: "fish.XYZ.raw" / "fish.XYZ.cooked" for the
         * unlocalized name and "fish_XYZ_raw" / "fish_XYZ_cooked" for the icon string.
         */
        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        /**
         * Gets the corresponding FishType value for the given item damage value of an ItemStack, defaulting to COD for
         * unrecognized damage values.
         */
        public static ItemBug.BugType byMetadata(int meta)
        {
            ItemBug.BugType itembug$bugtype = (ItemBug.BugType)META_LOOKUP.get(Integer.valueOf(meta));
            return itembug$bugtype == null ? LADYBUG : itembug$bugtype;
        }

        /**
         * Gets the FishType that corresponds to the given ItemStack, defaulting to COD if the given ItemStack does not
         * actually contain a fish.
         */
        public static ItemBug.BugType byItemStack(ItemStack stack)
        {
            return stack.getItem() instanceof ItemBug ? byMetadata(stack.getMetadata()) : LADYBUG;
        }

        static
        {
            for (ItemBug.BugType itembug$bugtype : values())
            {
                META_LOOKUP.put(Integer.valueOf(itembug$bugtype.getMetadata()), itembug$bugtype);
            }
        }
    }
}
