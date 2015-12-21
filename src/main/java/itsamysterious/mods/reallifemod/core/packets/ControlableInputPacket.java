package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import itsamysterious.mods.reallifemod.api.IControllable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlableInputPacket extends RLMPacketBase {
	public int key;

	public ControlableInputPacket() {
	}

	public ControlableInputPacket(int k) {
		key = k;
	}

	@Override
	public void handleServerSide(EntityPlayerMP playerEntity) {
		if (playerEntity.ridingEntity != null && playerEntity.ridingEntity instanceof IControllable) {
			((IControllable) playerEntity.ridingEntity).pressKey(key, playerEntity);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		key = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(key);
	}

}
