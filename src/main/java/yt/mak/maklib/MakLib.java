package yt.mak.maklib;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import yt.mak.maklib.init.ModContent;
import yt.mak.maklib.lib.ContentRegistry;

@Mod(MakLib.MODID)
public class MakLib {

    public static final String MODID = "maklib";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final CreativeModeTab MAK_TAB = CREATIVE_MODE_TABS.register("mak_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModContent.ASTRALCOIN.getDefaultInstance())
            .displayItems((parameters, output) -> output.accept(ModContent.ASTRALCOIN))
            .build()).get();

    public MakLib() {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        ContentRegistry.registerAnnotatedContent();
        ContentRegistry.ITEMS.register(eventBus);
        ContentRegistry.BLOCKS.register(eventBus);
        ContentRegistry.ENTITIES.register(eventBus);

        ContentRegistry.register(eventBus);

        CREATIVE_MODE_TABS.register(eventBus);
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) { ContentRegistry.registerAnnotatedContent(); }
}