package com.epolixa.bityard.entity;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.gameplay.BityardLootTables;
import com.epolixa.bityard.item.BityardItems;
import com.epolixa.bityard.item.ItemFish;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityBityardFishHook extends EntityFishHook implements IThrowableEntity
{
    private static final DataParameter<Integer> DATA_HOOKED_ENTITY = EntityDataManager.<Integer>createKey(EntityBityardFishHook.class, DataSerializers.VARINT);
    private boolean inGround;
    private int ticksInGround = 0;
    private EntityPlayer angler;
    private int ticksInAir;
    private int ticksCatchable;
    private int ticksCaughtDelay;
    private int ticksCatchableDelay;
    private float fishApproachAngle;
    public Entity caughtEntity;
    private EntityBityardFishHook.State currentState = EntityBityardFishHook.State.FLYING;
    private int field_191518_aw;
    private int field_191519_ax;

    @SideOnly(Side.CLIENT)
    public EntityBityardFishHook(World worldIn, EntityPlayer fishingPlayer, double x, double y, double z)
    {
        super(worldIn, fishingPlayer, x, y, z);
        System.out.println("fish hook: constructor 1 used");
        this.init(fishingPlayer);
        this.setPosition(x, y, z);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }

    public EntityBityardFishHook(World worldIn, EntityPlayer fishingPlayer)
    {
        super(worldIn, fishingPlayer);
        System.out.println("fish hook: constructor 2 used");
        this.init(fishingPlayer);
        this.shoot();
    }

    public EntityBityardFishHook(World worldIn)
    {
        this(worldIn, null); // wtf forge
        System.out.println("fish hook: constructor 3 used");
    }

    private void init(EntityPlayer player)
    {
        Bityard.log("EntityBityardFishHook", "fish hook: init");
        this.setSize(0.25F, 0.25F);
        this.ignoreFrustumCheck = true;
        this.angler = player;
        this.angler.fishEntity = this;
    }

    @Override
    public void func_191516_a(int p_191516_1_)
    {
        this.field_191519_ax = p_191516_1_;
    }

    @Override
    public void func_191517_b(int p_191517_1_)
    {
        this.field_191518_aw = p_191517_1_;
    }

    private void shoot()
    {
        System.out.println("fish hook: shoot");
        float f = this.angler.prevRotationPitch + (this.angler.rotationPitch - this.angler.prevRotationPitch);
        float f1 = this.angler.prevRotationYaw + (this.angler.rotationYaw - this.angler.prevRotationYaw);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        double d0 = this.angler.prevPosX + (this.angler.posX - this.angler.prevPosX) - (double)f3 * 0.3D;
        double d1 = this.angler.prevPosY + (this.angler.posY - this.angler.prevPosY) + (double)this.angler.getEyeHeight();
        double d2 = this.angler.prevPosZ + (this.angler.posZ - this.angler.prevPosZ) - (double)f2 * 0.3D;
        this.setLocationAndAngles(d0, d1, d2, f1, f);
        this.motionX = (double)(-f3);
        this.motionY = (double)MathHelper.clamp(-(f5 / f4), -5.0F, 5.0F);
        this.motionZ = (double)(-f2);
        float f6 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX *= 0.6D / (double)f6 + 0.5D + this.rand.nextGaussian() * 0.0045D;
        this.motionY *= 0.6D / (double)f6 + 0.5D + this.rand.nextGaussian() * 0.0045D;
        this.motionZ *= 0.6D / (double)f6 + 0.5D + this.rand.nextGaussian() * 0.0045D;
        float f7 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f7) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    @Override
    protected void entityInit()
    {
        System.out.println("fish hook: entity init");
        this.getDataManager().register(DATA_HOOKED_ENTITY, Integer.valueOf(0));
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        System.out.println("fish hook: notify data manager change");
        super.notifyDataManagerChange(key);
    }

    /**
     * Checks if the entity is in range to render.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        System.out.println("fish hook: is in range to render dist");
        double d0 = 64.0D;
        return distance < 4096.0D;
    }

    /**
     * Set the position and rotation values directly without any clamping.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        System.out.println("fish hook: set position and rotation direct (does nothing)");
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        System.out.println("fish hook: update");

        // super.onUpdate();
        if (!this.world.isRemote)
        {
            this.setFlag(6, this.isGlowing());
        }

        this.onEntityUpdate();
        // copied from Entity.onUpdate

        if (this.angler == null)
        {
            System.out.println("fish hook: angler is null");
            this.setDead();
        }
        else if (this.world.isRemote || !this.shouldStopFishing())
        {
            System.out.println("fish hook: world is client or don't stop fishing");
            if (this.inGround)
            {
                System.out.println("fish hook: in ground");
                ++this.ticksInGround;

                if (this.ticksInGround >= 1200)
                {
                    this.setDead();
                    return;
                }
            }

            float f = 0.0F;
            BlockPos blockpos = new BlockPos(this);
            IBlockState iblockstate = this.world.getBlockState(blockpos);

            if (iblockstate.getMaterial() == Material.WATER || iblockstate.getMaterial() == Material.LAVA)
            {
                System.out.println("fish hook: in water");
                f = BlockLiquid.getBlockLiquidHeight(iblockstate, this.world, blockpos);
            }

            if (this.currentState == EntityBityardFishHook.State.FLYING)
            {
                System.out.println("fish hook: flying");
                if (this.caughtEntity != null)
                {
                    System.out.println("fish hook: caught an entity");
                    this.motionX = 0.0D;
                    this.motionY = 0.0D;
                    this.motionZ = 0.0D;
                    this.currentState = EntityBityardFishHook.State.HOOKED_IN_ENTITY;
                    return;
                }

                if (f > 0.0F)
                {
                    System.out.println("fish hook: set state to bobbing");
                    this.motionX *= 0.3D;
                    this.motionY *= 0.2D;
                    this.motionZ *= 0.3D;
                    this.currentState = EntityBityardFishHook.State.BOBBING;
                    return;
                }

                if (!this.world.isRemote)
                {
                    System.out.println("fish hook: world is server");
                    this.checkCollision();
                }

                if (!this.inGround && !this.onGround && !this.isCollidedHorizontally)
                {
                    System.out.println("fish hook: in air");
                    ++this.ticksInAir;
                }
                else
                {
                    System.out.println("fish hook: not in air");
                    this.ticksInAir = 0;
                    this.motionX = 0.0D;
                    this.motionY = 0.0D;
                    this.motionZ = 0.0D;
                }
            }
            else
            {
                System.out.println("fish hook: not flying");
                if (this.currentState == EntityBityardFishHook.State.HOOKED_IN_ENTITY)
                {
                    System.out.println("fish hook: hooked in entity");
                    if (this.caughtEntity != null)
                    {
                        System.out.println("fish hook: entity is not null");
                        if (this.caughtEntity.isDead)
                        {
                            System.out.println("fish hook: entity is dead");
                            this.caughtEntity = null;
                            this.currentState = EntityBityardFishHook.State.FLYING;
                        }
                        else
                        {
                            System.out.println("fish hook: entity is not dead");
                            this.posX = this.caughtEntity.posX;
                            double d2 = (double)this.caughtEntity.height;
                            this.posY = this.caughtEntity.getEntityBoundingBox().minY + d2 * 0.8D;
                            this.posZ = this.caughtEntity.posZ;
                            this.setPosition(this.posX, this.posY, this.posZ);
                        }
                    }

                    return;
                }

                if (this.currentState == EntityBityardFishHook.State.BOBBING)
                {
                    System.out.println("fish hook: bobbing");
                    this.motionX *= 0.9D;
                    this.motionZ *= 0.9D;
                    double d0 = this.posY + this.motionY - (double)blockpos.getY() - (double)f;

                    if (Math.abs(d0) < 0.01D)
                    {
                        d0 += Math.signum(d0) * 0.1D;
                    }

                    this.motionY -= d0 * (double)this.rand.nextFloat() * 0.2D;

                    if (!this.world.isRemote && f > 0.0F)
                    {
                        System.out.println("fish hook: world is server");
                        this.catchingFish(blockpos);
                    }
                }
            }

            if (iblockstate.getMaterial() != Material.WATER && iblockstate.getMaterial() != Material.LAVA)
            {
                System.out.println("fish hook: not in water");
                this.motionY -= 0.03D;
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.updateRotation();
            double d1 = 0.92D;
            this.motionX *= 0.92D;
            this.motionY *= 0.92D;
            this.motionZ *= 0.92D;
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }

    private boolean shouldStopFishing()
    {
        System.out.println("fish hook: should stop fishing?");
        ItemStack itemstack = this.angler.getHeldItemMainhand();
        ItemStack itemstack1 = this.angler.getHeldItemOffhand();
        boolean flag = itemstack.getItem() == BityardItems.FISHING_ROD;
        boolean flag1 = itemstack1.getItem() == BityardItems.FISHING_ROD;

        if (!this.angler.isDead && this.angler.isEntityAlive() && (flag || flag1) && this.getDistanceSqToEntity(this.angler) <= 1024.0D)
        {
            return false;
        }
        else
        {
            this.setDead();
            return true;
        }
    }

    private void updateRotation()
    {
        System.out.println("fish hook: update rotation");
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

        for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
    }

    private void checkCollision()
    {
        System.out.println("fish hook: check collision");
        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (raytraceresult != null)
        {
            vec3d1 = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
        }

        Entity entity = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D));
        double d0 = 0.0D;

        for (Entity entity1 : list)
        {
            if (this.canBeHooked(entity1) && (entity1 != this.angler || this.ticksInAir >= 5))
            {
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
                RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

                if (raytraceresult1 != null)
                {
                    double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

                    if (d1 < d0 || d0 == 0.0D)
                    {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null)
        {
            raytraceresult = new RayTraceResult(entity);
        }

        if (raytraceresult != null && raytraceresult.typeOfHit != RayTraceResult.Type.MISS)
        {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.ENTITY)
            {
                this.caughtEntity = raytraceresult.entityHit;
                this.setHookedEntity();
            }
            else
            {
                this.inGround = true;
            }
        }
    }

    private void setHookedEntity()
    {
        System.out.println("fish hook: set hooked entity");
        this.getDataManager().set(DATA_HOOKED_ENTITY, Integer.valueOf(this.caughtEntity.getEntityId() + 1));
    }

    private void catchingFish(BlockPos p_190621_1_)
    {
        System.out.println("fish hook: catching fish");
        WorldServer worldserver = (WorldServer)this.world;
        int i = 1;
        BlockPos blockpos = p_190621_1_.up();

        if (this.rand.nextFloat() < 0.25F && this.world.isRainingAt(blockpos))
        {
            ++i;
        }

        if (this.rand.nextFloat() < 0.5F && !this.world.canSeeSky(blockpos))
        {
            --i;
        }

        if (this.ticksCatchable > 0)
        {
            --this.ticksCatchable;

            if (this.ticksCatchable <= 0)
            {
                this.ticksCaughtDelay = 0;
                this.ticksCatchableDelay = 0;
            }
            else
            {
                this.motionY -= 0.2D * (double)this.rand.nextFloat() * (double)this.rand.nextFloat();
            }
        }
        else if (this.ticksCatchableDelay > 0)
        {
            this.ticksCatchableDelay -= i;

            if (this.ticksCatchableDelay > 0)
            {
                this.fishApproachAngle = (float)((double)this.fishApproachAngle + this.rand.nextGaussian() * 4.0D);
                float f = this.fishApproachAngle * 0.017453292F;
                float f1 = MathHelper.sin(f);
                float f2 = MathHelper.cos(f);
                double d0 = this.posX + (double)(f1 * (float)this.ticksCatchableDelay * 0.1F);
                double d1 = (double)((float)MathHelper.floor(this.getEntityBoundingBox().minY) + 1.0F);
                double d2 = this.posZ + (double)(f2 * (float)this.ticksCatchableDelay * 0.1F);
                Block block = worldserver.getBlockState(new BlockPos(d0, d1 - 1.0D, d2)).getBlock();

                if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
                {
                    if (this.rand.nextFloat() < 0.15F)
                    {
                        worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0, d1 - 0.10000000149011612D, d2, 1, (double)f1, 0.1D, (double)f2, 0.0D, new int[0]);
                    }

                    float f3 = f1 * 0.04F;
                    float f4 = f2 * 0.04F;
                    worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, (double)f4, 0.01D, (double)(-f3), 1.0D, new int[0]);
                    worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, (double)(-f4), 0.01D, (double)f3, 1.0D, new int[0]);
                }
                else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
                {
                    if (this.rand.nextFloat() < 0.15F)
                    {
                        worldserver.spawnParticle(EnumParticleTypes.LAVA, d0, d1 - 0.10000000149011612D, d2, 1, (double)f1, 0.1D, (double)f2, 0.0D, new int[0]);
                    }

                    float f3 = f1 * 0.04F;
                    float f4 = f2 * 0.04F;
                    worldserver.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0, (double)f4, 0.01D, (double)(-f3), 1.0D, new int[0]);
                    worldserver.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0, (double)(-f4), 0.01D, (double)f3, 1.0D, new int[0]);
                }
            }
            else
            {
                this.motionY = (double)(-0.4F * MathHelper.nextFloat(this.rand, 0.6F, 1.0F));
                double d3 = this.getEntityBoundingBox().minY + 0.5D;
                Block block = worldserver.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getBlock();
                if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
                {
                    this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                    worldserver.spawnParticle(EnumParticleTypes.LAVA, this.posX, d3, this.posZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0D, (double)this.width, 0.20000000298023224D, new int[0]);
                    worldserver.spawnParticle(EnumParticleTypes.FLAME, this.posX, d3, this.posZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0D, (double)this.width, 0.20000000298023224D, new int[0]);
                }
                else
                {
                    this.playSound(SoundEvents.ENTITY_BOBBER_SPLASH, 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                    worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX, d3, this.posZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0D, (double)this.width, 0.20000000298023224D, new int[0]);
                    worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, this.posX, d3, this.posZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0D, (double)this.width, 0.20000000298023224D, new int[0]);
                }
                this.ticksCatchable = MathHelper.getInt(this.rand, 20, 40);
            }
        }
        else if (this.ticksCaughtDelay > 0)
        {
            this.ticksCaughtDelay -= i;
            float f5 = 0.15F;

            if (this.ticksCaughtDelay < 20)
            {
                f5 = (float)((double)f5 + (double)(20 - this.ticksCaughtDelay) * 0.05D);
            }
            else if (this.ticksCaughtDelay < 40)
            {
                f5 = (float)((double)f5 + (double)(40 - this.ticksCaughtDelay) * 0.02D);
            }
            else if (this.ticksCaughtDelay < 60)
            {
                f5 = (float)((double)f5 + (double)(60 - this.ticksCaughtDelay) * 0.01D);
            }

            if (this.rand.nextFloat() < f5)
            {
                float f6 = MathHelper.nextFloat(this.rand, 0.0F, 360.0F) * 0.017453292F;
                float f7 = MathHelper.nextFloat(this.rand, 25.0F, 60.0F);
                double d4 = this.posX + (double)(MathHelper.sin(f6) * f7 * 0.1F);
                double d5 = (double)((float)MathHelper.floor(this.getEntityBoundingBox().minY) + 1.0F);
                double d6 = this.posZ + (double)(MathHelper.cos(f6) * f7 * 0.1F);
                Block block1 = worldserver.getBlockState(new BlockPos((int)d4, (int)d5 - 1, (int)d6)).getBlock();

                if (block1 == Blocks.WATER || block1 == Blocks.FLOWING_WATER)
                {
                    worldserver.spawnParticle(EnumParticleTypes.WATER_SPLASH, d4, d5, d6, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
                }
                else if (block1 == Blocks.LAVA || block1 == Blocks.FLOWING_LAVA)
                {
                    worldserver.spawnParticle(EnumParticleTypes.FLAME, d4, d5, d6, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
                }
            }

            if (this.ticksCaughtDelay <= 0)
            {
                this.fishApproachAngle = MathHelper.nextFloat(this.rand, 0.0F, 360.0F);
                this.ticksCatchableDelay = MathHelper.getInt(this.rand, 20, 80);
            }
        }
        else
        {
            this.ticksCaughtDelay = MathHelper.getInt(this.rand, 100, 600);
            this.ticksCaughtDelay -= this.field_191519_ax * 20 * 5;
        }
    }

    @Override
    protected boolean canBeHooked(Entity p_189739_1_)
    {
        System.out.println("fish hook: can be hooked?");
        return p_189739_1_.canBeCollidedWith() || p_189739_1_ instanceof EntityItem;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
    }

    @Override
    public int handleHookRetraction()
    {
        System.out.println("fish hook: handle hook retraction");
        if (!this.world.isRemote && this.angler != null)
        {
            int i = 0;

            if (this.caughtEntity != null)
            {
                this.bringInHookedEntity();
                this.world.setEntityState(this, (byte)31);
                i = this.caughtEntity instanceof EntityItem ? 3 : 5;
            }
            else if (this.ticksCatchable > 0)
            {
                LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
                lootcontext$builder.withLuck((float)this.field_191518_aw + this.angler.getLuck());

                for (ItemStack stack : this.world.getLootTableManager().getLootTableFromLocation(lootTableWithContext()).generateLootForPools(this.rand, lootcontext$builder.build()))
                {
                    if (stack.getItem() == BityardItems.FISH)
                    {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setString("size", String.valueOf(ItemFish.FishType.byMetadata(stack.getMetadata()).getRandomizedSize()));
                        stack.setTagCompound(tag);
                        this.angler.addStat(StatList.FISH_CAUGHT, 1);
                    }
                    EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, stack);
                    entityitem.setEntityInvulnerable(true);
                    double d0 = this.angler.posX - this.posX;
                    double d1 = this.angler.posY - this.posY;
                    double d2 = this.angler.posZ - this.posZ;
                    double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    double d4 = 0.1D;
                    entityitem.motionX = d0 * 0.1D;
                    entityitem.motionY = d1 * 0.1D + (double)MathHelper.sqrt(d3) * 0.08D;
                    entityitem.motionZ = d2 * 0.1D;
                    this.world.spawnEntity(entityitem);
                    this.angler.world.spawnEntity(new EntityXPOrb(this.angler.world, this.angler.posX, this.angler.posY + 0.5D, this.angler.posZ + 0.5D, this.rand.nextInt(6) + 1));
                }

                i = 1;
            }

            if (this.inGround)
            {
                i = 2;
            }

            this.setDead();
            return i;
        }
        else
        {
            return 0;
        }
    }

    private ResourceLocation lootTableWithContext()
    {
        ResourceLocation lootTableLocation;

        Biome biome = world.getBiome(this.getPosition());
        boolean isDaytime = world.isDaytime();
        int weather = world.isThundering() ? 2 : world.isRaining() ? 1 : 0;
        BlockPos pos = this.getPosition();

        if // Ocean
        (
            biome == Biomes.BEACH ||
            biome == Biomes.STONE_BEACH ||
            biome == Biomes.COLD_BEACH ||
            biome == Biomes.OCEAN ||
            biome == Biomes.DEEP_OCEAN ||
            biome == Biomes.FROZEN_OCEAN
        )
        {
            if (biome == Biomes.DEEP_OCEAN)
            {
                if (!isDaytime)
                {
                    lootTableLocation = BityardLootTables.FISHING_DEEP_OCEAN_NIGHT;
                }
                else
                {
                    lootTableLocation = BityardLootTables.FISHING_DEEP_OCEAN;
                }

            }
            else
            {
                lootTableLocation = BityardLootTables.FISHING_OCEAN;
            }
        }
        else if // Jungle
        (
            biome == Biomes.JUNGLE || biome == Biomes.JUNGLE_EDGE || biome == Biomes.JUNGLE_HILLS ||
            biome == Biomes.MUTATED_JUNGLE || biome == Biomes.MUTATED_JUNGLE_EDGE
        )
        {
            lootTableLocation = BityardLootTables.FISHING_JUNGLE;
        }
        else if (biome == Biomes.SWAMPLAND || biome == Biomes.MUTATED_SWAMPLAND) // Swamp
        {
            lootTableLocation = BityardLootTables.FISHING_SWAMP;
        }
        else if (biome == Biomes.RIVER) // River
        {
            lootTableLocation = BityardLootTables.FISHING_RIVER;
        }
        else if (biome.getTempCategory() == Biome.TempCategory.WARM) // Hot, arid
        {
            lootTableLocation = BityardLootTables.FISHING_ARID;
        }
        else if (biome.getTempCategory() == Biome.TempCategory.COLD) // Cold, frozen
        {
            lootTableLocation = BityardLootTables.FISHING_COLD;
        }
        else // Freshwater
        {
            if (weather == 2)
            {
                lootTableLocation = BityardLootTables.FISHING_FRESHWATER_THUNDER;
            }
            else
            {
                lootTableLocation = BityardLootTables.FISHING_FRESHWATER;
            }
        }

        return lootTableLocation;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        System.out.println("fish hook: handle status update");
        super.handleStatusUpdate(id);
    }

    @Override
    protected void bringInHookedEntity()
    {
        System.out.println("fish hook: bring in hooked entity");
        if (this.angler != null)
        {
            double d0 = this.angler.posX - this.posX;
            double d1 = this.angler.posY - this.posY;
            double d2 = this.angler.posZ - this.posZ;
            double d3 = 0.25D;
            this.caughtEntity.motionX += d0 * d3;
            this.caughtEntity.motionY += Math.max(d1 * d3, 0.6D);
            this.caughtEntity.motionZ += d2 * d3;
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking()
    {
        System.out.println("fish hook: can trigger walking?");
        return false;
    }

    /**
     * Will get destroyed next tick.
     */
    @Override
    public void setDead()
    {
        System.out.println("fish hook: set dead");
        super.setDead();
    }

    @Override
    public Entity getThrower() {
        return this.angler;
    }

    @Override
    public void setThrower(Entity entity) {
        // do nothing
    }

    @Override
    public EntityPlayer getAngler()
    {
        return this.angler;
    }

    static enum State
    {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING;
    }
}
