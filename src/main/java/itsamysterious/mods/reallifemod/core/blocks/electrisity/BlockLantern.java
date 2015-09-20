package itsamysterious.mods.reallifemod.core.blocks.electrisity;

import java.util.Random;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLantern extends RLMBlockContainer {
	private boolean isBurning;
	private float rotation;
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{B3DLoader.B3DFrameProperty.instance});
	public BlockLantern() {
		super(Material.iron);
		setUnlocalizedName("blockLantern");
		setCreativeTab(RealLifeMod.Cars);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
		RealLifeMod_Blocks.blockList.add(this);
		setBlockBounds(0.3f, 0, 0.3f, 0.7f, 1, 0.7f);
	}


	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos) {
		TileEntity_Lantern tile = (TileEntity_Lantern) world.getTileEntity(pos);
		if (tile.isActive) {
			lightValue = 10;
		} else if (!tile.isActive) {
			lightValue = 0;
		}
		return lightValue;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	protected BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[]{}, new IUnlistedProperty[]{B3DLoader.B3DFrameProperty.instance});
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.randomDisplayTick(worldIn, pos, state, rand);
		TileEntity_Lantern tile = (TileEntity_Lantern) worldIn.getTileEntity(pos);
		if (tile.isActive) {
			this.isBurning = true;
			setLightLevel(10.0f);
		} else {
			setLightLevel(0);
			isBurning = false;
		}
		worldIn.markBlockForUpdate(pos);
		worldIn.markBlockRangeForRenderUpdate(pos.subtract(new Vec3i(-5,0,-5)), pos.add(5,0,5));
		worldIn.notifyNeighborsOfStateChange(pos, this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_Lantern();
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

}
