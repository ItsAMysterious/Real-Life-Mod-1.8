package itsamysterious.mods.reallifemod.core.vehicles;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.core.utils.MathUtils;
import itsamysterious.mods.reallifemod.core.utils.Physics;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityDriveable extends Entity implements IEntityAdditionalSpawnData {

	private String filename;
	private double throttle;
	// Durch die zwei unabhängigen geschwindigkeiten können die räder
	// durchdrehen!
	private double motorspeed;
	// will be the motorspeed reduced by the forces.
	private double actualspeed;
	float steeringAngle;
	float wheelsAngle;

	private boolean isLightOn;

	public EntitySeat[] seats;
	public EntityWheel[] wheels;
	public float rotationRoll;
	public float prevRotationRoll;
	public double fuellsevel;
	public double fuellevel;
	public CustomRotationAxes axes;

	public EntityDriveable(World worldIn) {
		super(worldIn);
		axes = new CustomRotationAxes(0,0,0);
		preventEntitySpawning = true;
		setSize(1F, 1F);
		ignoreFrustumCheck = true;
		renderDistanceWeight = 200D;
		stepHeight = 1.0F;
	}

	public EntityDriveable(World world, VehicleFile f, double x, double y, double z, EntityPlayer placer) {
		this(world);
		filename = f.vehicleName;
		System.out.println("Now running initFile!");
		initFile(f, false);
		setPosition(x, y, z);
		if (placer != null)
			rotateYaw(placer.rotationYaw + 90F);
		stepHeight = 1.0F;

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
			if(axes!=null){
				axes = new CustomRotationAxes(0,0,0);
				axes.setAngles(data.readFloat(), data.readFloat(), data.readFloat());
				prevRotationYaw = axes.getYaw();
				prevRotationPitch = axes.getPitch();
				prevRotationRoll = axes.getRoll();
			}else
				System.out.println("Axes are null");

			initFile(getFile(), true);

		} catch (Exception e) {
			System.out.println("Did not work!");
			super.setDead();
			e.printStackTrace();
		}
	}

	protected void initFile(VehicleFile f, boolean isClient) {

		/*
		 * seats = new EntitySeat[f.numDrivers]; for (int i = 0; i <
		 * seats.length; i++) {
		 * 
		 * if (!isClient) { seats[i] = new EntitySeat(worldObj, this, i);
		 * worldObj.spawnEntityInWorld(seats[i]); } }
		 */

		// Initialisation of wheels

		wheels = new EntityWheel[4];
		for (int i = 0; i < wheels.length; i++) {

			if (!isClient) {
				wheels[i] = new EntityWheel(worldObj, this, i);
				worldObj.spawnEntityInWorld(wheels[i]);
			}
		}

		stepHeight = 1.0f;
	}

	// Called on Server
	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		filename = tagCompund.getString("VehicleName");
		prevRotationYaw = tagCompund.getFloat("RotationYaw");
		prevRotationPitch = tagCompund.getFloat("RotationPitch");
		prevRotationRoll = tagCompund.getFloat("RotationRoll");
		System.out.println("Now setting axes!");
		axes = new CustomRotationAxes(prevRotationYaw, prevRotationPitch, prevRotationRoll);
		System.out.println("Succesfully set axes");

		fuellevel = tagCompund.getDouble("Fuel");
		initFile(Vehicles.get(filename), false);

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
		// TODO: perhaps send the player flying??
		// Sounds good. ^
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		System.out.println("Checking for file");
		VehicleFile file = getFile();
		if (file == null) {
			System.out.println("File is null, not ticking Entity");
			return;
		}
		System.out.println("File is found");

		System.out.println("Trying seat stugg");

		if (!worldObj.isRemote) {
			/*
			 * for (int i = 0; i < getFile().numDrivers + 1; i++) { if (seats[i]
			 * == null || !seats[i].addedToChunk) { seats[i] = new
			 * EntitySeat(worldObj, this, i);
			 * worldObj.spawnEntityInWorld(seats[i]); } }
			 */

			for (int i = 0; i < file.wheelPositions.length; i++) {
				if (wheels[i] == null || !wheels[i].addedToChunk) {
					wheels[i] = new EntityWheel(worldObj, this, i);
					worldObj.spawnEntityInWorld(wheels[i]);
				}
			}

		}
		System.out.println("prevRotation");
		prevRotationYaw = rotationYaw;
		prevRotationPitch = rotationPitch;
		prevRotationRoll = rotationRoll;

		for (EntityWheel wheel : wheels) {
			if (wheel == null)
				continue;

			// Hacky way of forcing the car to step up blocks
			onGround = true;
			wheel.onGround = true;
			wheel.rotationYaw = axes.getYaw();

			wheel.motionX *= 0.9F;
			wheel.motionY *= 0.9F;
			wheel.motionZ *= 0.9F;

			wheel.motionY -= 0.98F / 20F;
		}

		if (riddenByEntity != null && riddenByEntity.isDead) {
			riddenByEntity = null;
		}

		if (riddenByEntity != null && isDead) {
			riddenByEntity.mountEntity(null);
		}
		if (riddenByEntity != null)
			riddenByEntity.fallDistance = 0F;

		System.out.println("After prevRotation");

		// Festlegen des actualspeed;
		applyForces();
		// System.out.println("File is null cant apply forces!");
		System.out.println("After Forces");

		wheelsAngle += (Math.pow(throttle, 0.4)) * 0.2;
		// acceleration
		motorspeed += throttle;
		actualspeed *= 0.9;
		// Lenkeinschlag auf 0 laufen lassen und beschränken.
		steeringAngle *= 0.9F;
		if (steeringAngle < -35) {
			steeringAngle = -35;
		}

		if (steeringAngle > 35) {
			steeringAngle = 35;
		}

		checkInput();
		System.out.println("After InputCheck");
		
		if (wheels.length > 0) {
			// Calculate Vehicleangles from wheelpositions
			if (wheels[0] != null && wheels[1] != null && wheels[2] != null && wheels[3] != null) {
				Vector3f frontAxleCentre = MathUtils.vectorFromDouble((wheels[2].posX + wheels[3].posX) / 2F,
						(wheels[2].posY + wheels[3].posY) / 2F, (wheels[2].posZ + wheels[3].posZ) / 2F);
				Vector3f backAxleCentre = MathUtils.vectorFromDouble((wheels[0].posX + wheels[1].posX) / 2F,
						(wheels[0].posY + wheels[1].posY) / 2F, (wheels[0].posZ + wheels[1].posZ) / 2F);

				float dx = frontAxleCentre.x - backAxleCentre.x;
				float dy = frontAxleCentre.y - backAxleCentre.y;
				float dz = frontAxleCentre.z - backAxleCentre.z;

				float dxz = (float) Math.sqrt(dx * dx + dz * dz);

				float yaw = (float) Math.atan2(dz, dx);
				float pitch = -(float) Math.atan2(dy, dxz);
				float roll = 0;

				/*
				 * if (file.vehicleType == VehicleTypes.Tank) { yaw = (float)
				 * Math.atan2(wheels[3].posZ - wheels[2].posZ, wheels[3].posX -
				 * wheels[2].posX) + (float) Math.PI / 2F; }
				 */

				axes.setAngles(yaw * 180F / 3.14159F, pitch * 180F / 3.14159F, roll * 180F / 3.14159F);
			}
		} else
			System.out.println("Missing wheels!");

	}

	public void setAngles(float f, float g, float h) {
		rotationYaw = f;
		rotationPitch = g;
		rotationRoll = h;
	}

	public void checkInput() {
		if (isClient()) {

			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				System.out.println("Pressed 'W'!");
				throttle += 0.025f;
			} else {
				throttle = 0;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				if (actualspeed > 0.0001)
					actualspeed *= 0.8;
				else
					actualspeed = 0;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				if (!Keyboard.isKeyDown(Keyboard.KEY_D))
					if (steeringAngle < 35)
						steeringAngle += 2.5;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				if (steeringAngle > -35)
					steeringAngle -= 2.5;
			}

			// if (Keyboard.isKeyDown(i)) {
			// RealLifeMod.network.sendToServer(new
			// OpenContainerPackage(VehicleGui.ID, this));
			// }

			// if (Keyboard.isKeyDown(Keybindings.Horn.getKeyCode())) {
			// RealLifeMod.network.sendToServer(new
			// OpenContainerPackage(VehicleGui.ID, this));
			// }
		}

	}

	private boolean isClient() {
		return !this.worldObj.isRemote;
	}

	public VehicleFile getFile() {
		return Vehicles.get(filename);
	}

	public void applyForces() {
		actualspeed = motorspeed;
		actualspeed -= ((airDrag() + rollDrag() + accelerationDrag() + pitchDrag()) / 1000 / 60 / 20);

	}

	private double airDrag() {
		VehicleFile file = getFile();

		double cw = getCW();// Stroemungswiderstandskoeffizient
		double relativeSpeed = (float) Math.sqrt((motionX * motionX) + (motionZ * motionZ));
		double A = file.width * file.height;// Flaecheninhalt
		double airdrag = (1.2f / 2) * cw * A * relativeSpeed;
		return airdrag;
	}

	private double rollDrag() {
		float rdc = getRollDragCoefficient();
		float rollForce = (float) (getFullMass() * Physics.g * rdc);
		return rollForce;
	}

	private double pitchDrag() {
		return (getFullMass() * Physics.g * Math.sin(rotationPitch));
	}

	private double accelerationDrag() {
		double momentOfInertia = (4 * (wheels[0].mass)) * motorspeed;
		return (momentOfInertia * getFullMass()) * throttle;
	}

	private double getFullMass() {
		int playerCount = 0;
		/*
		 * for (EntitySeat seat : seats) { if (seat.riddenByEntity != null &&
		 * seat.riddenByEntity instanceof EntityPlayer) { playerCount++; } }
		 */
		return getFile().mass + (playerCount * 75);
	}

	private double getCW() {
		return 0.2;
	}

	private float getRollDragCoefficient() {
		return Physics.getRollDragCoefficientForBlock(findBlockUnderVehicle());
	}

	private Block findBlockUnderVehicle() {
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
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void setDead() {
		super.setDead();
		for (EntityWheel wheel : wheels)
			if (wheel != null)
				wheel.setDead();
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

}
