package noki.bouyomichat;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


/**********
 * @class BouyomiConf
 *
 * @description このModのコンフィグの値を保存するクラスです。
 */
public class BouyomiConf {
	
	//******************************//
	// define member variables.
	//******************************//
	public static File configFile;
	
	public static String host;
	public static final String hostDefault= "localhost";
	public static int port;
	public static final int portDefault = 50001;
	
	public static boolean readName;
	public static final boolean readNameDefault = true;
	public static boolean readPrefix;
	public static final boolean readPrefixDefault = true;
	public static boolean readBouyomi;
	public static final boolean readBouyomiDefault = false;
	public static boolean readSwitch;
	public static final boolean readSwitchDefault = true;
	
	public static short speed;
	public static final short speedDefault = (short)-1;
	public static short tone;
	public static final short toneDefault =(short)-1;
	public static short volume;
	public static final short volumeDefault = (short)-1;
	public static short voice;
	public static final short voiceDefault = (short)0;
	
	public static final String charsetName = "UTF-8";
	
	public static String pathBouyomi;
	public static boolean autoExecBouyomi;
	
	
	//******************************//
	// define member methods.
	//******************************//
	public static void setConf() {
		
		Property prop;
		Configuration cfg = new Configuration(configFile);
		cfg.load();
		
		prop = cfg.get("Settings", "host", hostDefault);
		host = prop.getString();
		prop = cfg.get("Settings", "port", portDefault);
		port = prop.getInt();
		
		prop = cfg.get("Settings", "readName", readNameDefault);
		readName = prop.getBoolean(readNameDefault);
		prop = cfg.get("Settings", "readPrefix", readPrefixDefault);
		readPrefix = prop.getBoolean(readPrefixDefault);
		prop = cfg.get("Settings", "readBouyomi", readBouyomiDefault);
		readBouyomi = prop.getBoolean(readBouyomiDefault);
		prop = cfg.get("Settings", "readSwitch", readSwitchDefault);
		readSwitch = prop.getBoolean(readSwitchDefault);
		
		prop = cfg.get("Settings", "speed", speedDefault);
		speed = (short)prop.getInt();
		if(speed != -1 && (speed < 50 || 300 < speed)) {
			speed = speedDefault;
		}
		prop = cfg.get("Settings", "tone", toneDefault);
		tone = (short)prop.getInt();
		if(tone != -1 && (tone < 50 || 200 < tone)) {
			tone = toneDefault;
		}
		prop = cfg.get("Settings", "volume", volumeDefault);
		volume = (short)prop.getInt();
		if(volume != -1 && (volume < 0 || 100 < volume)) {
			volume = volumeDefault;
		}
		prop = cfg.get("Settings", "voice", voiceDefault);
		voice = (short)prop.getInt();
		if(voice != 0 && (voice < 1 || 8 < voice)) {
			voice = voiceDefault;
		}
		
		prop = cfg.get("Settings", "pathBouyomi", "");
		pathBouyomi = prop.getString();
		pathBouyomi = pathBouyomi.replaceAll("\\\\", "\\\\\\\\");
		
		prop = cfg.get("Settings", "autoExecBouyomi", true);
		autoExecBouyomi = prop.getBoolean(true);
		
		
		cfg.save();
	}

}
