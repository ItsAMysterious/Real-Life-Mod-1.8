package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_VendingMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockVendingMachine extends RLMFurnitureBlock{
	public BlockVendingMachine() {
		super("VendingMachine", new TileEntity_VendingMachine());
	}
	
    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
    	EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
		return new TileEntity_VendingMachine();
    }

	
	

}
