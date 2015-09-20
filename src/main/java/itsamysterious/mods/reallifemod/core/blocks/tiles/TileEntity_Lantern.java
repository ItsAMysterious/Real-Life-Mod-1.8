package itsamysterious.mods.reallifemod.core.blocks.tiles;

import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import scala.collection.generic.VolatileAbort;

public class TileEntity_Lantern extends TileEntity_Electric {
	public boolean isActive;
	
	public TileEntity_Lantern() {
	}
	
    public TileEntity_Lantern(int i) {
    	this.rotation=i;
    }

	@Override
    public void writeToNBT(NBTTagCompound compound){
    	super.writeToNBT(compound);
    	NBTTagCompound tag=new NBTTagCompound();
    	tag.setBoolean("IsActive", isActive);
    	compound.setTag("LanternTag", tag);

    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	NBTTagCompound tag=compound.getCompoundTag("LanternTag");
    	this.isActive=tag.getBoolean("IsActive");
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	super.onDataPacket(net, pkt);
      NBTTagCompound tag = pkt.getNbtCompound();
      readFromNBT(tag);
    }
    
    public Packet getDescriptionPacket()
    {
      super.getDescriptionPacket();
      NBTTagCompound tag = new NBTTagCompound();
      writeToNBT(tag);
      return new S35PacketUpdateTileEntity(getPos(), 1, tag);
    }
    

    
    @Override
    public void update(){
    	super.update();
    	if(this.getVoltage()>0){
    		isActive=true;
    	}else
    		isActive=false;
    	
    	if(isActive){
    		worldObj.getBlockState(getPos()).getBlock().setLightLevel(10.0f);
    	}else
    	{
    		worldObj.getBlockState(getPos()).getBlock().setLightLevel(0);
    	}
		worldObj.markBlockForUpdate(getPos());
    }
    
	public void onPowered(float f) {
		this.isActive=true;
		worldObj.markBlockForUpdate(getPos());
	}

	@Override
	public float getMaxVoltage() {
		return 155;
	}

	@Override
	public float getVoltageValueAfterPowering() {
		return getVoltage();
	}

	@Override
	public float getMinVoltage() {
		return 150;
	}
}
