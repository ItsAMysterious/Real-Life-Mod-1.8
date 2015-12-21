package itsamysterious.mods.reallifemod.core.vehicles;

import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.utils.MathUtils;
import itsamysterious.mods.reallifemod.core.utils.Physics;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;

public class EntityCar extends EntityDriveable {
	private int ticksSinceUsed = 0;
	public float health;

	public EntityCar(World worldIn) {
		super(worldIn);
		stepHeight = 1.0F;
	}

	public EntityCar(World worldIn, VehicleFile f, double x, double y, double z, EntityPlayer placer) {
		super(worldIn, f, x, y, z, placer);
		stepHeight = 1.0F;
		setPosition(x, y, z);
		rotateYaw(placer.rotationYaw - 90F);
		initFile(f, false);
	}

	@Override
	protected void initFile(VehicleFile type, boolean clientSide) {
		super.initFile(type, clientSide);
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		super.readSpawnData(data);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		// Getting VehicleFIle
		VehicleFile file = this.getFile();
		if (file == null) {
			RealLifeMod.log("Vehicle type null. Not ticking vehicle");
			return;
		}

		// Checking wether the current drivr actually is the player
		boolean playerIsDrivingThis = worldObj.isRemote && riddenByEntity != null
				&& riddenByEntity instanceof EntityPlayer
				&& RealLifeMod.proxy.isThePlayer((EntityPlayer) riddenByEntity);

		// Handling health updates
		ticksSinceUsed++;
		if (health <= 0) {
			destroy();
		}

		// Managing wheels
		wheelsAngle += motorspeed / 4;

		// Running steeringangle back to zero
		if (steeringAngle > 0)
			steeringAngle--;
		else if (steeringAngle < 0)
			steeringAngle++;

		// Limit steeringAngle
		if (steeringAngle > 35)
			steeringAngle = 35;
		if (steeringAngle < -35)
			steeringAngle = 35;

		// Handling Driving Dynamics
		for (EntityWheel wheel : wheels) {
			if (wheel != null && worldObj != null) {
				wheel.rotationYaw = this.rotationYaw + 90;
				if (wheel.ID == 1 || wheel.ID == 0) {
					wheel.rotationYaw -= 180;
				}

				if (wheel.ID == 0 || wheel.ID == 3) {
					wheel.rotationYaw += steeringAngle;
				}
				wheel.setPrevPos(wheel.posX, wheel.posY, wheel.posZ);
				wheel.rotationPitch += actualspeed / Math.PI / 2;

			}
		}
		applyForces();

		simulateValues();
		// Festlegen des actualspeed;
	}

	private void destroy() {
		// TODO Auto-generated method stub

	}

	public void applyForces() {
		actualspeed = motorspeed;
		motorspeed -= ((airDrag() + rollDrag() + pitchDrag()))/1000/60;

	}

	@Override
	public void simulateValues() {
		VehicleFile file = getFile();
		if (motorspeed < file.maxSpeed && motorspeed > -file.maxReverseSpeed) {
			motorspeed += throttle;
		}

		boolean isAccelerating = false;

		if (worldObj.isRemote) {
			isAccelerating = Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown();
		}
		

		if (!isAccelerating) {
			System.out.println(Physics.getRollDragCoefficientForBlock(findBlockUnderVehicle()));
			throttle *= 0.8333333;
			throttle = Math.round(throttle);
		} else {
			actualspeed -= accelerationDrag();

		}

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
		double momentOfInertia = 40 * motorspeed;// 40 = 4*10 (Masse der Räder)
		return (momentOfInertia * getFullMass()) * throttle;
	}

	@Override
	public void applyEntityCollision(Entity entityIn) {
		if (entityIn != riddenByEntity) {
			super.applyEntityCollision(entityIn);
		}
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

	@Override
	public boolean pressKey(int key, EntityPlayer player) {
		VehicleFile file = getFile();
		switch (key) {

		case 0: {
			throttle += file.acceleration / 20;
			if (throttle > file.acceleration) {
				throttle = file.acceleration;
			}
			return true;
		}
		case 1: {
			throttle -= file.acceleration / 20;
			if (throttle < -file.acceleration / 2) {
				throttle = -file.acceleration / 2;
			}
			return true;
		}
		case 2: {
			steeringAngle -= 2.5;
			return true;
		}

		case 3: {
			steeringAngle += 2.5;
			return true;
		}

		case 4:
			// Up: Braking
			throttle *= 0.9;

			if (onGround) {
				motorspeed *= 0.9f;
				motionX *= 0.8;
				motionZ *= 0.8;
			}
			return true;
		case 5: {
			// Down: Nothing todo here
			return true;
		}

		case 6: {
			// Exit: Get out of the driveable
			riddenByEntity.mountEntity(null);
			// TODO:Making seats work
			// seats[0].riddenByEntity.mountEntity(null);
			return true;

		}
		}

		return false;
	}
}
