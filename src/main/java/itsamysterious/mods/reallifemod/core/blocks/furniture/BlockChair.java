package itsamysterious.mods.reallifemod.core.blocks.furniture;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Chair;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockChair extends RLMBlockContainer {

	public BlockChair() {
		super(Material.wood);
		setUnlocalizedName("blockChair");
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
		setCreativeTab(RealLifeMod.RLMFurniture);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		TileEntity_Chair tile = (TileEntity_Chair) world.getTileEntity(pos);
		tile.sitDown(player);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_Chair();
	}

}
