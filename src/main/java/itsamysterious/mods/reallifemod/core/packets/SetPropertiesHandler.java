package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.lifesystem.EnumJob;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.lifesystem.enums.EnumGender;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetPropertiesHandler implements IMessageHandler<SetPropertiesPackage, SetPropertiesPackage> {

	public SetPropertiesHandler() {
	}

	@Override
	public SetPropertiesPackage onMessage(final SetPropertiesPackage message, final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				EntityPlayerMP player = ctx.getServerHandler().playerEntity;
				RLMPlayerProps.get(player).name = message.name;
				RLMPlayerProps.get(player).surname = message.surname;
				RLMPlayerProps.get(player).gender = EnumGender.valueOf(message.gender);
				RLMPlayerProps.get(player).profession = EnumJob.valueOf(message.profession);
				System.out.println(RLMPlayerProps.get(player).name + " " + RLMPlayerProps.get(player).surname + " "
						+ String.valueOf(RLMPlayerProps.get(player).gender));
			}
		});
		return null;
	}

}
