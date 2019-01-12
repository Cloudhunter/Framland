package uk.gaz492.framland.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.gaz492.framland.Framland;
import uk.gaz492.framland.ModBlocks;
import uk.gaz492.framland.util.ModInformation;

import java.util.Collections;
import java.util.Random;

public class BlockFramland extends Block {

    public static final ResourceLocation FRAMLAND = new ResourceLocation(ModInformation.MOD_ID, "framland");

    public BlockFramland() {
        super(Material.GROUND);
        setRegistryName(FRAMLAND);
        setUnlocalizedName(ModInformation.MOD_ID + ".framland");
        setCreativeTab(Framland.creativeTab);
        setHardness(0.6f);
        setTickRandomly(true);
        setHarvestLevel("shovel", 0);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public static void triggerTransformation(World world, BlockPos pos) {
        world.spawnParticle(EnumParticleTypes.NOTE, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.2D, (double) pos.getZ() + 0.5D, 24.0D, 0.0D, 0.0D);
        world.playEvent(1033, pos, 0);
        world.setBlockState(pos, ModBlocks.blockFramland.getDefaultState(), 3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        BlockPos plantPos = pos.up(1);
        EnumPlantType plantType = plantable.getPlantType(world, plantPos);
        return plantType == EnumPlantType.Crop;
    }

    private int[] growthLevel(IBlockState blockState) {
        for (IProperty<?> property : blockState.getProperties().keySet()) {
            if (!"age".equals(property.getName())) continue;
            if (property.getValueClass() == Integer.class) {
                IProperty<Integer> integerProperty = (IProperty<Integer>) property;
                int age = blockState.getValue(integerProperty);
                int maxAge = Collections.max(integerProperty.getAllowedValues());
                return new int[]{age, maxAge};
            }
        }
        return null;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        Block blockUp = world.getBlockState(pos.up()).getBlock();

        if (blockUp instanceof BlockCrops || blockUp instanceof BlockStem) {
            IBlockState blockPlant = world.getBlockState(pos.up());
            int[] plantGrowthLevel = growthLevel(blockPlant);
            if (plantGrowthLevel != null) {
                if (plantGrowthLevel[0] != plantGrowthLevel[1]) {
                    world.playEvent(2005, pos.up(), 0);
                    IGrowable iGrowable = (IGrowable) blockPlant.getBlock();
                    iGrowable.grow(world, rand, pos.up(), blockPlant);
                    world.scheduleUpdate(pos, this.getDefaultState().getBlock(), 20);
                }
            }
        } else if (blockUp instanceof IPlantable) {
            blockUp.updateTick(world, pos.up(), world.getBlockState(pos.up()), rand);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, world, pos, blockIn, fromPos);

        if (world.getBlockState(pos.up()).getMaterial().isSolid()) {
            world.setBlockState(pos, Blocks.CLAY.getDefaultState(), 3);
        } else if (world.getBlockState(pos.up()).getBlock() instanceof BlockCrops || world.getBlockState(pos.up()).getBlock() instanceof BlockStem) {
            world.scheduleUpdate(pos, this.getDefaultState().getBlock(), 20);
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);

        IBlockState blockUpState = world.getBlockState(pos.up());
        System.out.println(blockUpState.getBlock());
        if (blockUpState.getMaterial().isSolid()) {

            world.setBlockState(pos, Blocks.CLAY.getDefaultState(), 3);
        } else if (blockUpState.getBlock() instanceof BlockCrops || blockUpState.getBlock() instanceof BlockStem) {
            world.scheduleUpdate(pos, this.getDefaultState().getBlock(), 20);
        }
    }

}
