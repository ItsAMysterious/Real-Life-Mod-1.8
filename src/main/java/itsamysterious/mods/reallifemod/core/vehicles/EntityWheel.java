package itsamysterious.mods.reallifemod.core.vehicles;

import javax.vecmath.Vector3d;

import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWheel extends Entity implements IEntityAdditionalSpawnData {
	public int ID;
	public EntityDriveable parent;
	public float rotationRoll;
	private int vehicleID;
	@SideOnly(Side.CLIENT)
	private boolean foundVehicle;
	public double mass = 10;

	public EntityWheel(World worldIn) {
		super(worldIn);
		setSize(1, 1);
	}

	public EntityWheel(World worldIn, EntityDriveable entityVehicle, int id) {
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
		System.out.println("Initialiizing Position:");

		VehicleFile file = parent.getFile();
		if (file == null) {
			return;
		}
		Vector3f pos = parent.getPositionVectorFloat();
		if (file.wheelPositions != null && ID <= file.wheelPositions.length) {
			Vector3f wantedPos = new Vector3f(0, 0, 0);

			if (parent.axes != null) {
				wantedPos = parent.axes.findLocalVectorGlobally(file.wheelPositions[ID]);
			}else
			{
				System.out.println("Axes are null!");
			}
			setPosition(pos.x + wantedPos.x, pos.y + wantedPos.y, pos.z + wantedPos.z);

		} else {
			System.out.println("Too large!");

		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
	}

	@Override
	public void onUpdate() {
		if (worldObj.isRemote && !foundVehicle) {
			if (!(worldObj.getEntityByID(vehicleID) instanceof EntityDriveable))
				return;
			parent = (EntityDriveable) worldObj.getEntityByID(vehicleID);
			foundVehicle = true;
			parent.wheels[ID] = this;
		}

		if (parent == null)
			return;

		if (!addedToChunk)
			worldObj.spawnEntityInWorld(this);
		if (parent.getFile() != null && parent.getFile().wheelPositions != null) {

		}

		rotationPitch = parent.wheelsAngle;

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
		if (worldObj.getEntityByID(vehicleID) instanceof EntityDriveable)
			parent = (EntityDriveable) worldObj.getEntityByID(vehicleID);
		if (parent != null)
			setPosition(posX, posY, posZ);
	}

	public Vector3d getPositionVectorFloat() {
		return new Vector3d(posX, posY, posZ);
	}

	public double getSpeedXZ() {
		return Math.sqrt(motionX + motionX * motionZ * motionZ);
	}

}
