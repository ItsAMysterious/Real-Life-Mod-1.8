package itsamysterious.mods.reallifemod.core.packets;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.core.vehicles.EntityDriveable;
import net.minecraft.entity.Entity;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateControlPackage implements IMessage {

	public int vehicleID;
	public double posX, posY, posZ;
	public float yaw, pitch, roll;
	public double motX, motY, motZ;
	public float avelX, avelY, avelZ;
	public double throttle;
	public float steeringAngle;
	public int ticksSinceEntered;
	public int ticksSinceLeft;

	public UpdateControlPackage() {
	}

	public UpdateControlPackage(EntityDriveable entity) {
		this.vehicleID = entity.getEntityId();
		this.posX = entity.posX;
		this.posY = entity.posY;
		this.posZ = entity.posZ;
		this.motX = entity.motionX;
		this.motY = entity.motionY;
		this.motZ = entity.motionZ;
		this.yaw = entity.axes.getYaw();
		this.pitch = entity.axes.getPitch();
		this.roll = entity.axes.getRoll();
		this.throttle = entity.throttle;
		this.steeringAngle = entity.steeringAngle;
		this.ticksSinceEntered=entity.ticksSinceEntered;
		this.ticksSinceLeft=entity.ticksSinceLeft;

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.vehicleID = buf.readInt();
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
		this.motX = buf.readDouble();
		this.motY = buf.readDouble();
		this.motZ = buf.readDouble();
		this.yaw = buf.readFloat();
		this.pitch = buf.readFloat();
		this.roll = buf.readFloat();
		this.throttle = buf.readDouble();
		this.steeringAngle = buf.readFloat();
		this.ticksSinceEntered=buf.readInt();
		this.ticksSinceLeft=buf.readInt();
		

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(vehicleID);
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		buf.writeDouble(motX);
		buf.writeDouble(motY);
		buf.writeDouble(motZ);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		buf.writeFloat(roll);
		buf.writeDouble(throttle);
		buf.writeFloat(steeringAngle);
		buf.writeInt(ticksSinceEntered);
		buf.writeInt(ticksSinceLeft);
	}

}
