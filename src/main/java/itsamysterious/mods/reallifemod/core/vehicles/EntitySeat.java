package itsamysterious.mods.reallifemod.core.vehicles;

import javax.vecmath.Vector3d;

import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySeat extends Entity implements IEntityAdditionalSpawnData {
	public int ID;
	public EntityDriveable parent;
	public float rotationRoll;
	private int vehicleID;
	private boolean foundVehicle;
	public double mass = 10;

	public EntitySeat(World worldIn) {
		super(worldIn);
		setSize(1, 1);
	}

	public EntitySeat(World worldIn, EntityDriveable entityVehicle, int id) {
		this(worldIn);
		parent = entityVehicle;
		vehicleID = entityVehicle.getEntityId();
		ID = id;
		stepHeight = 1;
		initPosition();
	}

	@Override
	protected void entityInit() {
	}

	private void initPosition() {
		if (!worldObj.isRemote) {
			VehicleFile file = parent.getFile();
			Vector3f pos = parent.getPositionVectorFloat();

			Vector3f wantedPos = file.wheelPositions[ID];
			setPosition(pos.x, pos.y + wantedPos.y, pos.z);

			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
		}
	}

	@Override
	public void onUpdate() {
		if (worldObj.isRemote && !foundVehicle) {
			if (!(worldObj.getEntityByID(vehicleID) instanceof EntityVehicle))
				return;
			parent = (EntityDriveable) worldObj.getEntityByID(vehicleID);
			foundVehicle = true;
			parent.seats[ID] = this;
		}

		if (parent == null)
			return;

		if (!addedToChunk)
			worldObj.spawnEntityInWorld(this);
		if (parent.getFile() != null && parent.getFile().wheelPositions != null) {
			if (ID <= parent.getFile().wheelPositions.length) {
				if (ID == 2 || ID == 3) {
					this.rotationYaw = (float) (parent.rotationYaw + parent.steeringAngle);
				} else
					this.rotationYaw = parent.rotationYaw;

				double rotYaw = Math.toRadians(parent.rotationYaw + 90);
				motionX = parent.motionX;
				motionZ = parent.motionZ;
			}
		}

		moveEntity(motionX, motionY, motionZ);

		if (!onGround) {
			this.motionY *= 0.9800000190734863D;

		} else {
			motionX *= 0.9;
			this.motionY *= -0.5D;
			motionZ *= 0.9;

		}

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeInt(vehicleID);
		data.writeInt(ID);
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		vehicleID = data.readInt();
		ID = data.readInt();
		if (worldObj.getEntityByID(vehicleID) instanceof EntityVehicle)
			parent = (EntityDriveable) worldObj.getEntityByID(vehicleID);
		if (parent != null)
			setPosition(posX, posY, posZ);
		initPosition();
	}

	public Vector3d getPositionVectorFloat() {
		return new Vector3d(posX, posY, posZ);
	}

	public double getSpeedXZ() {
		return Math.sqrt(motionX + motionX * motionZ * motionZ);
	}

	public void updatePosition() {
		// TODO Auto-generated method stub

	}

}
