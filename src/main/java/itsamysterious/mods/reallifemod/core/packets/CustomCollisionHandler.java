package itsamysterious.mods.reallifemod.core.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CustomCollisionHandler implements IMessageHandler<CustomCollisionPacket, CustomCollisionPacket> {

	@Override
	public CustomCollisionPacket onMessage(final CustomCollisionPacket message, final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			Entity e = (Entity) world.getEntityByID(message.id);

			@Override
			public void run() {
				if (e != null) {
					e.motionY=message.newY;
				} else
					System.out.println("Is null");
			}
		});

		return null;
	}

}
