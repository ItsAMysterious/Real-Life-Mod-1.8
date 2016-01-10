package itsamysterious.mods.reallifemod.core.packets;

import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.lifesystem.enums.EnumGender;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerPropsSet implements IMessageHandler<PropertiesSetPackage, PropertiesSetPackage> {

	@Override
	public PropertiesSetPackage onMessage(PropertiesSetPackage message, MessageContext ctx) {
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		RLMPlayerProps props = RLMPlayerProps.get(p);
		props.setName(message.name);
		props.setSurname(message.surname);
		props.gender = EnumGender.valueOf(message.gender);
		return null;
	}

}