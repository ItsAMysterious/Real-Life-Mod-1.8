package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DefecationHandler implements IMessageHandler<DefecatePacket, DefecatePacket> {

	@Override
	public DefecatePacket onMessage(final DefecatePacket message, final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				World world = ctx.getServerHandler().playerEntity.worldObj;
				RLMPlayerProps.get(ctx.getServerHandler().playerEntity).peeing = message.isPeeing;
				RLMPlayerProps.get(ctx.getServerHandler().playerEntity).pooping = message.isPooping;

			}
		});

		return null;
	}

}
