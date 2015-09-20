package itsamysterious.mods.reallifemod.core.blocks.tiles;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;

public class TileEntity_Pinwheel extends TileEntity_Electric{
	public double rotorangle;
	public double gondlerotation;
	
	@Override
	public void update(){
		setVoltage(440);
		rotorangle+=2;
		gondlerotation+=0.25;
		if(rotorangle>360){
			rotorangle=1;
		}
		super.update();

	}
	
	@Override
	public double getMaxRenderDistanceSquared() {
		return 100000;
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
		return getVoltage();
	}

	@Override
	public float getMinVoltage() {
		return 0;
	}
	
	@Override
	public boolean isPowerSource() {
		return true;
	}

}