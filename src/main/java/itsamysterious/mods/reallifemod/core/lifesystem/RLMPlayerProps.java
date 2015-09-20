package itsamysterious.mods.reallifemod.core.lifesystem;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RLMPlayerProps implements IExtendedEntityProperties {

	private EntityPlayer player;
	private World theWorld;

	private String name;
	private String surname;

	private double toilet;
	private double water;
	public float money;
	private double energy;
	private double stamina;
	private String gender;

	public boolean shownGui;

	private int timeWaterless = 0;
	private int waterlowmessagetime = 0;

	private String partner;

	public static final String EXT_PROP_NAME = "RealLifeProperties";

	public TileEntity_Electric lastTile;
	private boolean isPeeing;

	public RLMPlayerProps() {
	}

	public RLMPlayerProps(EntityPlayer player) {
		this.player = player;
	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(RLMPlayerProps.EXT_PROP_NAME, new RLMPlayerProps(player));
	}

	public static final RLMPlayerProps get(EntityPlayer player) {
		return (RLMPlayerProps) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public void circleOfLife() {
		this.theWorld = this.player.worldObj;
		if (player != null && !player.capabilities.isCreativeMode) {
			if (getWater() > 0.1) {
				setWater(getWater() - 0.00138888889D);
			}

			if (toilet < 100) {
				toilet += 0.00415151515151515D;
			}

			updateEffects();
		}
	}

	private void updateEffects() {
		if (getWater() < 40 && getWater() > 10 && waterlowmessagetime % 200 == 0) {
			player.addChatComponentMessage(LinesHelper.ThirstWarning);

		}
		if (getWater() < 10 && getWater() > 0.1) {
			player.addChatComponentMessage(LinesHelper.ThirstWarning2);
			player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 100));
		} else if (player.getActivePotionEffect(Potion.confusion) != null) {
			player.removePotionEffect(Potion.confusion.id);
		}

		if (getWater() < 0.1) {
			player.addPotionEffect(new PotionEffect(Potion.weakness.getId(), 100));
			timeWaterless++;
			if (timeWaterless == 200) {
				player.addChatComponentMessage(
						new ChatComponentText("Look at the empty bar at the top. It will hold your current amount of "
								+ EnumChatFormatting.AQUA + "Water" + EnumChatFormatting.RESET + "later on. "));
				// player.addChatComponentMessage(LinesHelper.DyingOfThirst);
				// player.setHealth(player.getHealth() - 1);
			}

		}
		
		if(isPeeing){
			if(this.getWater()>0&&isPeeing){
				this.water-=0.25;
			}else
			{
				if(this.getWater()<=0){
					this.isPeeing=false;
				}
			}
		}
		if (toilet > 50) {
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 1));
		} else {
			player.removePotionEffect(Potion.digSlowdown.getId());

		}

	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setDouble("WATER", getWater());
		properties.setDouble("TOILET", toilet);
		properties.setFloat("MONEY", money);
		if (this.name != null && !this.surname.isEmpty())
			properties.setString("NAME", this.name);
		else
			properties.setString("NAME", "Set");
		if (this.surname != null && !this.surname.isEmpty())
			properties.setString("SURNAME", surname.isEmpty() ? "Yourname" : this.surname);
		properties.setDouble("ENERGIE", energy);
		properties.setBoolean("GUISHOWN", shownGui);
		if (getGender() != null) {
			properties.setString("GENDER", getGender());
		} else {
			properties.setString("GENDER", "male");
		}
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound theTag = compound.getCompoundTag(EXT_PROP_NAME);
		this.setWater(theTag.getDouble("WATER"));
		this.toilet = theTag.getDouble("TOILET");
		this.money = theTag.getFloat("MONEY");
		this.setName(theTag.getString("NAME"));
		this.setSurname(theTag.getString("SURNAME"));
		this.energy = theTag.getDouble("ENERGIE");
		this.shownGui = theTag.getBoolean("GUISHOWN");
		this.setGender(theTag.getString("GENDER"));

	}

	@Override
	public void init(Entity entity, World world) {
		if (entity instanceof EntityPlayer) {
			this.player = (EntityPlayer) entity;
			this.setWater(100);
			this.toilet = 0;
		}
	}

	public void copy(RLMPlayerProps props) {
		this.name = props.name;
		this.surname = props.surname;
		this.gender = props.gender;

	}

	public void pee() {
		if (toilet > 0) {
			toilet -= 1;
		}
	}

	public void setGender(String s) {
		if (s.equals("male")) {
			gender = "male";
		} else
			gender = "female";
	}

	public static final String getFullname(EntityPlayerSP thePlayer) {
		return get(thePlayer).getName() + " " + get(thePlayer).getSurname();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public void setPeeing() {
		this.isPeeing = true;
	}

}
