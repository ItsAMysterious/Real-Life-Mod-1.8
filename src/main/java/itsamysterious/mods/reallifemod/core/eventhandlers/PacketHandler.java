package itsamysterious.mods.reallifemod.core.eventhandlers;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.netty.channel.ChannelHandler;
import itsamysterious.mods.reallifemod.core.packets.RLMPacketBase;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@ChannelHandler.Sharable
public class PacketHandler extends SimpleNetworkWrapper {
	// Map of channels for each side
	private EnumMap<Side, FMLEmbeddedChannel> channels;

	
	private ConcurrentLinkedQueue<RLMPacketBase> receivedPacketsClient = new ConcurrentLinkedQueue<RLMPacketBase>();
	private HashMap<String, ConcurrentLinkedQueue<RLMPacketBase>> serverPackets = new HashMap<String, ConcurrentLinkedQueue<RLMPacketBase>>();

	public PacketHandler() {
		super(Reference.ID);
	}

	public void handleServerPackets() {
		for(String playerName : serverPackets.keySet())
		{
			ConcurrentLinkedQueue<RLMPacketBase> receivedPacketsFromPlayer = serverPackets.get(playerName);
			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(playerName); 
			for(RLMPacketBase packet = receivedPacketsFromPlayer.poll(); packet != null; packet = receivedPacketsFromPlayer.poll())
			{
				packet.handleServerSide(player);
			}
		}

	}

	public void sendToServer(RLMPacketBase packet) {
		channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET)
				.set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		channels.get(Side.CLIENT).writeAndFlush(packet);
	}
}
