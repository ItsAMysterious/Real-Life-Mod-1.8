package itsamysterious.mods.reallifemod.core.lifesystem.enums;

public enum EnumGender {
	Male,Female;

	public static EnumGender getFromString(String string) {
		return EnumGender.valueOf(string);
	}
	
	public static String toString(EnumGender g) {
		return g.toString();
	}

}
