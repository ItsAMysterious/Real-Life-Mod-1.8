package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TV;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTelevision extends RLMFurnitureBlock{
	public BlockTelevision() {
		super("RLMTelevision", new TileEntity_TV());
		setCreativeTab(RealLifeMod.Cars);
	}

}