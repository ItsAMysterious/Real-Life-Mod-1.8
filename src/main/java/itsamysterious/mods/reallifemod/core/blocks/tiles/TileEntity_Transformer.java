package itsamysterious.mods.reallifemod.core.blocks.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntity_Transformer extends TileEntity_Electric{
	private float tempVoltage;
	
	public TileEntity_Transformer() {
	
	}
	
	@Override
	public void update() {
		super.update();
	};
	
	@Override
	public void onPowered(float f) {
	}

	@Override
	public float getMaxVoltage() {
		return 450;
	}
	
	@Override
	public float getVoltageValueAfterPowering() {
		if(getVoltage()>=440){
			this.tempVoltage+=290;
			setVoltage(getVoltage()-290);
		}
		return getVoltage();
	}

	@Override
	public float getMinVoltage() {
		return 440;
	}

}
