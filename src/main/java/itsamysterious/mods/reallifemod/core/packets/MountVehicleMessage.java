package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MountVehicleMessage implements IMessage {
	public int vehicleId;

	public MountVehicleMessage(){
	}
	
	public MountVehicleMessage(int id){
		this.vehicleId=id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.vehicleId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.vehicleId);
	}

}
