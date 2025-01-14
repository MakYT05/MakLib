package yt.mak.maklib.utils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.maklib.annotations.AutoRegister;
import yt.mak.maklib.blocks.MyCustomBlock;
import yt.mak.maklib.items.MyCustomItem;

public class AutoRegisterManager {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "maklib");
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "maklib");

    public static void initialize() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        scanAndRegister();
    }

    private static void scanAndRegister() {
        registerAnnotatedClass(MyCustomBlock.class);
        registerAnnotatedClass(MyCustomItem.class);
    }

    private static void registerAnnotatedClass(Class<?> clazz) {
        AutoRegister annotation = clazz.getAnnotation(AutoRegister.class);

        if (annotation == null) return;

        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            switch (annotation.type()) {
                case BLOCK -> registerBlock((Block) instance, clazz.getSimpleName().toLowerCase());
                case ITEM -> registerItem((Item) instance, clazz.getSimpleName().toLowerCase());
                case ENTITY -> System.out.println("Entity registration not implemented yet.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerBlock(Block block, String name) {
        RegistryObject<Block> registeredBlock = BLOCKS.register(name, () -> block);
        ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
    }

    private static void registerItem(Item item, String name) {
        ITEMS.register(name, () -> item);
    }
}