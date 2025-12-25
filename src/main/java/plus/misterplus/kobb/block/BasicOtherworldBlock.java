package plus.misterplus.kobb.block;

import com.klikli_dev.occultism.api.common.data.OtherworldBlockTier;
import com.klikli_dev.occultism.common.block.otherworld.IOtherworldBlock;
import dev.latvian.mods.kubejs.block.custom.BasicKubeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import plus.misterplus.kobb.builder.block.OtherworldBlockBuilder;

import javax.annotation.Nullable;
import java.util.List;

public class BasicOtherworldBlock extends BasicKubeBlock implements IOtherworldBlock {
    private final OtherworldBlockBuilder builder;

    public BasicOtherworldBlock(OtherworldBlockBuilder builder) {
        super(builder);
        this.builder = builder;
        this.registerDefaultState(this.defaultBlockState().setValue(UNCOVERED, false));
    }

    @Override
    public Block getUncoveredBlock() {
        return builder.uncoveredBlock;
    }

    @Override
    public Block getCoveredBlock() {
        return builder.coveredBlock;
    }

    @Override
    public OtherworldBlockTier getTier() {
        return builder.tier;
    }

    @Override
    public String getDescriptionId() {
        return builder.coveredBlock.getDescriptionId();
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state,
                              @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, IOtherworldBlock.super.getHarvestState(player, state, stack), te,
                stack);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(LevelReader worldIn, BlockPos pos, BlockState state) {
        return IOtherworldBlock.super.getItem(worldIn, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UNCOVERED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (builder.useCustomLoot) {
            return super.getDrops(state, params);
        }
        LootParams lootparams = params.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
        ServerLevel serverlevel = lootparams.getLevel();
        LootTable lootTableUncovered = serverlevel.getServer().reloadableRegistries().getLootTable(this.getUncoveredBlock().getLootTable());
        LootTable lootTableCovered = serverlevel.getServer().reloadableRegistries().getLootTable(this.getCoveredBlock().getLootTable());
        return state.getValue(UNCOVERED) ? lootTableUncovered.getRandomItems(lootparams) : lootTableCovered.getRandomItems(lootparams);
    }
}
