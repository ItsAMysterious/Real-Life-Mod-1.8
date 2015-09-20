package itsamysterious.mods.reallifemod.core.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class RLM_StorageTileEntity extends RLMTileEntity implements IInventory{

	public static int INV_SIZE = 15;
	private ItemStack[] inventory;
	private String invName;
	private String tagName;
	
	public RLM_StorageTileEntity(int size) {
		this.INV_SIZE=size;
		inventory = new ItemStack[size];
	}

	public int getSizeInventory() {
		return inventory.length;
	}
	
	public boolean receiveClientEvent(int par1, int par2) {
		return super.receiveClientEvent(par1, par2);
	}

	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize > amount) {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			} else {
				setInventorySlotContents(slot, null);
			}

			this.onInventoryChanged();
		}
		return stack;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) 
			setInventorySlotContents(slot, null);
		return stack;
	}

	public void onInventoryChanged() {
		for (int i = 0; i < inventory.length; ++i) {
			if (this.getStackInSlot(i) != null
					&& this.getStackInSlot(i).stackSize == 0)
				this.setInventorySlotContents(i, null);
		}
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		worldObj.markBlockForUpdate(pos);
		markDirty(); 
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return getWorld().getTileEntity(this.pos) == this
				&& p_70300_1_.getDistanceSq((double) pos.getX() + 0.5D,
						(double) pos.getY() + 0.5D,
						(double) pos.getZ() + 0.5D) <= 64.0D;

	}

	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		// If you have different kinds of slots, then check them here:
		// if (slot == SLOT_SHIELD && itemstack.getItem() instanceof ItemShield)
		// return true;

		// For now, only ItemUseMana items can be stored in these slots
		return true;
	}

	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
		NBTTagList nbttaglist = p_145839_1_.getTagList(getName()+"Items", 10);
		this.inventory = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
			int j = tag.getByte("Slot") & 255;

			if (j >= 0 && j < inventory.length) {
				this.inventory[j] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	public void writeToNBT(NBTTagCompound comp) {
		super.writeToNBT(comp);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i) {
			ItemStack stack = inventory[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				nbttaglist.appendTag(tag);
			}
		}

		comp.setTag("DrawerItems", nbttaglist);

	}

	public ItemStack getItem(int slot) {
		ItemStack theItem = inventory[slot];
		if (theItem != null)
			return theItem;
		else
			return null;
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound dataTag = new NBTTagCompound();
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setLong("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		dataTag.setTag(getName()+"Items", itemList);
		this.writeToNBT(dataTag);
		return new S35PacketUpdateTileEntity(pos, 1, dataTag);

	}

	public void onDataPacket(NetworkManager manager,
			S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbtData = packet.getNbtCompound();
		NBTTagList tagList = nbtData.getTagList(getName()+"Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < inventory.length; i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.length)
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
		worldObj.markBlockForUpdate(pos); 
		markDirty();
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName()+" Inventory");
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public int getField(int id) {
		return id%INV_SIZE;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return INV_SIZE;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
