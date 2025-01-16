package yt.mak.maklib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import yt.mak.maklib.utils.AutoRegisterManager;
import yt.mak.maklib.utils.RegistryHelper;

@Mod(MakLib.MODID)
public class MakLib {
    public static final String MODID = "maklib";

    public MakLib(RegistryHelper registryHelper, IEventBus bus) {
        MinecraftForge.EVENT_BUS.register(this);

        AutoRegisterManager.initialize(bus);

        registryHelper.autoRegister("yt.mak.maklib.content");
        registryHelper.register(bus);
    }
}