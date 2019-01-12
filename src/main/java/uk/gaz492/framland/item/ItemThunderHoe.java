package uk.gaz492.framland.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.gaz492.framland.Framland;
import uk.gaz492.framland.util.ModInformation;

public class ItemThunderHoe extends Item {
    public static final ResourceLocation THUNDERHOE = new ResourceLocation(ModInformation.MOD_ID, "thunderhoe");


    public ItemThunderHoe()
    {
        setRegistryName(THUNDERHOE);
        setUnlocalizedName(ModInformation.MOD_ID + ".thunderhoe");
        setCreativeTab(Framland.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
