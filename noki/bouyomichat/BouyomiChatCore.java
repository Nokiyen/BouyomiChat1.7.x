package noki.bouyomichat;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;


/**********
 * @Mod BouyomiChat
 *
 * @author Nokiyen
 * 
 * @description 棒読みちゃんにチャットを読んでもらうModです。
 * 元祖棒読みちゃんMod作者のIwatoRockyさんのコードを参考にしています(しかし、内容はけっこう違いﾏｽ)。
 */
@Mod(modid = "BouyomiChat", version = "1.0.3", name = "Bouyomi Chat")
public class BouyomiChatCore {
	
	//******************************//
	// define member variables.
	//******************************//
	@Instance(value = "BouyomiChat")
	public static BouyomiChatCore instance;
	@Metadata
	public static ModMetadata metadata;	//	extract from mcmod.info file, not java internal coding.
	public static VersionInfo versionInfo;

	
	//******************************//
	// define member methods.
	//******************************//
	//----------
	//Core Event Methods.
	//----------
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		BouyomiConf.configFile = event.getSuggestedConfigurationFile();
		BouyomiConf.setConf();
		
		versionInfo = new VersionInfo(metadata.modId.toLowerCase(), metadata.version, metadata.updateUrl);
		
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new BouyomiEvent());
		ClientCommandHandler.instance.registerCommand(new BouyomiCommand());//	register a command to the client.
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		if(BouyomiConf.autoExecBouyomi == true) {
			executeBouyomiChan();
		}
	
	}
	
	
	//----------
	//Static Method.
	//----------
	public static void log(String message, Object... data) {
		
		FMLLog.fine("[LOG] "+message, data);
		
	}

	public static boolean executeBouyomiChan() {
		
		File file = new File(BouyomiConf.pathBouyomi);
		boolean flag = true;
		if(file.exists()) {
			try {
				Runtime rt = Runtime.getRuntime();
				rt.exec(BouyomiConf.pathBouyomi);
			} catch (IOException ex) {
				BouyomiChatCore.log("Can't execute Bouyomi-Chan.");
				flag = false;
			}
		}
		else {
			flag = false;
		}
		
		return flag;
		
	}
	
}
