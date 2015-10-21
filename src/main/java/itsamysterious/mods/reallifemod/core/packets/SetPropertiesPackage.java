package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SetPropertiesPackage implements IMessage{
	public String gender;
	public String name;
	public String surname;
	public int id;
	
	public SetPropertiesPackage(){}
	
	public SetPropertiesPackage(int id, String name, String surname, String gender) {
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		name = ByteBufUtils.readUTF8String(buf);
		surname = ByteBufUtils.readUTF8String(buf);
		gender = ByteBufUtils.readUTF8String(buf);
		id=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        ByteBufUtils.writeUTF8String(buf, surname);
        ByteBufUtils.writeUTF8String(buf, gender);
        buf.writeInt(id);

	}

}
