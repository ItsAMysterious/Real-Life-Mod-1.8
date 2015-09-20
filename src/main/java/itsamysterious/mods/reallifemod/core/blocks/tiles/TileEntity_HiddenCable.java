package itsamysterious.mods.reallifemod.core.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntity_HiddenCable extends TileEntity_Electric {
	
	public String block;
	public double energy;

	public TileEntity_HiddenCable() {
	}

	public TileEntity_HiddenCable(Block theBlock) {
		this.block=theBlock.getUnlocalizedName();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("ContainingBlock", block);
		tag.setDouble("Energy",energy);
		compound.setTag("CableTag", tag);
		super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagCompound tag = compound.getCompoundTag("CableTag");
		this.block = tag.getString("ContainingBlock");
		this.energy = tag.getDouble("Energy");

	}
	
	public void update(){
		List<TileEntity_Electric> tiles = new ArrayList<TileEntity_Electric>();
		if(worldObj.getTileEntity(getPos().add(0,0,1)) instanceof TileEntity_Electric)
			tiles.add((TileEntity_Electric) worldObj.getTileEntity(getPos().add(0,0,1)));
		
		if(worldObj.getTileEntity(getPos().add(0,0,-1)) instanceof TileEntity_Electric);
			tiles.add((TileEntity_Electric) worldObj.getTileEntity(getPos().add(0,0,-1)));
		
		if(worldObj.getTileEntity(getPos().add(1,0,0)) instanceof TileEntity_Electric);
			tiles.add((TileEntity_Electric) worldObj.getTileEntity(getPos().add(1,0, 0)));

		if(worldObj.getTileEntity(getPos().add(-1,0,0)) instanceof TileEntity_Electric);
			tiles.add((TileEntity_Electric) worldObj.getTileEntity(getPos().add(-1,0, 0)));

		if(worldObj.getTileEntity(getPos().add(0,1,0)) instanceof TileEntity_Electric);
			tiles.add((TileEntity_Electric) worldObj.getTileEntity(getPos().add(0,1, 0)));

		if(worldObj.getTileEntity(getPos().add(0,-1,0)) instanceof TileEntity_Electric);
			tiles.add((TileEntity_Electric) worldObj.getTileEntity(getPos().add(0 ,-1, 0)));
			
		for(TileEntity_Electric t:tiles){
			t.setVoltage(this.getVoltage()/tiles.size());
		}
		tiles.clear();
	}

	@Override
	public void onPowered(float f) {
	}

	@Override
	public float getMaxVoltage() {
		return 440;
	}

	@Override
	public float getMinVoltage() {
		return 0;
	}

	@Override
	public float getVoltageValueAfterPowering() {
		return getVoltage();
	}


}
