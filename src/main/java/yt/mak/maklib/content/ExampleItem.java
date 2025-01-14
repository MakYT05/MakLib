package yt.mak.maklib.content;

import net.minecraft.world.item.Item;
import yt.mak.maklib.annotations.AutoRegister;

@AutoRegister(name = "example_item")
public class ExampleItem extends Item {
    public ExampleItem() {
        super(new Item.Properties().stacksTo(64));
    }
}