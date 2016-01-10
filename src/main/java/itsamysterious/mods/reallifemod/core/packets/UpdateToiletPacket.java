package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class UpdateToiletPacket implements IMessage {
	double newpoopvalue;

	public UpdateToiletPacket() {
	}

	public UpdateToiletPacket(double newpoopvalue) {
		this.newpoopvalue = newpoopvalue;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.newpoopvalue = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(this.newpoopvalue);
	}

}
