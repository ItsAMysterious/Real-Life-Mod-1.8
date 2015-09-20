package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessageHandler<SetPropertiesPackage, SetPropertiesPackage>{
	
	public PacketHandler() {
	}
	
	@Override
	public SetPropertiesPackage onMessage(final SetPropertiesPackage message, final MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj; // or Minecraft.getMinecraft() on the client
        mainThread.addScheduledTask(
        		new Runnable() {
        	EntityPlayer player = ctx.getServerHandler().playerEntity;
            @Override
            public void run() {
            	RLMPlayerProps props=RLMPlayerProps.get(player);
            	props.setName(message.name);
            	props.setSurname(message.surname);
            	props.setGender(message.gender);
            }
        });
		return null;
	}
	

}
