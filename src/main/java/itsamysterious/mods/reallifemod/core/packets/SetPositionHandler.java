package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.gui.lifesystem.Overlay.RLMOverlay;
import itsamysterious.mods.reallifemod.core.vehicles.EntityDriveable;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetPositionHandler implements IMessageHandler<UpdateVehiclePacket, UpdateVehiclePacket> {

	@Override
	public UpdateVehiclePacket onMessage(final UpdateVehiclePacket message, final MessageContext ctx) {
		final IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			World world = ctx.getServerHandler().playerEntity.worldObj;
			EntityDriveable v = (EntityDriveable) world.getEntityByID(message.id);

			@Override
			public void run() {
				v.setPositionAndRotation(message.x, message.y, message.z, message.newRot, 0);
			}
		});
		return null;
	}

}
