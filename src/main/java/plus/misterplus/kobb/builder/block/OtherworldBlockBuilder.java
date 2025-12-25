package plus.misterplus.kobb.builder.block;

import com.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.client.ModelGenerator;
import dev.latvian.mods.kubejs.client.VariantBlockStateGenerator;
import dev.latvian.mods.kubejs.generator.KubeDataGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ID;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import plus.misterplus.kobb.block.BasicOtherworldBlock;
import plus.misterplus.kobb.builder.item.OtherBlockItemBuilder;

public class OtherworldBlockBuilder extends BlockBuilder {
    public transient Block coveredBlock;
    public transient Block uncoveredBlock;
    public transient OtherworldBlockTier tier;
    public transient boolean useCustomLoot;

    public OtherworldBlockBuilder(ResourceLocation id) {
        super(id);
        this.useCustomLoot = false;
    }

    @Info("Sets the covered block.")
    public BlockBuilder coveredBlock(Block block) {
        this.coveredBlock = block;
        return this;
    }

    @Info("Sets the uncovered block.")
    public BlockBuilder uncoveredBlock(Block block) {
        this.uncoveredBlock = block;
        return this;
    }

    @Info("Sets the tier needed for harvesting.")
    public BlockBuilder tier(int tier) {
        this.tier = OtherworldBlockTier.get(tier);
        return this;
    }

    @Info("Sets this block to use its own loot table rather than mimicking")
    public BlockBuilder useCustomLoot() {
        this.useCustomLoot = true;
        return this;
    }

    @HideFromJS
    private LootItemCondition.Builder uncoveredCondition(Block block) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties()
                        .hasProperty(IOtherworldBlock.UNCOVERED, true));
    }

    @HideFromJS
    @Override
    public LootTable generateLootTable(KubeDataGenerator generator) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(this.uncoveredBlock)
                        .when(this.uncoveredCondition(this.get()))
                        .otherwise(LootItem.lootTableItem(this.coveredBlock))
                );
        return LootTable.lootTable().withPool(builder).build();
    }

    @HideFromJS
    protected ItemBuilder getOrCreateItemBuilder() {
        return itemBuilder == null ? (itemBuilder = new OtherBlockItemBuilder(id)) : itemBuilder;
    }

    @Override
    protected void generateBlockState(VariantBlockStateGenerator bs) {
        bs.simpleVariant("uncovered=true", uncoveredBlock.kjs$getIdLocation().withPath(ID.BLOCK));
        bs.simpleVariant("uncovered=false", coveredBlock.kjs$getIdLocation().withPath(ID.BLOCK));
    }

    @Override
    protected void generateItemModel(ModelGenerator m) {
        m.parent(coveredBlock.kjs$getIdLocation().withPath(ID.BLOCK));
    }

    @Override
    public Block createObject() {
        return new BasicOtherworldBlock(this);
    }
}
