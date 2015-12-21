package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.sounds.CustomSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlaySoundHandler implements IMessageHandler<PacketPlaySound, PacketPlaySound> {

	@Override
	public PacketPlaySound onMessage(final PacketPlaySound message, final MessageContext ctx) {
		if(CustomSound.getSoundWithPosition(message.sound, message.posX, message.posY, message.posZ, message.volume, message.pitch).isDonePlaying()){
			FMLClientHandler.instance().getClient().getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation("reallifemod:"+message.sound), message.posX, message.posY, message.posZ, 1, 1));
		}else
		{
			RealLifeMod.log("Not finished playing!");
		}


		return null;
	}

}
