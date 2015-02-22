package noki.bouyomichat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;


/**********
 * @class BouyomiCommand
 *
 * @description 追加するコマンドを定義します。
 * pause, resume, skip, clear, reload, edu, memo, on, off, help, exec.
 */
public class BouyomiCommand extends CommandBase {
	
	//******************************//
	// define member variables.
	//******************************//

	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public String getCommandName() {
		
		return "bouyomi";
		
	}
	
	@Override
	public List<String> getCommandAliases() {
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("bc");
		return list;
		
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		
		return 0;
		
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return I18n.format("bouyomichat.command.help", new Object[0]);//サーバに登録しない限り読まれない模様。
		
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(args.length == 0)  {
			sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.noarg", new Object[0])));
			stackBouyomiMessage(I18n.format("bouyomichat.command.noarg"));
			return;
		}
		EBouyomiPostType type = EBouyomiPostType.getEnum(args[0]);
		if(type == null) {
			sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.incorrectarg", new Object[0])));
			stackBouyomiMessage(I18n.format("bouyomichat.command.incorrectarg"));
			return;
		}
		
		switch(type) {
			case PAUSE:
				BouyomiGate.instance.stackQueue(type);
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.pause", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.pause"));
				break;
			case RESUME:
				BouyomiGate.instance.stackQueue(type);
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.resume", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.resume"));
				break;
			case SKIP:
				BouyomiGate.instance.stackQueue(type);
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.skip", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.skip"));
				break;
			case CLEAR:
				BouyomiGate.instance.stackQueue(type);
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.clear", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.clear"));
				break;
			case RELOAD:
				BouyomiConf.setConf();
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.reload", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.reload"));
				break;
			case EDU:
				if(args[1] == null || args[2] == null) {
					sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.missingarg", new Object[0])));
					stackBouyomiMessage(I18n.format("bouyomichat.command.missingarg"));
					return;
				}
				BouyomiGate.instance.stackQueue(type, "教育("+args[1]+"="+args[2]+")");
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.educate", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.educate"));
				break;
			case FORGET:
				if(args[1] == null) {
					sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.missingarg", new Object[0])));
					stackBouyomiMessage(I18n.format("bouyomichat.command.missingarg"));
					return;
				}
				BouyomiGate.instance.stackQueue(type, "忘却("+args[1]+")");
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.forget", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.forget"));
				break;
			case ON:
				BouyomiConf.readSwitch = true;
				BouyomiGate.instance.stackQueue(EBouyomiPostType.CLEAR);
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.on", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.on"));
				break;
			case OFF:
				BouyomiConf.readSwitch = false;
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.off", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.off"));
				break;
			case HELP:
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.help", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.help"));
				break;
			case EXEC:
				boolean res = BouyomiChatCore.executeBouyomiChan();
				if(res == true) {
					sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.exec", new Object[0])));
					stackBouyomiMessage(I18n.format("bouyomichat.command.exec"));
				}
				else {
					sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.execfail", new Object[0])));
					stackBouyomiMessage(I18n.format("bouyomichat.command.execfail"));
				}
				break;
			default:
				sender.addChatMessage(new ChatComponentText(I18n.format("bouyomichat.command.fail", new Object[0])));
				stackBouyomiMessage(I18n.format("bouyomichat.command.fail"));
				break;
		}
		
	}
	
	private static void stackBouyomiMessage(String message) {
		
		if(BouyomiConf.readBouyomi) {
			BouyomiGate.instance.stackQueue(EBouyomiPostType.TALK, message);
		}
		
	}
	


}
