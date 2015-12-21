package itsamysterious.mods.reallifemod;

public class RealLifeModConfig {

	public static boolean mouseSteering;
	public static boolean minecraftstyle;
	public static boolean playermodel;
	public static boolean MOUSESTEERING_DEFAULT=false;
	public static boolean MINECRAFTSTYLE_DEFAULT=false;
	public static boolean PLAYERMODEL_DEFAULT=true;
	public static final String mousesteeringname="Enable Mousesteering for flying";
	public static final String minecraftstylename="Enable Minecraft styled 3D-Models";
	public static final String playermodelname="Enable new Playermodel";//Maybe do a preview option here



			
	public static void syncConfig() {
    	final String FEATURES=RealLifeMod.config.CATEGORY_GENERAL+RealLifeMod.config.CATEGORY_SPLITTER+"Features";
    	final String THREED=RealLifeMod.config.CATEGORY_GENERAL+RealLifeMod.config.CATEGORY_SPLITTER+"3D-Settings";
        RealLifeMod.config.addCustomCategoryComment(FEATURES, "Enable or disable different features");
        mouseSteering=RealLifeMod.config.get(FEATURES,mousesteeringname,MOUSESTEERING_DEFAULT).getBoolean(MOUSESTEERING_DEFAULT);
        RealLifeMod.config.addCustomCategoryComment(THREED, "Change 3D setting for Real Life Mod");
        minecraftstyle=RealLifeMod.config.get(THREED,minecraftstylename,MINECRAFTSTYLE_DEFAULT).getBoolean(MINECRAFTSTYLE_DEFAULT);
        playermodel=RealLifeMod.config.get(THREED,playermodelname,PLAYERMODEL_DEFAULT).getBoolean(PLAYERMODEL_DEFAULT);
        if(RealLifeMod.config.hasChanged()){
    	RealLifeMod.config.save();
        }
	}


}
