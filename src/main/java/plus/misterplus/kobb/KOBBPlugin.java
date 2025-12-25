package plus.misterplus.kobb;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import net.minecraft.core.registries.Registries;
import plus.misterplus.kobb.builder.block.OtherworldBlockBuilder;

public class KOBBPlugin implements KubeJSPlugin {
    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.BLOCK, reg -> {
            reg.add(KOBB.id("otherworld"), OtherworldBlockBuilder.class, OtherworldBlockBuilder::new);
        });
    }
}
