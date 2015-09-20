package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PropertiesSetPackage implements IMessage{
	public String gender;
	public String name;
	public String surname;
	
	public PropertiesSetPackage() {
	}
	
	public PropertiesSetPackage(RLMPlayerProps props){
		this.name=props.getName();
		this.surname=props.getSurname();
		this.gender=props.getGender();
	}



	@Override
	public void fromBytes(ByteBuf buf) {
		name = ByteBufUtils.readUTF8String(buf);
		surname = ByteBufUtils.readUTF8String(buf);
		gender = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        ByteBufUtils.writeUTF8String(buf, surname);
        ByteBufUtils.writeUTF8String(buf, gender);

	}


	



}
