package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
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
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			@Override
			public void run() {
				RLMPlayerProps props = RLMPlayerProps.get(player);
				if(props==null){
					System.out.println("Properties are NULL!!");
				}
				props.name=message.name;
				props.surname=message.surname;
				props.gender=EnumGender.getFromString(message.gender);
				RealLifeMod.network.sendTo(new PropertiesSetPackage(), player);
			}
		});
		return null;
	}

}
