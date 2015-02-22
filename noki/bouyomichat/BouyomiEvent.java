package noki.bouyomichat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;


/**********
 * @class BouyomiEvent
 *
 * @description クライアントでチャットを受けた時のイベントです。あと、Worldイン時の新バージョン表示も。
 */
public class BouyomiEvent {

	//******************************//
	// define member variables.
	//******************************//

	
	//******************************//
	// define member methods.
	//******************************//
	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent event) {
		
		//**コンフィグによる読み上げ可否。
		if(BouyomiConf.readSwitch == false) {
			return;
		}

		String message = event.message.getUnformattedText();
		
		//**configによるチャットメッセージへの内部操作。
		//**原則生文送信で棒読みちゃんの辞書で対応してもらうことを想定し、
		//**内部での文字列操作はごくシンプルに留める。

		//コンフィグにより、名前を読ませない。
		if(BouyomiConf.readName == false) {
			Pattern pattern = Pattern.compile("<[a-zA-Z0-9_]{2,16}>\\s|\\s<[a-zA-Z0-9_]{2,16}>$");
			Matcher matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
			pattern = Pattern.compile("<[a-zA-Z0-9_]{2,16}>");
			matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
		}
		//コンフィグにより、前置詞を読ませない。
		if(BouyomiConf.readPrefix == false) {
			Pattern pattern = Pattern.compile("\\[\\S+\\]\\s|\\s\\[\\S+\\]$");
			Matcher matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
			pattern = Pattern.compile("\\[\\S+\\]");
			matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
		}
		
//		message = message.replaceAll("\\u00a7.", "");//必要なさそう。様子見。
		
		BouyomiGate.instance.stackQueue(EBouyomiPostType.TALK, message);

	}
	
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {
		
		if(!event.world.isRemote) {
			return;
		}
		
		if(event.entity instanceof EntityPlayer &&
			((EntityPlayer)event.entity).getDisplayName() == Minecraft.getMinecraft().thePlayer.getDisplayName()) {
			BouyomiChatCore.versionInfo.notifyUpdate(Side.CLIENT);
		}
		
	}

}
