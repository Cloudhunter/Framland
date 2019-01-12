package uk.gaz492.framland.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.gaz492.framland.Framland;
import uk.gaz492.framland.util.ModInformation;

import static net.minecraftforge.event.ForgeEventFactory.onHoeUse;

public class ItemThunderHoe extends Item {
    public static final ResourceLocation THUNDERHOE = new ResourceLocation(ModInformation.MOD_ID, "thunderhoe");


    public ItemThunderHoe() {
        setRegistryName(THUNDERHOE);
        setUnlocalizedName(ModInformation.MOD_ID + ".thunderhoe");
        setCreativeTab(Framland.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL;
        } else {
            int hook = onHoeUse(itemstack, player, world, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                if (block == Blocks.FARMLAND) {
                    world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
                }

                if (block == Blocks.DIRT) {
                    switch (iblockstate.getValue(BlockDirt.VARIANT)) {
                        case DIRT:
                            world.setBlockState(pos, Blocks.FARMLAND.getDefaultState(), 3);
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            world.setBlockState(pos, Blocks.FARMLAND.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), 3);
                            return EnumActionResult.SUCCESS;
                    }
                }
            }

            return EnumActionResult.PASS;
        }
    }
}
