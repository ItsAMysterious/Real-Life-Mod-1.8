package itsamysterious.mods.reallifemod.core.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MountHandler implements IMessageHandler<MountVehicleMessage, MountVehicleMessage> {

	@Override
	public MountVehicleMessage onMessage(final MountVehicleMessage message, final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj; 
		mainThread.addScheduledTask(new Runnable() {
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			World world = ctx.getServerHandler().playerEntity.worldObj;
			Entity v = world.getEntityByID(message.vehicleId);

			@Override
			public void run() {
				if (v != null) {
					v.interactFirst(player);
				}
			}
		});

		return null;
	}

}