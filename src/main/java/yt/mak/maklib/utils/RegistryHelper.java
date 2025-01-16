package yt.mak.maklib.utils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yt.mak.maklib.annotations.AutoRegister;

import java.lang.reflect.Modifier;
import java.util.List;

public class RegistryHelper {
    private final DeferredRegister<Block> blockRegister;
    private final DeferredRegister<Item> itemRegister;
    private final DeferredRegister<EntityType<?>> entityRegister;

    public RegistryHelper(String modId) {
        this.blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
        this.itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
        this.entityRegister = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, modId);
    }

    public void register(IEventBus eventBus) {
        blockRegister.register(eventBus);
        itemRegister.register(eventBus);
        entityRegister.register(eventBus);
    }

    public void autoRegister(String basePackage) {
        List<Class<?>> annotatedClasses = ClassScanner.findClassesWithAnnotation(basePackage, AutoRegister.class);

        for (Class<?> clazz : annotatedClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                AutoRegister annotation = clazz.getAnnotation(AutoRegister.class);

                String name = annotation.name();

                try {
                    Object instance = clazz.getDeclaredConstructor().newInstance();

                    switch (annotation.type()) {
                        case BLOCK -> registerBlock(name, (Block) instance);
                        case ITEM -> registerItem(name, (Item) instance);
                        case ENTITY -> registerEntity(name, clazz);
                        default -> throw new IllegalArgumentException("Unsupported type: " + clazz.getSimpleName());
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

    private void registerEntity(String name, Class<?> entityClass) {
        if (!EntityType.EntityFactory.class.isAssignableFrom(entityClass)) {
            throw new IllegalArgumentException(entityClass.getSimpleName() + " is not a valid EntityType.EntityFactory class.");
        }

        try {
            @SuppressWarnings("unchecked")
            EntityType.EntityFactory<?> factory = (EntityType.EntityFactory<?>) entityClass.getDeclaredConstructor().newInstance();

            EntityType<?> entityType = EntityType.Builder.of(
                            factory,
                            MobCategory.MISC
                    ).sized(0.6F, 1.8F)
                    .build(null);

            entityRegister.register(name, () -> entityType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}