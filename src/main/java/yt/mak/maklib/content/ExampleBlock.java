package yt.mak.maklib.content;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import yt.mak.maklib.annotations.AutoRegister;

@AutoRegister(name = "example_block", type = AutoRegister.Type.BLOCK)
public class ExampleBlock extends Block {
    public ExampleBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(3.0f)
                .requiresCorrectToolForDrops());
    }
}