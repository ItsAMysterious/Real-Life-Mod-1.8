package itsamysterious.mods.reallifemod.core.blocks.tiles;

import itsamysterious.mods.reallifemod.core.roads.signs.Signfile;
import itsamysterious.mods.reallifemod.core.roads.signs.Signs;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class TileEntity_Sign extends RLMTileEntity{
	public Signfile file;
	public float rotation;
	public String signname;
	
	public TileEntity_Sign() {
	}
	
	public TileEntity_Sign(Signfile f, int i) {
		this.file=f;
		this.signname=f.name;
		this.rotation=i*90;
	}

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
    	super.readFromNBT(compound);
    	this.rotation=compound.getFloat("SignRotation");
    	this.signname=compound.getString("SignfileName");
    	if(file==null){
    		for(Signfile fi:Signs.signs){
    			if(fi.name==signname){
    				this.file=fi;
    			}
    		}
    	}
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound){
    	super.writeToNBT(compound);
    	if(!signname.isEmpty()){
	    	compound.setFloat("SignRotation", rotation);
	    	compound.setString("SignfileName", signname);
    	}
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
}
