package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DefecatePacket implements IMessage {
	public int EntityId;
	public boolean isPeeing;
	public boolean isPooping;

	public DefecatePacket() {
	}

	public DefecatePacket(boolean peeing, boolean pooping) {
		this.isPeeing = peeing;
		this.isPooping = pooping;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.isPeeing = buf.readBoolean();
		this.isPooping = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isPeeing);
		buf.writeBoolean(isPooping);
	}

}
