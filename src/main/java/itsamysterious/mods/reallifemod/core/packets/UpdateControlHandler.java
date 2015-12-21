package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.vehicles.EntityDriveable;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateControlHandler implements IMessageHandler<UpdateControlPackage, UpdateControlPackage> {

	@Override
	public UpdateControlPackage onMessage(final UpdateControlPackage message, final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			World world = ctx.getServerHandler().playerEntity.worldObj;

			@Override
			public void run() {
				if (world.getEntityByID(message.vehicleID) instanceof EntityDriveable) {
					EntityDriveable e = (EntityDriveable) world.getEntityByID(message.vehicleID);
					e.posX = message.posX;
					e.posY = message.posY;
					e.posZ = message.posZ;
					e.motionX = message.motX;
					e.motionY = message.motY;
					e.motionZ = message.motZ;
					e.axes.setAngles(message.yaw, message.pitch, message.roll);
					e.rotationYaw = message.yaw;
					e.rotationPitch = message.pitch;
					e.throttle = message.throttle;
					e.steeringAngle = message.steeringAngle;
					e.ticksSinceEntered = message.ticksSinceEntered;
					e.ticksSinceLeft = message.ticksSinceLeft;
				} else
					RealLifeMod.log(world.getEntityByID(message.vehicleID).getName()
							+ " cannot be an EntityDriveable! Not updating!");
			}
		});

		return null;
	}

}
