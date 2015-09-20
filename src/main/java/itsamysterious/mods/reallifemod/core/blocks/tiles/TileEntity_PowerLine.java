package itsamysterious.mods.reallifemod.core.blocks.tiles;

import itsamysterious.mods.reallifemod.core.blocks.electrisity.BlockPowerLine;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntity_PowerLine extends TileEntity_Electric{

    
    public AxisAlignedBB getRenderBoundingBox(){
		return super.getRenderBoundingBox();
    }
    
    @Override
    public void update() {
    	super.update();
    }
    
	@Override
	public void onPowered(float f) {
	}

	@Override
	public float getMaxVoltage() {
		return 440;
	}

	@Override
	public float getVoltageValueAfterPowering() {
		return super.getVoltage();
	}

	@Override
	public float getMinVoltage() {
		return 0;
	}
}
