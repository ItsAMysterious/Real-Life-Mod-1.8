package itsamysterious.mods.reallifemod.core.tiles;

import itsamysterious.mods.reallifemod.core.roads.signs.Signfile;
import itsamysterious.mods.reallifemod.core.roads.signs.Signs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RLMTileEntity extends TileEntity {

	public float rotation;

	public RLMTileEntity() {
		
	}
	
	@Override
    public void writeToNBT(NBTTagCompound compound){
    	super.writeToNBT(compound);
    	NBTTagCompound tag=new NBTTagCompound();
    	tag.setFloat("Rotation", rotation);
    	compound.setTag("RLMTileTag", tag);

    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	NBTTagCompound tag=compound.getCompoundTag("RLMTileTag");
    	rotation=tag.getFloat("Rotation");
    }
	

}
