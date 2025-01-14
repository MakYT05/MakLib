package yt.mak.maklib.utils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yt.mak.maklib.annotations.AutoRegister;

import java.lang.reflect.Modifier;
import java.util.List;

public class RegistryHelper {
    private final DeferredRegister<Block> blockRegister;
    private final DeferredRegister<Item> itemRegister;

    public RegistryHelper(String modId) {
        this.blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
        this.itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
    }

    public void register(IEventBus eventBus) {
        blockRegister.register(eventBus);
        itemRegister.register(eventBus);
    }

    public void autoRegister(String basePackage) {
        List<Class<?>> annotatedClasses = ClassScanner.findClassesWithAnnotation(basePackage, AutoRegister.class);
        for (Class<?> clazz : annotatedClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                AutoRegister annotation = clazz.getAnnotation(AutoRegister.class);
                String name = annotation.name();

                try {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    if (instance instanceof Block block) {
                        registerBlock(name, block);
                    } else if (instance instanceof Item item) {
                        registerItem(name, item);
                    } else {
                        throw new IllegalArgumentException("Unsupported type: " + clazz.getSimpleName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void registerBlock(String name, Block block) {
        blockRegister.register(name, () -> block);
        itemRegister.register(name, () -> new BlockItem(block, new Item.Properties()));
    }

    private void registerItem(String name, Item item) {
        itemRegister.register(name, () -> item);
    }
}