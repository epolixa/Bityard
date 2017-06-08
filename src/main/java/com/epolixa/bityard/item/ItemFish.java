package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import com.google.common.collect.Maps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ItemFish extends ItemBase implements ItemModelProvider
{
    public ItemFish()
    {
        super("fish");
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(1);
        setCreativeTab(BityardItems.TAB_COLLECTIBLES);
    }

    @Override
    public void registerItemModel(Item item)
    {
        for (int i = 0; i < FishType.values().length; i++)
        {
            Bityard.proxy.registerItemRenderer(item, i, "fish/fish_" + FishType.byMetadata(i).getUnlocalizedName());
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        for (ItemFish.FishType itemfish$fishtype : ItemFish.FishType.values())
        {
            subItems.add(new ItemStack(this, 1, itemfish$fishtype.getMetadata()));
        }
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        ItemFish.FishType itemfish$fishtype = ItemFish.FishType.byItemStack(stack);
        return this.getUnlocalizedName() + "." + itemfish$fishtype.getUnlocalizedName();
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound tag = stack.getTagCompound();
            String size = tag.getString("size");

            if (size != null)
            {
                tooltip.add(TextFormatting.GRAY + "Size: " + size);
            }
        }
    }

    public static enum FishType
    {
        FRESHWATER_COD(0, "freshwater_cod", 90, 10, 2, 0),
        SOCKEYE_SALMON(1, "sockeye_salmon", 74, 10, 2, 0),
        JELLYFISH(2, "jellyfish", 22.5, 17, 0, 0),
        PUFFERFISH(3, "pufferfish", 57, 10, 1, 0),
        CLOWNFISH(4, "clownfish", 8, 3, 1, 0),
        SUNFISH(5, "sunfish", 450, 20, 1, 0),
        ANGLER(6, "angler", 70, 30, 1, 0),
        FROG(7, "frog", 8, 2, 0, 0),
        ELECTRIC_EEL(8, "electric_eel", 170, 30, 2, 0),
        PUPFISH(9, "pupfish", 9, 2, 1, 0),
        ARCTIC_CHAR(10, "arctic_char", 90, 13, 1, 0),
        PIRANHA(11, "piranha", 20, 6, 1, 0),
        TURTLE(12, "turtle", 16, 7, 0, 0),
        STINGRAY(13, "stingray", 30, 10, 0, 0);
        //SALAMANDER(14, "salamander", 200, 66, 0, 0),
        //GOLDFISH(15, "goldfish", 6, 20, 0, 0);

        private static final Map<Integer, ItemFish.FishType> META_LOOKUP = Maps.<Integer, ItemFish.FishType>newHashMap();

        private final int meta;
        private final String unlocalizedName;
        private final double averageSize; // standard fish size lore that random value is based around, should vary between fish. based on real life cm.
        private final double sizeVariance;
        private final int foodAmount; // how much raw_fish crafts from this
        private final int baitMeta; // which bug bait is used to attract this fish

        private FishType(int meta, String unlocalizedName, double averageSize, double sizeVariance, int foodAmount, int baitMeta)
        {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.averageSize = averageSize;
            this.sizeVariance = sizeVariance;
            this.foodAmount = foodAmount;
            this.baitMeta = baitMeta;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        public double getRandomizedSize()
        {
            Random random = new Random();
            return (double)Math.round((this.averageSize + ((random.nextDouble() * (this.sizeVariance * 2)) - this.sizeVariance)) * 100d) / 100d;
        }

        public int getFoodAmount()
        {
            return this.foodAmount;
        }

        public ItemStack getFishFromBait(ItemStack bait) {
            if (/*bait.getItem() == BityardItems.BUG*/ true)
            {
                ArrayList<ItemStack> fishCandidates = new ArrayList<ItemStack>();
                for (int i = 0; i < FishType.values().length; i++)
                {
                    if (bait.getMetadata() == FishType.byMetadata(i).baitMeta)
                    {
                        fishCandidates.add(new ItemStack(new ItemFish(), 1, i));
                    }
                }
                if (!fishCandidates.isEmpty())
                {
                    Random random = new Random();
                    return fishCandidates.get(random.nextInt(fishCandidates.size() - 1));
                }
            }

            return new ItemStack(new ItemFish());
        }

        public static ItemFish.FishType byMetadata(int meta)
        {
            ItemFish.FishType itemfish$fishtype = (ItemFish.FishType)META_LOOKUP.get(Integer.valueOf(meta));
            return itemfish$fishtype == null ? FRESHWATER_COD : itemfish$fishtype;
        }

        public static ItemFish.FishType byItemStack(ItemStack stack)
        {
            return stack.getItem() instanceof ItemFish ? byMetadata(stack.getMetadata()) : FRESHWATER_COD;
        }

        static
        {
            for (ItemFish.FishType itemfish$fishtype : values())
            {
                META_LOOKUP.put(Integer.valueOf(itemfish$fishtype.getMetadata()), itemfish$fishtype);
            }
        }
    }
}
