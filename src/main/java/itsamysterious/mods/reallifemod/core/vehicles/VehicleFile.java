package itsamysterious.mods.reallifemod.core.vehicles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.sounds.CustomSound;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.util.ResourceLocation;

public class VehicleFile {

	public double maxSpeed;
	public double maxReverseSpeed;

	public float mass;
	public float acceleration;
	public Vector3f dimensions;

	public Vector3f steeringwheelpos;

	public Vector3f[] wheelPositions = new Vector3f[0];

	public Vector3f[] seatPositions =  new Vector3f[0];

	public String iconFile;
	public String vehicleName;
	public int numDrivers;

	public String fileName;
	public String modelName;
	public String wheelsName;
	public String steeringWheelName;
	public String transparentpartsname;
	public String textureName;
	public ResourceLocation texture;
	public ResourceLocation speedometertexture;

	public CustomSound startsound;
	public CustomSound stopsound;
	public CustomSound throttlesound;
	public CustomSound enginesound;

	public double price;

	public IModelCustom model;
	private int id;
	public float height = 1;
	public float width = 1;
	public VehicleTypes vehicleType = VehicleTypes.Car;
	public float wheelStepHeight;

	public VehicleFile(File f) {
		loadFromFile(f);

	}

	private void loadFromFile(File f) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {

				if (line.toLowerCase().startsWith("maxspeed:")) {
					this.maxSpeed = Double.parseDouble(line.split(" ")[1]);
				}

				if (line.toLowerCase().startsWith("maxreversespeed:")) {
					this.maxReverseSpeed = Double.parseDouble(line.split(" ")[1]);

				}

				if (line.toLowerCase().startsWith("wheelcount:")) {
					wheelPositions = new Vector3f[Integer.parseInt(line.split(" ")[1])];

				}
				if (wheelPositions.length <= 0)
					wheelPositions = new Vector3f[4];
				else if (line.toLowerCase().toLowerCase().startsWith("wheelpositions: ")) {
					Vector3f position = new Vector3f();
					for (int i = 0; i < wheelPositions.length; i++) {
						String posVector = line.split(":")[1].split(",")[i];
						position.x = Float.parseFloat(posVector.split("/")[0]);
						position.y = Float.parseFloat(posVector.split("/")[1]);
						position.z = Float.parseFloat(posVector.split("/")[2]);
						wheelPositions[i] = new Vector3f();
						wheelPositions[i].set(Float.parseFloat(posVector.split("/")[0]),
								Float.parseFloat(posVector.split("/")[1]), Float.parseFloat(posVector.split("/")[2]));
					}

				}
				
				if (line.toLowerCase().toLowerCase().startsWith("seatpositions: ")) {
					numDrivers=line.split(" ")[1].split(",").length;
					Vector3f position = new Vector3f();
					seatPositions=new Vector3f[numDrivers];
					for (int i = 0; i < seatPositions.length; i++) {
						String posVector = line.split(":")[1].split(",")[i];
						position.x = Float.parseFloat(posVector.split("/")[0]);
						position.y = Float.parseFloat(posVector.split("/")[1]);
						position.z = Float.parseFloat(posVector.split("/")[2]);
						seatPositions[i] = new Vector3f();
						seatPositions[i].set(Float.parseFloat(posVector.split("/")[0]),
								Float.parseFloat(posVector.split("/")[1]), Float.parseFloat(posVector.split("/")[2]));
					}
					numDrivers=seatPositions.length;

				}

				/*
				 * if (line.startsWith("steeringWheelPos: ")) { Vector3f
				 * position = new Vector3f(); position.x =
				 * Float.parseFloat(line.split(" ")[1].split("|")[0]);
				 * position.y = Float.parseFloat(line.split("|")[1]); position.z
				 * = Float.parseFloat(line.split("|")[2]); this.steeringwheelpos
				 * = position;
				 * 
				 * }
				 */

				if (line.toLowerCase().startsWith("numdrivers: ")) {
					this.numDrivers = Integer.parseInt(line.split(" ")[1]);
				}

				/*
				 * if (line.startsWith("seatPos_") && this.ridersPositions !=
				 * null) {
				 * 
				 * float f1 = Float.parseFloat(line.split(" "
				 * )[1].split("|")[0]); float f2 =
				 * Float.parseFloat(line.split("|")[1]); float f3 =
				 * Float.parseFloat(line.split("|")[2]); int i =
				 * Integer.parseInt(line.split("_")[1].split(":")[0]);
				 * ridersPositions.add(i, new Vector3f(f1, f2, f3)); }
				 */

				if (line.toLowerCase().startsWith("iconfile:")) {
					this.iconFile = line.split(" ")[1];
				}

				if (line.toLowerCase().startsWith("acceleration:")) {
					this.acceleration = Float.parseFloat(line.split(" ")[1]);
				}

				if (line.toLowerCase().startsWith("mass:")) {
					this.mass = Float.parseFloat(line.split(" ")[1]);
				}

				if (line.toLowerCase().startsWith("dimensions:")) {
					float f1 = Float.parseFloat(line.split(" ")[1].split(",")[0]);
					float f2 = Float.parseFloat(line.split(",")[1].split(",")[0]);
					float f3 = Float.parseFloat(line.split(",")[1].split(",")[0]);
					this.dimensions = new Vector3f(f1, f2, f3);
				}

				if (line.toLowerCase().startsWith("vehiclename:")) {
					this.vehicleName = line.split(" ")[1];
				}

				if (line.toLowerCase().startsWith("modelname:")) {
					this.modelName = line.split(" ")[1];
				}

				if (line.toLowerCase().startsWith("wheelsname:")) {
					this.wheelsName = line.split(" ")[1];
				}

				if (line.toLowerCase().startsWith("steeringwheelname:")) {
					this.steeringWheelName = line.split(" ")[1];
				}

				if (line.toLowerCase().startsWith("transparentname:")) {
					this.transparentpartsname = line.split(" ")[1];
				}

				if (line.toLowerCase().startsWith("texturename:")) {
					this.texture = new ResourceLocation("reallifemod:textures/vehicle/" + line.split(" ")[1] + ".png");
					this.textureName = line.split(" ")[1];
				}

				if (line.toLowerCase().startsWith("filename:")) {
					this.model = AdvancedModelLoader.loadModel(
							new ResourceLocation("reallifemod:models/vehicle/" + line.split(" ")[1] + ".obj"));
					this.fileName = line.split(" ")[1];
				}

				/** Sound stuff */
				if (line.toLowerCase().startsWith("sound_start:")) {
					int length = Integer.parseInt(line.split(":")[2].trim());
					this.startsound = new CustomSound(new ResourceLocation(Reference.ID + ":" + line.split(" ")[1]),
							length);
				}

				if (line.toLowerCase().startsWith("sound_stop:")) {
					int length = Integer.parseInt(line.split(":")[2].trim());
					this.stopsound = new CustomSound(new ResourceLocation(Reference.ID + ":" + line.split(" ")[1]),
							length);
				}

				if (line.toLowerCase().startsWith("sound_throttle:") || line.toLowerCase().startsWith("sound_run:")) {
					int length = Integer.parseInt(line.split(":")[2].trim());

					this.throttlesound = new CustomSound(new ResourceLocation(Reference.ID + ":" + line.split(" ")[1]),
							length);
				}

				if (line.toLowerCase().startsWith("sound_run:")) {
					int length = Integer.parseInt(line.split(":")[2].trim());

					this.enginesound = new CustomSound(new ResourceLocation(Reference.ID + ":" + line.split(" ")[1]),
							length);
				}

				if (line.toLowerCase().startsWith("price:")) {
					this.price = Double.parseDouble(line.split(" ")[1]);
				}

				if (line.toLowerCase().startsWith("id:")) {
					this.id = Integer.parseInt(line.split(" ")[1]);
				}

				if (line.toLowerCase().startsWith("wheelstepheight:")) {
					this.wheelStepHeight = Float.parseFloat(line.split(" ")[1]);
				}
			}
			reader.close();
		} catch (IOException e) {
		}
	}

	public int getID() {
		return id;
	}

}
