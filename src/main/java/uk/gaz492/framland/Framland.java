package uk.gaz492.framland;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import uk.gaz492.framland.proxy.CommonProxy;
import uk.gaz492.framland.util.ModInformation;

@Mod(modid = ModInformation.MOD_ID, name = ModInformation.MOD_NAME, version = ModInformation.MOD_VERSION, dependencies = ModInformation.MOD_DEPENDENCIES, useMetadata = true)
public class Framland
{

    @SidedProxy(clientSide = ModInformation.CLIENT_PROXY, serverSide = ModInformation.SERVER_PROXY)
    public static CommonProxy proxy;

    public static CreativeTabs creativeTab = new CreativeTabs("Framland") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.blockFramland);
        }
    };

    @Mod.Instance
    public static Framland instance;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }
}
