package itsamysterious.mods.reallifemod.core.vehicles;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.api.IControllable;
import itsamysterious.mods.reallifemod.core.packets.ControlableInputPacket;
import itsamysterious.mods.reallifemod.core.packets.PacketDriveableKeyHeld;
import itsamysterious.mods.reallifemod.core.packets.UpdateControlPackage;
import itsamysterious.mods.reallifemod.core.sounds.CustomSound;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityDriveable extends Entity implements IControllable, IEntityAdditionalSpawnData {

	private String filename;
	public double throttle;

	public double serverPosX, serverPosY, serverPosZ;
	// Durch die zwei unabhängigen geschwindigkeiten können die rädery
	// durchdrehen!
	public double motorspeed;
	// will be the motorspeed reduced by the forces.
	public double actualspeed;
	public float steeringAngle;
	public float wheelsAngle;

	public boolean isLightOn;

	public EntitySeat[] seats;
	public EntityWheel[] wheels;
	public float rotationRoll;
	public float prevRotationRoll;
	public double fuellevel;

	public CustomRotationAxes axes;
	public CustomRotationAxes prevAxes;

	public CustomSound startsound;
	public CustomSound stopsound;
	public CustomSound runsound;
	public CustomSound throttlesound;

	private boolean syncFromServer;
	protected int serverPositionTransitionTicker;
	protected float serverYaw;
	protected float serverPitch;
	private Vector3f angularVelocity;
	protected float serverRoll;
	private boolean leftMouseHeld;
	private boolean rightMouseHeld;
	
	//Depending on the health, the model renders different?
	public float health;


	/**
	 * All the stuff for playing the sounds right
	 */
	public int ticksSinceLeft = 0;
	public int ticksSinceEntered = 0;
	public int loopingsoundposition;

	public EntityDriveable(World worldIn) {
		super(worldIn);
		axes = new CustomRotationAxes();
		preventEntitySpawning = true;
		setSize(1F, 1F);
		ignoreFrustumCheck = true;
		renderDistanceWeight = 200D;
	}

	public EntityDriveable(World world, VehicleFile f, double x, double y, double z, EntityPlayer placer) {
		this(world);
		filename = f.vehicleName;
		setPosition(x, y, z);
		axes.setAngles(rotationYaw, rotationPitch, rotationRoll);
	}

	protected void initFile(VehicleFile f, boolean isClient) {

		wheels = new EntityWheel[f.wheelPositions.length];
		for (int i = 0; i < wheels.length; i++) {
			if (!isClient) {
				wheels[i] = new EntityWheel(worldObj, this, i);
				worldObj.spawnEntityInWorld(wheels[i]);
			}
		}

		stepHeight = f.wheelStepHeight;
		this.startsound = new CustomSound(f.startsound.getSoundLocation(), f.startsound.length);
		this.stopsound = new CustomSound(f.stopsound.getSoundLocation(), f.stopsound.length);
		this.throttlesound = new CustomSound(f.throttlesound.getSoundLocation(), f.throttlesound.length);
		this.runsound = new CustomSound(f.enginesound.getSoundLocation(), f.enginesound.length);

	}

	public void setPosition(double x, double y, double z) {
		super.setPosition(x, y, z);
		if (isClient()) {
			// RealLifeMod.network.sendToServer(new UpdateControlPackage());
		}
	}

	@Override
	public void func_180426_a(double d, double d1, double d2, float f, float f1, int i, boolean b) {
		if (ticksExisted > 1)
			return;
		if (riddenByEntity instanceof EntityPlayer && RealLifeMod.proxy.isThePlayer((EntityPlayer) riddenByEntity)) {
		} else {
			if (syncFromServer) {
				serverPositionTransitionTicker = i + 5;
			} else {
				double var10 = d - posX;
				double var12 = d1 - posY;
				double var14 = d2 - posZ;
				double var16 = var10 * var10 + var12 * var12 + var14 * var14;

				if (var16 <= 1.0D) {
					return;
				}

				serverPositionTransitionTicker = 3;
			}
			serverPosX = d;
			serverPosY = d1;
			serverPosZ = d2;
			serverYaw = f;
			serverPitch = f1;
		}
	}

	public void setPositionRotationAndMotion(double x, double y, double z, float yaw, float pitch, float roll,
			double motX, double motY, double motZ, float velYaw, float velPitch, float velRoll, float throt,
			float steeringYaw) {
		if (worldObj.isRemote) {
			serverPosX = x;
			serverPosY = y;
			serverPosZ = z;
			serverYaw = yaw;
			serverPitch = pitch;
			serverRoll = roll;
			serverPositionTransitionTicker = 5;
		} else {
			setPosition(x, y, z);
			prevRotationYaw = yaw;
			prevRotationPitch = pitch;
			prevRotationRoll = roll;
			setRotation(yaw, pitch, roll);
		}
		// Set the motions regardless of side.
		motionX = motX;
		motionY = motY;
		motionZ = motZ;
		angularVelocity = new Vector3f(velYaw, velPitch, velRoll);
		throttle = throt;
	}

	protected void setRotation(float yaw, float pitch, float roll) {
		super.setRotation(yaw, pitch);
		axes.setAngles(yaw, pitch, roll);
	}

	// Called on Server
	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		filename = tagCompund.getString("VehicleName");
		initFile(Vehicles.get(filename), false);

		prevRotationYaw = tagCompund.getFloat("RotationYaw");
		prevRotationPitch = tagCompund.getFloat("RotationPitch");
		prevRotationRoll = tagCompund.getFloat("RotationRoll");
		axes = new CustomRotationAxes(prevRotationYaw, prevRotationPitch, prevRotationRoll);

		fuellevel = tagCompund.getDouble("Fuel");

	}

	// Called on Server
	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompund) {
		tagCompund.setString("VehicleName", filename);
		tagCompund.setDouble("Fuel", fuellevel);
		tagCompund.setFloat("RotationYaw", rotationYaw);
		tagCompund.setFloat("RotationPitch", rotationPitch);
		tagCompund.setFloat("RotationRoll", rotationRoll);

	}

	// Called on client
	@Override
	public void writeSpawnData(ByteBuf data) {
		ByteBufUtils.writeUTF8String(data, this.filename);
		data.writeFloat(axes.getYaw());
		data.writeFloat(axes.getPitch());
		data.writeFloat(axes.getRoll());
	}

	// Called on client
	@Override
	public void readSpawnData(ByteBuf data) {
		try {
			filename = ByteBufUtils.readUTF8String(data);
			initFile(getFile(), true);

			axes.setAngles(data.readFloat(), data.readFloat(), data.readFloat());
			prevRotationYaw = axes.getYaw();
			prevRotationPitch = axes.getPitch();
			prevRotationRoll = axes.getRoll();
			// Warum nicht hier initFIle? Dann ist die rotation korrekt!

		} catch (Exception e) {
			RealLifeMod.log("Did not work!");
			super.setDead();
			e.printStackTrace();
		}
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return null;// entity.boundingBox;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
		// Do nothing. Like a boss.
		// : perhaps send the player flying??
		// Sounds good. ^
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		VehicleFile file = getFile();

		if (file == null) {
			return;
		}

		if (!worldObj.isRemote) {

			for (int i = 0; i < file.wheelPositions.length; i++) {
				if (wheels[i] == null || !wheels[i].addedToChunk) {
					wheels[i] = new EntityWheel(worldObj, this, i);
					worldObj.spawnEntityInWorld(wheels[i]);
				}
			}

		}

		prevRotationYaw = axes.getYaw();
		prevRotationPitch = axes.getPitch();
		prevRotationRoll = axes.getRoll();
		prevAxes = axes.clone();

		if (riddenByEntity != null && riddenByEntity.isDead) {
			riddenByEntity = null;
		}

		if (riddenByEntity != null && isDead) {
			riddenByEntity.mountEntity(null);
		}
		if (riddenByEntity != null)
			riddenByEntity.fallDistance = 0F;

		simulateValues();

		boolean playerIsDrivingThis = worldObj.isRemote && riddenByEntity != null&& riddenByEntity instanceof EntityPlayer && riddenByEntity == Minecraft.getMinecraft().thePlayer;// seats[0]

		serverPosX = posX;
		serverPosY = posY;
		serverPosZ = posZ;

		if (playerIsDrivingThis) {
			if (!isClient()) {
				if (ticksSinceEntered > 0 && ticksSinceEntered < 5) {
					ticksSinceEntered++;
				}
				if (ticksSinceEntered == 5)
					ticksSinceEntered = 0;
			}

		} else {
			if (isClient() && ticksSinceLeft > 0) {
				ticksSinceLeft--;
			}
		}

		if (loopingsoundposition > 0) {
			loopingsoundposition--;
		}

		if (!worldObj.isRemote) {
			playSounds();
		}

		if (!worldObj.isRemote && ticksExisted % 5 == 0) {
			RealLifeMod.network.sendToAllAround(new UpdateControlPackage(this),
					new TargetPoint(dimension, posX, posY, posZ, 10));
		}

	}

	public void simulateValues() {
	}

	private void playSounds() {
		VehicleFile file = getFile();
		// Only playing enginesound if startsound and stopsound are not playing
		if (ticksSinceEntered == 0 && ticksSinceLeft == 0 && loopingsoundposition == 0 && riddenByEntity != null) {
			loopingsoundposition = runsound.length;
			worldObj.playSoundAtEntity(this, "reallifemod:" + runsound.getSoundLocation().getResourcePath(), 1, 1f);
		}

		if (throttle > 0) {
			if (loopingsoundposition == 0) {
				loopingsoundposition = throttlesound.length;
				worldObj.playSoundAtEntity(this, "reallifemod:" + throttlesound.getSoundLocation().getResourcePath(), 1,
						1);

				// PacketPlaySound.sendSoundPacket((float) posX, (float) posY,
				// (float) posZ, 10f, worldObj.provider.getDimensionId(),
				// throttlesound.getSoundLocation().getResourcePath(), 1f);
			}
		}

		if (ticksSinceEntered == 1) {
			if (startsound != null && loopingsoundposition == 0) {
			}
		}

	}

	public void setAngles(float f, float g, float h) {
		rotationYaw = f;
		rotationPitch = g;
		rotationRoll = h;
	}

	@Override
	public boolean interactFirst(EntityPlayer playerIn) {
		if (worldObj == null)
			return false;
		if (!worldObj.isRemote) {
			worldObj.playSoundAtEntity(this, "reallifemod:" + startsound.getSoundLocation().getResourcePath(), 1,
					(float) this.actualspeed * 100);

		} else {
			// Minecraft.getMinecraft().getSoundHandler().playSound(CustomSound.getSoundWithPosition(getFile().startsound.getSoundLocation().getResourcePath(),
			// (float)posX, (float)posY, (float)posZ, 1, 1));
		}
		playerIn.rotationYaw = this.rotationYaw;
		playerIn.rotationYawHead = this.rotationYaw;

		playerIn.mountEntity(this);

		/** Setting ticksSinceEntered to 1 so onUpdate starts counting */
		ticksSinceEntered = startsound.length / 2 - 20;

		return true;
	}

	@Override
	public void updateRiderPosition() {
		EntityPlayer p = (EntityPlayer) riddenByEntity;
		Vector3f riderPosition = axes.findLocalVectorGlobally(getFile().seatPositions[0]);
		p.setAir(1);
		p.setPosition(posX + riderPosition.x, posY + riderPosition.y, posZ + riderPosition.z);
	}

	protected boolean isClient() {
		return !this.worldObj.isRemote;
	}

	public VehicleFile getFile() {
		return Vehicles.get(filename);
	}

	protected Block findBlockUnderVehicle() {
		if (worldObj != null)
			return worldObj.getBlockState(getPosition().subtract(new Vec3i(0, 1, 0))).getBlock();
		else
			return Blocks.air;
	}

	public Vector3f getPositionVectorFloat() {
		return new Vector3f((float) posX, (float) posY, (float) posZ);
	}

	public void rotateYaw(float rotateBy) {
		if (Math.abs(rotateBy) < 0.01F)
			return;
		rotationYaw = rotateBy;
		updatePrevAngles();
	}

	public void updatePrevAngles() {
		// Correct angles that crossed the +/- 180 line, so that rendering
		// doesnt make them swing 360 degrees in one tick.
		double dYaw = rotationYaw - prevRotationYaw;
		if (dYaw > 180)
			prevRotationYaw += 360F;
		if (dYaw < -180)
			prevRotationYaw -= 360F;

		double dPitch = rotationPitch - prevRotationPitch;
		if (dPitch > 180)
			prevRotationPitch += 360F;
		if (dPitch < -180)
			prevRotationPitch -= 360F;

		double dRoll = rotationRoll - prevRotationRoll;
		if (dRoll > 180)
			prevRotationRoll += 360F;
		if (dRoll < -180)
			prevRotationRoll -= 360F;
		axes.setAngles(rotationYaw, rotationPitch, rotationRoll);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void setDead() {
		super.setDead();
	}

	public Vector3f rotate(Vector3f inVec) {
		return axes.findLocalVectorGlobally(inVec);
	}

	/**
	 * Takes a vector (such as the origin of a seat / gun) and translates it
	 * from local coordinates to global coordinates
	 */
	public Vector3f rotate(Vec3 inVec) {
		return rotate(inVec.xCoord, inVec.yCoord, inVec.zCoord);
	}

	/**
	 * Takes a vector (such as the origin of a seat / gun) and translates it
	 * from local coordinates to global coordinates
	 */
	public Vector3f rotate(double x, double y, double z) {
		return rotate(new Vector3f((float) x, (float) y, (float) z));
	}

	@Override
	public void onMouseMoved(int deltaX, int deltaY) {
	}

	@Override
	public boolean pressKey(int key, EntityPlayer player) {
		VehicleFile file = getFile();
		if (worldObj.isRemote && (key == Keyboard.KEY_W || key == 5)) {
			// SendKeyPacketToServer
			/**
			 * If player dismounts, set tickssinceleft to 1 for the stopping
			 * sound
			 */
			if (key == 6) {
				loopingsoundposition = 5;
				if (riddenByEntity == null && loopingsoundposition == 5) {
					if (stopsound != null && loopingsoundposition == 0) {
						worldObj.playSoundAtEntity(this,
								"reallifemod:" + startsound.getSoundLocation().getResourcePath(), 1, 1);
					}
				}
			}

			RealLifeMod.network.sendToServer(new ControlableInputPacket());
		}
		return false;
	}

	@Override
	public void updateKeyHeldState(int key, boolean held) {
		if (worldObj.isRemote) {
			RealLifeMod.network.sendToServer(new PacketDriveableKeyHeld(key, held));
		}
		switch (key) {
		case 9:
			leftMouseHeld = held;
			break;
		case 8:
			rightMouseHeld = held;
			break;
		}
	}

	@Override
	public Entity getControllingEntity() {
		return riddenByEntity;
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public float getPlayerRoll() {
		return axes.getRoll();
	}

	@Override
	public float getPrevPlayerRoll() {
		return prevAxes.getRoll();
	}

}
