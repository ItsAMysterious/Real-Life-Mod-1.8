package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.api.IControllable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketDriveableKeyHeld extends RLMPacketBase {
	private int key;
	private boolean held;

	public PacketDriveableKeyHeld() {
	}

	public PacketDriveableKeyHeld(int key, boolean held) {
		this.key = key;
		this.held = held;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		key = buf.readInt();
		held = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(key);
		buf.writeBoolean(held);
	}

	public void handleServerSide(EntityPlayerMP playerEntity) {
		if (playerEntity.ridingEntity != null && playerEntity.ridingEntity instanceof IControllable) {
			((IControllable) playerEntity.ridingEntity).updateKeyHeldState(key, held);
		}
	}

}
