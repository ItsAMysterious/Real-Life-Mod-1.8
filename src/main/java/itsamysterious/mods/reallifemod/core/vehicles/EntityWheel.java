package itsamysterious.mods.reallifemod.core.vehicles;

import javax.vecmath.Vector3d;

import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWheel extends Entity implements IEntityAdditionalSpawnData {
	public EntityDriveable parent;
	public int ID;

	@SideOnly(Side.CLIENT)
	public boolean foundVehicle;

	private int vehicleID;

	public EntityWheel(World worldIn) {
		super(worldIn);
		setSize(1F, 1F);
		stepHeight = 1.0F;
	}

	public EntityWheel(World worldIn, EntityDriveable e, int id) {
		this(worldIn);
		parent = e;
		vehicleID = e.getEntityId();
		ID = id;

		initPosition();
	}

	public void initPosition() {
		Vector3f wheelVector = parent.axes.findLocalVectorGlobally(parent.getFile().wheelPositions[ID]);
		System.out.println("Wheelvector from wheel number " + ID + "= " + wheelVector.toString());
		setPosition(parent.posX + wheelVector.x, parent.posY + wheelVector.y, parent.posZ + wheelVector.z);
		stepHeight = 1F;

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
	}

	@Override
	public void fall(float k, float l) {
		if (parent == null || k <= 0)
			return;
		int i = MathHelper.ceiling_float_int(k - 3F);
		if (i > 0) {
		}
		// parent.attackPart(parent.getFile().wheelPositions[ID],
		// DamageSource.fall, i);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tags) {
		setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tags) {
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

	@Override
	public void func_180426_a(double d, double d1, double d2, float f, float f1, int i, boolean b) {
	}

	public void setPrevPos(double x, double y, double z) {
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;
	}

}
