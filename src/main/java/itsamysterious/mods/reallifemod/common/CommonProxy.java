package itsamysterious.mods.reallifemod.common;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import itsamysterious.mods.reallifemod.core.roads.signs.Signfile;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import itsamysterious.mods.reallifemod.core.vehicles.VehicleFile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.server.FMLServerHandler;

public class CommonProxy {
	public void registerTiles() {
	}

	public void registerRenderThings() {
	}

	public void registerItemModels() {
	}

	public void setupCrafting() {
	}

	public void registerEntities() {

	}

	public void loadCoreModules() {
	}

	public boolean isThePlayer(EntityPlayer player) {
		return player == FMLServerHandler.instance().getServer().getCommandSenderEntity();
	}

}
