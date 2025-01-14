package yt.mak.maklib.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import yt.mak.maklib.annotations.AutoRegister;

@AutoRegister(name = "block", type = AutoRegister.Type.BLOCK)
public class MyCustomBlock extends Block {
    public MyCustomBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(3.0f)
                .requiresCorrectToolForDrops());
    }
}