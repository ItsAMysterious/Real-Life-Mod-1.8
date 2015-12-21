package itsamysterious.mods.reallifemod.core.packets;

import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class KeyHeldHandler implements IMessageHandler<PacketDriveableKeyHeld, PacketDriveableKeyHeld>{

	@Override
	public PacketDriveableKeyHeld onMessage(final PacketDriveableKeyHeld message, final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			World world = ctx.getServerHandler().playerEntity.worldObj;

			@Override
			public void run() {
				if(world.isRemote){
					message.handleServerSide(ctx.getServerHandler().playerEntity);
				}
			}
		});

		return null;	}

}
