package plus.misterplus.kobb.builder.item;

import com.klikli_dev.occultism.registry.OccultismBlockItem;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class OtherBlockItemBuilder extends BlockItemBuilder {

    public OtherBlockItemBuilder(ResourceLocation id) {
        super(id);
    }

    @Override
    public Item createObject() {
        return new OccultismBlockItem(blockBuilder.get(), createItemProperties());
    }

}
