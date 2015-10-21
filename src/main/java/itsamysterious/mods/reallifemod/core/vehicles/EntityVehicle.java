package itsamysterious.mods.reallifemod.core.vehicles;

import javax.vecmath.Vector3d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.handlers.Keybindings;
import itsamysterious.mods.reallifemod.core.packets.UpdateVehiclePacket;
import itsamysterious.mods.reallifemod.core.sounds.SoundPlayer;
import itsamysterious.mods.reallifemod.core.utils.Physics;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVehicle extends Entity implements IEntityAdditionalSpawnData {
	private double serverPosX;
	private double serverPosY;
	private double serverPosZ;
	private double serverYaw;
	private double serverPitch;

	public String filename;
	EntitySeat[] seats;
	EntityWheel[] wheels;

	public double backWheelRotation;
	public double wheelRotL;
	public double steeringAngle;
	private boolean isEmpty;
	private boolean canDoStuff;

	public double speed;
	public double fuellevel;
	private SoundPlayer player;
	// Constants

	private float prevRotationRoll;

	private double throttle;
	private int motorspeed;
	private double wheelsAngle;

	public EntityVehicle(World world) {
		super(world);
		setSize(1F, 1F);
		preventEntitySpawning = true;
		ignoreFrustumCheck = true;
		stepHeight = 1;
		isEmpty = true;

	}

	public EntityVehicle(World world, VehicleFile file) {
		this(world);
		initVehicle(file, false);
	}

	public EntityVehicle(World world, VehicleFile file, double x, double y, double z) {
		this(world, file);
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.throttle = 0;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		ByteBufUtils.writeUTF8String(buffer, this.filename);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		try {
			filename = ByteBufUtils.readUTF8String(additionalData);
			initVehicle(getFile(), true);
		} catch (Exception e) {
			System.out.println("Did not work!");
			super.setDead();
			e.printStackTrace();
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		filename = tagCompund.getString("VehicleName");
		initVehicle(Vehicles.get(filename), false);
		fuellevel = tagCompund.getDouble("Fuel");

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompund) {
		tagCompund.setString("VehicleName", filename);
		tagCompund.setDouble("Fuel", fuellevel);
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(17, new Integer(0));
		dataWatcher.addObject(18, new Integer(1));
		dataWatcher.addObject(19, new Float(0.0F));
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return null;
	}

	public AxisAlignedBB getBoundingBox() {
		return getEntityBoundingBox();
	}

	public boolean canBePushed() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		setForwardDirection(-getForwardDirection());
		setTimeSinceHit(10);
		setDamageTaken(getDamageTaken() * 11.0F);
	}

	private void createSeats(World w) {
		for (int i = 0; i < seats.length; i++) {
			seats[i] = new EntitySeat(w);
			w.spawnEntityInWorld(seats[i]);
		}
		canDoStuff = true;
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		motionX = x;
		motionY = y;
		motionZ = z;
	}

	public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		if (isClient()) {
			serverPosX = x;
			serverPosY = y;
			serverPosZ = z;
			serverYaw = yaw;
			serverPitch = pitch;

		} else {
			setPosition(x, y, z);
			prevRotationYaw = yaw;
			prevRotationPitch = pitch;
			setRotation(yaw, pitch);

		}

	}

	private void setRotation(float yaw, float pitch, float roll) {
		super.setRotation(yaw, pitch);
	}

	public void onUpdate() {
		super.onUpdate();

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		VehicleFile file = getFile();
		if (file == null) {
			return;
		}
		if (!isClient()) {
			for (int i = 0; i < file.wheelPositions.length; i++) {
				if (wheels[i] == null || !wheels[i].addedToChunk) {
					//wheels[i] = new EntityWheel(worldObj, this, i);
					worldObj.spawnEntityInWorld(wheels[i]);
				}
			}

			/*
			 * for (int i = 0; i < file.ridersPositions.size() - 1; i++) { if
			 * (seats[i] == null || !seats[i].addedToChunk) { seats[i] = new
			 * EntitySeat(worldObj, this, i);
			 * worldObj.spawnEntityInWorld(seats[i]); } }
			 */

		}

		if (wheels.length > 0) {
			if (wheels[0] != null) {

				if (wheels[0].posX - posX != 0 && wheels[0].posY - posY != 0)
					rotationPitch = (float) Math.tan(wheels[0].posY - posY / wheels[0].posX - posX);
				double hypothenuse = posZ - wheels[0].posZ;
				if (hypothenuse != 0) {
					double distance = Math.sin(hypothenuse / (Math.sin(rotationPitch)) * hypothenuse);
					this.posY = posY - distance;
				}

				prevRotationYaw = rotationYaw;
				prevRotationPitch = rotationPitch;
			}
		}

		if (posY < -64.0D) {
			posY = 0;
		}

		if (isClient()) {
			if (riddenByEntity != null) {
				simulateMovement();
			}

		} else {
			playSounds();

		}

		if (!onGround) {
			motionY *= 0.9800000190734863D;
		} else {
			motionY *= -0.5;
		}
		
		if (isClient()) {
			
			motorspeed += throttle;
			double k = 0;
			double R = 0;
			if (steeringAngle != 0) {
				R = file.dimensions.x / this.steeringAngle;
				k = (double) 1.0D / R;
			}
			double r = ((this.motorspeed * 1000) / 60 / 60 / 20) * k;
			rotationYaw= (float) (this.rotationYaw - r);
			for(EntityWheel wheel: wheels){
				if(wheel.ID==1||wheel.ID==2){
					wheel.rotationYaw=(float) (rotationYaw+steeringAngle);
					wheel.motionX=-(this.motorspeed * 1000 / 60 / 60 / 20) * Math.sin(Math.toRadians(wheel.rotationYaw));
					wheel.motionZ = (this.motorspeed * 1000 / 60 / 60 / 20) * Math.cos(Math.toRadians(wheel.rotationYaw));
					wheel.moveEntity(wheel.motionX, wheel.motionY, wheel.motionZ);
				}else
				{
					wheel.rotationYaw=rotationYaw;
					wheel.motionX=-(this.motorspeed * 1000 / 60 / 60 / 20) * Math.sin(Math.toRadians(wheel.rotationYaw));
					wheel.motionZ = (this.motorspeed * 1000 / 60 / 60 / 20) * Math.cos(Math.toRadians(wheel.rotationYaw));
				}
				
			}
			
			

			RealLifeMod.network.sendToServer(new UpdateVehiclePacket(getEntityId(), posX+motionX, posY+motionY, posZ+motionZ, 0, filename));
		}

		wheelsAngle += (Math.pow(throttle, 0.4))*0.2;	
		steeringAngle *= 0.9F;
		
		if(steeringAngle<-35){
			steeringAngle=-35;
		}
		
		if(steeringAngle>35){
			steeringAngle=35;
		}
		

		/*
		 * if (this.canDoStuff) { for (int i = 0; i < seats.length; i++) {
		 * Vector3f f = file.ridersPositions.get(i);
		 * this.seats[i].setPosition(this.posX + f.x, this.posY + f.y + 0.5,
		 * this.posZ + f.z); } }
		 */

		if (riddenByEntity != null && riddenByEntity.isDead)

		{
			riddenByEntity = null;
		}

		if (this.getTimeSinceHit() > 0)

		{
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F)

		{
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}
		if (

		getFile() != null) {
			 applyForces();
		}

	}

	private void playSounds() {
		VehicleFile file = getFile();
		if (ticksExisted % 4 == 0) {
			worldObj.playSoundAtEntity(this, file.startsound.toString(), 1.0f, 1.0f + (float) this.throttle * 0.1f);
		}
		if (throttle > 0) {
			worldObj.playSoundAtEntity(this, file.throttlesound.toString(), 1.0f, 1.0f);

		}
	}

	public void simulateMovement() {
		// final int i = Keybindings.VehicleInventory.getKeyCode();
		if (isClient()) {

			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				System.out.println("Pressed 'W'!");
				throttle += 0.025f;
			} else {
				throttle = 0;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				brake();
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

			if (Keyboard.isKeyDown(Keybindings.Horn.getKeyCode())) {
				// RealLifeMod.network.sendToServer(new
				// OpenContainerPackage(VehicleGui.ID, this));
			}
		}

	}

	private void brake() {
		if (motorspeed > 0.0001)
			motorspeed *= 0.8;
		else
			motorspeed = 0;
	}

	public boolean interactFirst(EntityPlayer player) {
		if (isClient())
			return false;
		if (isDead)
			return false;

		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
			return true;
		} else {
			this.isEmpty = false;
			player.mountEntity(this);
			return true;
		}
	}

	public void applyForces() {
		System.out.println((airDrag() + rollDrag() + accelerationDrag() + pitchDrag()));
		
			motorspeed -= ((airDrag() + rollDrag() + accelerationDrag() + pitchDrag())/1000/60/20);
		if (motorspeed > 0)
			motorspeed -= accelerationDrag();
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

	@Override
	public void applyEntityCollision(Entity entity) {
		super.applyEntityCollision(entity);
	}

	public VehicleFile getFile() {
		return Vehicles.get(filename);
	}

	public void setDamageTaken(float p_70266_1_) {
		this.dataWatcher.updateObject(19, Float.valueOf(p_70266_1_));
	}

	public float getDamageTaken() {
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	public void setTimeSinceHit(int p_70265_1_) {
		this.dataWatcher.updateObject(17, Integer.valueOf(p_70265_1_));
	}

	public int getTimeSinceHit() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	public void setForwardDirection(int p_70269_1_) {
		this.dataWatcher.updateObject(18, Integer.valueOf(p_70269_1_));
	}

	public int getForwardDirection() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	public boolean shouldDismountInWater(Entity rider) {
		return true;
	}

	private void initVehicle(VehicleFile file, boolean isClient) {
		this.filename = file.vehicleName;
		wheels = new EntityWheel[4];
		for (int i = 0; i < wheels.length; i++) {
			//wheels[i] = new EntityWheel(worldObj, this, i);

			if (!isClient) {
				worldObj.spawnEntityInWorld(wheels[i]);
			}
		}

		/*
		 * seats = new EntitySeat[4]; for (int i = 0; i < seats.length; i++) {
		 * 
		 * seats[i] = new EntitySeat(worldObj, this, i);
		 * 
		 * if (!isClient) { worldObj.spawnEntityInWorld(seats[i]); }
		 * 
		 * }
		 */
	}

	@Override
	public void setDead() {
		super.setDead();
	}

	public Vector3d getPositionVectorFloat() {
		return new Vector3d(posX, posY, posZ);
	}

	private boolean isClient() {
		return worldObj.isRemote;
	}

}
