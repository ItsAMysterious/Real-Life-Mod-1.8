package itsamysterious.mods.reallifemod.core.vehicles;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.handlers.Keybindings;
import itsamysterious.mods.reallifemod.core.packets.MountVehicleMessage;
import itsamysterious.mods.reallifemod.core.packets.UpdateVehiclePacket;
import itsamysterious.mods.reallifemod.core.sounds.SoundPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVehicle extends Entity implements IEntityAdditionalSpawnData {
	private static final double g = 9.81;
	protected VehicleFile file;

	public double backWheelRotation;
	public double wheelRotL;
	public double steeringangle;
	private boolean isEmpty;
	private boolean canDoStuff;

	private EntitySeat[] seats;
	private double vehicleX;
	private double vehicleY;
	private double vehicleZ;
	private double vehicleYaw;
	private double vehiclePitch;

	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	EntityPlayer mountedPlayer;
	public double speed;
	public double fuellevel;
	private SoundPlayer player;
	// Constants

	private float P;// Power Lever
	private float Xb; // Brake pedal deflection
	private float Xn; // Brake pedal deflection

	private double F;// Propulsive fort
	private double V;// Propulsive fort

	private VehicleRadio radio;
	private boolean updateMounted;

	public EntityVehicle(World world) {
		super(world);
		this.isEmpty = true;
		this.preventEntitySpawning = true;
		this.ignoreFrustumCheck = true;
		this.setSize(0.9999F, 0.9f);

	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public void entityInit() {
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
	}

	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}

	public AxisAlignedBB getBoundingBox() {
		return this.getEntityBoundingBox();
	}

	public boolean canBePushed() {
		return false;
	}

	public EntityVehicle(World world, double x, double y, double z) {
		this(world);
		this.setPosition(x, y, z);
		// this.seats = new EntitySeat[file.numDrivers];
		// this.createSeats(world);
		// this.canDoStuff = true;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	public double getMountedYOffset() {
		return (double) this.height * 0.0D - 0.30000001192092896D;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else if (!this.worldObj.isRemote && !this.isDead) {
			if (this.riddenByEntity != null && this.riddenByEntity == source.getEntity()
					&& source instanceof EntityDamageSourceIndirect) {
				return false;
			} else {
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
				this.setBeenAttacked();
				boolean flag = source.getEntity() instanceof EntityPlayer
						&& ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				if (flag || this.getDamageTaken() > 40.0F) {
					if (this.riddenByEntity != null) {
						this.riddenByEntity.mountEntity(this);
					}

					if (!flag) {
						this.dropItemWithOffset(Items.boat, 1, 0.0F);
					}

					this.setDead();
				}

				return true;
			}
		} else {
			return true;
		}
	}

	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	private void createSeats(World w) {
		for (int i = 0; i < this.seats.length; i++) {
			System.out.println(seats.length);
			this.seats[i] = new EntitySeat(w);
			w.spawnEntityInWorld(seats[i]);
		}
		this.canDoStuff = true;
	}

	@SideOnly(Side.CLIENT)
	public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_,
			float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
		if (p_180426_10_ && this.riddenByEntity != null) {
			this.prevPosX = this.posX = p_180426_1_;
			this.prevPosY = this.posY = p_180426_3_;
			this.prevPosZ = this.posZ = p_180426_5_;
			this.rotationYaw = p_180426_7_;
			this.rotationPitch = p_180426_8_;
			this.setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
			this.motionX = this.velocityX = 0.0D;
			this.motionY = this.velocityY = 0.0D;
			this.motionZ = this.velocityZ = 0.0D;
		} else {
			{
				double d3 = p_180426_1_ - this.posX;
				double d4 = p_180426_3_ - this.posY;
				double d5 = p_180426_5_ - this.posZ;
				double d6 = d3 * d3 + d4 * d4 + d5 * d5;

				if (d6 <= 1.0D) {
					return;
				}

			}

			this.vehicleX = p_180426_1_;
			this.vehicleY = p_180426_3_;
			this.vehicleZ = p_180426_5_;
			this.vehicleYaw = p_180426_7_;
			this.vehiclePitch = p_180426_8_;
			this.motionX = this.velocityX;
			this.motionY = this.velocityY;
			this.motionZ = this.velocityZ;
		}
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.velocityX = this.motionX = x;
		this.velocityY = this.motionY = y;
		this.velocityZ = this.motionZ = z;
	}

	public void onUpdate() {
		super.onUpdate();

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		if (this.posY < -64.0D) {
			this.kill();
		}

		if (this.file == null && this.ticksExisted > 3) {
			setDead();
		}

		if (worldObj.isRemote) {
			if (this.file != null && riddenByEntity != null) {
				this.simulateMovement();
			}

		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			this.setRotation(this.rotationYaw, this.rotationPitch);

			this.posX += this.vehicleX;
			this.posY += this.vehicleY;
			this.posZ += this.vehicleZ;
			this.setPosition(this.posX, this.posY, this.posZ);

		}

		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if (!onGround) {
			this.motionX *= 0.9D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.9D;
		} else {
			this.motionY *= -0.5D;
		}

		/*
		 * if (this.canDoStuff) { for (int i = 0; i < seats.length; i++) {
		 * Vector3f f = this.file.ridersPositions.get(i);
		 * this.seats[i].setPosition(this.posX + f.x, this.posY + f.y + 0.5,
		 * this.posZ + f.z); } }
		 */

		if (this.file == null && this.ticksExisted > 1) {
			System.out.println("Missing a file!!");
			this.setDead();
		}

		if (!this.worldObj.isRemote) {

			/*List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

			if (list != null && !list.isEmpty()) {
				for (int k1 = 0; k1 < list.size(); ++k1) {
					Entity entity = (Entity) list.get(k1);

					if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityVehicle) {
						entity.applyEntityCollision(this);
					}
				}
			}*/

			if (this.ticksExisted % 4 == 0) {
				worldObj.playSoundAtEntity(this, this.file.startsound.toString(), 1.0f, 1.0f + (float) this.P * 0.1f);
			}
			if (this.P > 0) {
				worldObj.playSoundAtEntity(this, file.throttlesound.toString(), 1.0f, 1.0f);

			}

			if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
				this.riddenByEntity = null;
			}
			double x = Math.sin(Math.toRadians(rotationYaw));
			double z = Math.cos(Math.toRadians(rotationYaw));

			if (isCollidedVertically
					&& worldObj
							.getBlockState(
									(new BlockPos((int) posX + x, (int) getPosition().getY() + 0.5, (int) posZ + z)))
							.getBlock() != Blocks.air) {
				motionX = 0;
				motionY += 0.1;
				motionZ = 0;
			}
		}

	}

	public void simulateMovement() {
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (this.steeringangle < 30) {
				this.steeringangle += 3;
			}
		} else {
			if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE))
				if (this.steeringangle > 0) {
					this.steeringangle -= 3;
				}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (this.steeringangle > -30) {
				this.steeringangle -= 3;
			}
		} else {
			if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE))
				if (this.steeringangle < 0) {
					this.steeringangle += 3;
				}
		}

		boolean pressedW;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (this.speed < this.file.maxSpeed) {
				this.speed += file.acceleration * 20;

			}
			if (this.P < 30) {
				this.P += 2.5;
			}
			pressedW = true;
		} else {

			if (this.P > 5) {
				this.P -= 0.5;
			} else
				this.P = 0;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (this.speed > -this.file.maxReverseSpeed) {
				this.speed -= this.file.acceleration * 1000 / 60 / 60 * 20;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (this.speed < -5) {
				this.speed += 5;
			} else if (this.speed < 0) {
				this.speed = 0;
			}

			if (this.speed > 5) {
				this.speed -= 5;
			} else {
				this.speed = 0;
			}
		}

		double k = 0;
		double R = 0;
		if (this.steeringangle != 0) {
			R = this.file.dimensions.x / this.steeringangle;
			k = (double) 1.0D / R;
		}
		// double m = file.mass;
		/*
		 * V=this.speed*1000/m; double u= motionX; double v= motionZ;//
		 * V=Math.pow(((u*u)+(v*v)),0.5) ; double ax=u-r*v; double ay=v+r*u;
		 * double Pw; Vector2d F; double Fa; double W = m*g;
		 */
		// this.this.speed -= FLuft() + FSteig();
		double r = ((this.speed * 1000) / 60 / 60 / 20) * k;
		float ry = (float) (this.rotationYaw - r);
		this.setRotation(ry, this.rotationPitch);
		this.motionX = -(this.speed * 1000 / 60 / 60 / 20) * Math.sin(Math.toRadians(this.rotationYaw));
		this.motionZ = (this.speed * 1000 / 60 / 60 / 20) * Math.cos(Math.toRadians(this.rotationYaw));
		this.speed *= 0.988888881;

		this.wheelRotL += this.speed;
		this.backWheelRotation += this.speed;

		double dx = this.posX + this.motionX;
		double dy = this.posY + this.motionY;
		double dz = this.posZ + this.motionZ;
		this.setPosition(dx, dy, dz);
		RealLifeMod.network.sendToServer(new UpdateVehiclePacket(getEntityId(), dx, dy, dz, ry));

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		NBTTagCompound vehicletag = tagCompund.getCompoundTag("VehicleTag");
		this.fuellevel = vehicletag.getDouble("Fuel");
		if (file == null)
			if (Vehicles.getFromId(vehicletag.getInteger("FileID")) != null) {
				this.file = Vehicles.getFromId(vehicletag.getInteger("FileID"));
			}

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompund) {
		if (this.file != null) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setDouble("Fuel", this.fuellevel);
			tagCompund.setInteger("FileID", file.getID());
			tagCompund.setTag("VehicleTag", tag);
		}

	}

	public boolean interactFirst(EntityPlayer player) {
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
			return true;
		} else {
			this.isEmpty = false;
			player.mountEntity(this);
			return true;
		}
	}

	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			double k = 0;
			double R = 0;
			if (this.steeringangle != 0) {
				if (file.dimensions != null) {
					R = file.dimensions.z / steeringangle;
				} else
					R = 4.74 / steeringangle;
				k = 1 / R;
			}
			double r = ((this.speed * 1000) / 60 / 60 / 80) * k;
			this.riddenByEntity.rotationYaw -= r;
			double l = 1;
			double x = -Math.sin(Math.toRadians(rotationYaw - 25)) * 0.1;
			double z = Math.cos(Math.toRadians(rotationYaw - 25)) * 0.1;
			this.riddenByEntity.setPosition(this.posX + x, this.posY - 0.3, this.posZ + z);
		}
	};

	public VehicleFile getFile() {
		return this.file;
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

	protected double FLuft() {
		return (1.44 * (this.speed * this.speed));// (Luftdichte/2)*Strömungswiderstand*Flaeche*Geschwindigkeit²
	}

	protected double FRoll() {
		if (file != null) {
			return (file.mass) * g * VehicleHelper.rollresistancecoeff_tarmac * Math.cos(rotationPitch);
		} else {
			return 0;
		}

	}

	// Steigungswiderstand
	protected double FSteig() {
		if (file != null) {
			return (file.mass) * g * Math.sin(rotationPitch);
		}
		return 0;
	}

	protected double FB() {
		if (file != null) {
			return (mF() * (file.mass)) * aG();
		}
		return 0;
	}

	protected double mF() {
		return FT();
	}

	protected double FT() {
		if (file != null) {
			return -(file.mass) * aG();
		}
		return 0;
	}

	protected double FWGes() {
		return FLuft() + FRoll();// +FSteig()+FB());
	}

	protected double aG() {
		return Math.sqrt((vehicleX * vehicleX) + (vehicleZ * vehicleZ));
	}

	public void setFile(VehicleFile file) {
		this.file = file;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		if (file != null) {
			buffer.writeInt(file.getID());
		}
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.readEntityFromNBT(getEntityData());
		this.file = Vehicles.getFromId(additionalData.readInt());
	}

}
