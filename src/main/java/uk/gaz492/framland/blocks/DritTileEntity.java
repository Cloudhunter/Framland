package uk.gaz492.framland.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import uk.gaz492.framland.ConfigHandler;
import uk.gaz492.framland.ModBlocks;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Random;

public class DritTileEntity extends TileEntity implements ITickable {

    private int tickCount = 0;

    public EnergyStorage energy = new EnergyStorage(50000, 50000, 50000);

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = super.serializeNBT();
        nbt.setInteger("energy", energy.getEnergyStored());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        energy = new EnergyStorage(nbt.getInteger("energy"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ? (T) energy : super.getCapability(capability, facing);
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
    public void update() {
        if (!world.isRemote) {
            Block blockUp = world.getBlockState(pos.up()).getBlock();
            if (blockUp instanceof BlockCrops || blockUp instanceof BlockStem) {
                int growthTick = new Random().nextInt((ConfigHandler.dritConfig.maxGrowthTicks - ConfigHandler.dritConfig.minGrowthTicks) + 1) + ConfigHandler.dritConfig.minGrowthTicks;
                if (tickCount >= growthTick) {
                    if (energy.getEnergyStored() >= ConfigHandler.dritConfig.rfToGrow) {
                        IBlockState blockPlant = world.getBlockState(pos.up(1));
                        int[] plantGrowthLevel = growthLevel(blockPlant);
                        if (plantGrowthLevel != null) {
                            if (plantGrowthLevel[0] != plantGrowthLevel[1]) {
                                tickCount = 0;
                                energy.extractEnergy(ConfigHandler.dritConfig.rfToGrow, false);
                                world.playEvent(2005, pos.up(1), 0);
                                IGrowable iGrowable = (IGrowable) blockPlant.getBlock();
                                iGrowable.grow(world, new Random(), pos.up(), blockPlant);
                                world.markBlockRangeForRenderUpdate(pos, pos);
                            }
                        }
                    }
                }
                tickCount++;
            } else if (blockUp instanceof IPlantable) {
                blockUp.updateTick(world, pos.up(), world.getBlockState(pos.up(1)), new Random());
            }

        }
    }
}
