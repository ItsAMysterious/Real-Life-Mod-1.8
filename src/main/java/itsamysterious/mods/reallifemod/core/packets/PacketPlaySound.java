package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.RealLifeMod;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketPlaySound implements IMessage {

	public float posX, posY, posZ;
	public String sound;
	private boolean distort;
	private boolean silenced;
	public float volume;
	public float pitch;

	public static void sendSoundPacket(double x, double y, double z, double range, int dimension, String s, float volume)
	{	
		sendSoundPacket(x, y, z, range, dimension, s, volume, 1F);
	}
	
	public static void sendSoundPacket(double x, double y, double z, double range, int dimension, String s, float volume, float pitch)
	{
		RealLifeMod.network.sendToAllAround(new PacketPlaySound(x, y, z, s, volume, pitch),new TargetPoint( dimension,x, y, z, (float)range));
	}
	
	public PacketPlaySound()
	{
	}
	
	public PacketPlaySound(double x, double y, double z, String s)
	{
		this(x, y, z, s, 1f);
	}

	public PacketPlaySound(double x, double y, double z, String s, float volume )
	{	
		this(x, y, z, s, volume, 1f);
	}
	
	public PacketPlaySound(double x, double y, double z, String s, float volume2, float pitch2)
	{
		posX = (float)x; posY = (float)y; posZ = (float)z;
		sound = s;
		this.volume = volume2;
		this.pitch = pitch2;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		posX = buf.readFloat();
		posY = buf.readFloat();
		posZ = buf.readFloat();
		sound = ByteBufUtils.readUTF8String(buf);
		volume = buf.readFloat();
		pitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(posX);
		buf.writeFloat(posY);
		buf.writeFloat(posZ);
		ByteBufUtils.writeUTF8String(buf, sound);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
	}

}
