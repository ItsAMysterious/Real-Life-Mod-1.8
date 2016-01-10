package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateToiletHandler implements IMessageHandler<UpdateToiletPacket, UpdateToiletPacket>{
	@Override
	public UpdateToiletPacket onMessage(final UpdateToiletPacket message, final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				World world = ctx.getServerHandler().playerEntity.worldObj;
				RLMPlayerProps.get(ctx.getServerHandler().playerEntity).poop_value = message.newpoopvalue;
				System.out.println("New poopvalue is"+message.newpoopvalue);

			}
		});

		return null;
	}
}
