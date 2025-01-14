package yt.mak.maklib.items;

import net.minecraft.world.item.Item;
import yt.mak.maklib.annotations.AutoRegister;

@AutoRegister(name = "item", type = AutoRegister.Type.ITEM)
public class MyCustomItem extends Item {
    public MyCustomItem() {
        super(new Item.Properties());
    }
}