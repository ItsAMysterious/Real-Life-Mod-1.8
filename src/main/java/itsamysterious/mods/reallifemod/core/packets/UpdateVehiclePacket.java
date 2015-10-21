package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import itsamysterious.mods.reallifemod.core.vehicles.VehicleFile;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class UpdateVehiclePacket implements IMessage{
	double x;
	double y;
	double z;
	int id;
	float newRot;
	
	
	public UpdateVehiclePacket() {
	}
	
	public UpdateVehiclePacket(int id, double newX, double newY, double newZ, float newRot, String filename){
		this.x=newX;
		this.y=newY;
		this.z=newZ;
		this.newRot=newRot;
		this.id=id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x=buf.readDouble();
		this.y=buf.readDouble();
		this.z=buf.readDouble();
		this.newRot=buf.readFloat();
		this.id=buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(newRot);
		buf.writeInt(id);
	}

}
