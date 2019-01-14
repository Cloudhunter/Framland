package uk.gaz492.framland.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import uk.gaz492.framland.ConfigHandler;

import javax.annotation.Nullable;

public class TileFramland extends TileEntity implements ITickable {

    private int tickCount = 0;

    public EnergyStorage energy = new EnergyStorage(ConfigHandler.framlandConfig.maxRF, ConfigHandler.framlandConfig.maxRF, ConfigHandler.framlandConfig.maxRF);

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = super.serializeNBT();
        nbt.setInteger("energy", energy.getEnergyStored());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        energy = new EnergyStorage(ConfigHandler.framlandConfig.maxRF, ConfigHandler.framlandConfig.maxRF, ConfigHandler.framlandConfig.maxRF, nbt.getInteger("energy"));
//        energy = new EnergyStorage(nbt.getInteger("energy"));
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

    @Override
    public void update() {
        if (!world.isRemote) {
            Block blockUp = world.getBlockState(pos.up()).getBlock();
            if (blockUp instanceof IGrowable) {
                int growthTick = world.rand.nextInt((ConfigHandler.framlandConfig.maxGrowthTicks - ConfigHandler.framlandConfig.minGrowthTicks) + 1) + ConfigHandler.framlandConfig.minGrowthTicks;
                if (tickCount >= growthTick) {
                    if (energy.getEnergyStored() >= ConfigHandler.framlandConfig.rfToGrow) {
                        IBlockState blockPlant = world.getBlockState(pos.up(1));
                        IGrowable iGrowable = (IGrowable) blockPlant.getBlock();
                        if (iGrowable.canGrow(world, pos.up(1), blockPlant, false)) {
                            tickCount = 0;
                            energy.extractEnergy(ConfigHandler.framlandConfig.rfToGrow, false);
                            world.playEvent(2005, pos.up(1), 0);
                            iGrowable.grow(world, world.rand, pos.up(), blockPlant);
                            world.markBlockRangeForRenderUpdate(pos, pos);
                        }
                    }
                }
                tickCount++;
            }
        }
    }
}