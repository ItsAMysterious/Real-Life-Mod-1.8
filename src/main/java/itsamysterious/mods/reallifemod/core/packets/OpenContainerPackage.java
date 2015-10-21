package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class OpenContainerPackage implements IMessage {

	public OpenContainerPackage() {
	}
	
	public OpenContainerPackage(int GUIID, IInventory vehicle) {
		
	}
	
	public OpenContainerPackage(int id, EntityVehicle entityVehicle) {
	}

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

}
