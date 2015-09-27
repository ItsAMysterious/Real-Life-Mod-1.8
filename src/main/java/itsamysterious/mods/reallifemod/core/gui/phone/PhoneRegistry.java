package itsamysterious.mods.reallifemod.core.gui.phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneRegistry {
	private static List<IApp> apps=new ArrayList<IApp>();
	
	public static IApp getAppFromID(int appID) {
		return apps.get(appID);
	}
	
	public static void registerApp(IApp app){
		IApp theapp=app;
		theapp.ID=getFreeSpace();
		apps.add(theapp);
	}
	
	public static List<IApp> getListApps() {
		return apps;
	}
	
	private static int getFreeSpace(){
		return apps.size();
	}

}
