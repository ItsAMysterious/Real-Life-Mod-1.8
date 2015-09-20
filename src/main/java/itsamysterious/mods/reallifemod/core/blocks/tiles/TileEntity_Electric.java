package itsamysterious.mods.reallifemod.core.blocks.tiles;

import java.util.Random;

import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntity_Electric extends RLMTileEntity implements IUpdatePlayerListBox {
	private float voltage;
	public boolean isUsuable;
	public int age;
	public BlockPos storedPos;
	public BlockPos storedPosFrom;

	private TileEntity_Electric to;
	private TileEntity_Electric from;

	@SideOnly(Side.CLIENT)
	public float clientVoltage;

	public TileEntity_Electric() {
		isUsuable = true;
	}

	@Override
	public void update() {
		age++;
		if (!this.hasWorldObj())
			return;
		if (worldObj.isRemote) {
			Random rand = new Random();
			if (!isUsuable) {
				if (age % 3 == 0)
					worldObj.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
							-0.025 + rand.nextFloat() / 10, rand.nextDouble() / 5, -0.025 + rand.nextFloat() / 10);
			}
			this.clientVoltage = getVoltage();
		}
		{

			if (voltage > getMaxVoltage()) {
				isUsuable = false;
			}

			if (getTo() != null) {
				if (getVoltage() >= getMinVoltage() && isUsuable) {
					getTo().from = this;
					onPowered(voltage);
					passOverVoltage(getVoltageValueAfterPowering());
				}
				if (storedPos != null && worldObj.getTileEntity(storedPos) == null
						|| !(worldObj.getTileEntity(storedPos) instanceof TileEntity_Electric)) {
					to = null;
					storedPos = null;
				}
			}

			if (getFrom() == null && !this.isPowerSource()) {
				this.voltage = 0;
			}

		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound tag = new NBTTagCompound();
		if (storedPos != null) {
			BlockPos pto = storedPos;
			NBTTagCompound blockPosNBT = new NBTTagCompound();
			blockPosNBT.setInteger("x", pto.getX());
			blockPosNBT.setInteger("y", pto.getY());
			blockPosNBT.setInteger("z", pto.getZ());
			blockPosNBT.setInteger("xf", pto.getX());
			blockPosNBT.setInteger("yf", pto.getY());
			blockPosNBT.setInteger("zf", pto.getZ());
			tag.setTag("connectedBlock", blockPosNBT);
		}
		tag.setFloat("Voltage", this.voltage);
		tag.setBoolean("IsUsuable", this.isUsuable);
		tag.setInteger("Age", this.age);
		compound.setTag("RLMElectrizityTag", tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		final int NBT_INT_ID = 3;

		NBTTagCompound tag = compound.getCompoundTag("RLMElectrizityTag");

		NBTTagCompound blockPosNBT = tag.getCompoundTag("connectedBlock");
		BlockPos readBlockPos = null;
		if (blockPosNBT.hasKey("x", NBT_INT_ID) && blockPosNBT.hasKey("y", NBT_INT_ID)
				&& blockPosNBT.hasKey("z", NBT_INT_ID)) {
			readBlockPos = new BlockPos(blockPosNBT.getInteger("x"), blockPosNBT.getInteger("y"),
					blockPosNBT.getInteger("z"));
			this.storedPos = readBlockPos;
		}

		if (blockPosNBT.hasKey("xf", NBT_INT_ID) && blockPosNBT.hasKey("yf", NBT_INT_ID)
				&& blockPosNBT.hasKey("zf", NBT_INT_ID)) {
			readBlockPos = new BlockPos(blockPosNBT.getInteger("xf"), blockPosNBT.getInteger("yf"),
					blockPosNBT.getInteger("zf"));
			this.storedPosFrom = readBlockPos;
		}

		this.voltage = tag.getFloat("Voltage");
		this.isUsuable = tag.getBoolean("IsUsuable");
		this.age = tag.getInteger("Age");
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public boolean connectTo(TileEntity_Electric t) {
		this.storedPos = t.getPos();
		t.storedPosFrom = this.getPos();
		return !(t.isPowerSource());
	}

	public boolean isPowerSource() {
		return false;
	}

	public void passOverVoltage(float v) {
		if (getTo() != null) {
			getTo().setVoltage(v);
			worldObj.markBlockForUpdate(getTo().getPos());
		}
	}

	public abstract float getVoltageValueAfterPowering();

	public abstract float getMinVoltage();

	public float getVoltage() {
		return voltage;
	}

	public void setVoltage(float v) {
		voltage = v;
	}

	public abstract void onPowered(float f);

	public float getMaxVoltage() {
		return 440;
	};

	public TileEntity_Electric getTo() {
		if (storedPos != null)
			if ((to == null || to.isInvalid()) && worldObj.getTileEntity(storedPos) instanceof TileEntity_Electric) {
				to = (TileEntity_Electric) worldObj.getTileEntity(storedPos);
			}
		return to;
	}

	public TileEntity_Electric getFrom() {
		if (storedPosFrom != null)
			if ((from == null || from.isInvalid())
					&& worldObj.getTileEntity(storedPosFrom) instanceof TileEntity_Electric) {
				from = (TileEntity_Electric) worldObj.getTileEntity(storedPosFrom);
			}
		return from;
	}

	public void disconnect() {
		if (getTo() instanceof TileEntity_Lantern) {
			getTo().setVoltage(0);
			getTo().from = null;
			worldObj.markBlockForUpdate(storedPos);
		}
		this.storedPos = null;
		this.to = null;
	}

}
