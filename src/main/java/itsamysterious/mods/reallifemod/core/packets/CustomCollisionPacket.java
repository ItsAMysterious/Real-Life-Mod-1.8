package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CustomCollisionPacket implements IMessage{
	
	public int id;
	public int x;
	public int y;
	public int z;
	public double newY;

	public CustomCollisionPacket() {
	}
	
	public CustomCollisionPacket(int id, int x, int y, int z,double f){
		this.id=id;
		this.x=x;
		this.y=y;
		this.z=z;
		this.newY=f;

	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id=buf.readInt();
		this.x=buf.readInt();
		this.y=buf.readInt();
		this.z=buf.readInt();
		this.newY=buf.readDouble();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeDouble(this.newY);


	}

}
