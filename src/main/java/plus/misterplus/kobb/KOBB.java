package plus.misterplus.kobb;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;

@Mod(KOBB.MODID)
public class KOBB {
    public static final String MODID = "kobb";

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
