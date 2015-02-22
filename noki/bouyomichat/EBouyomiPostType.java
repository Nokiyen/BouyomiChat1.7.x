package noki.bouyomichat;


/**********
 * @enum EBouyomiPostType
 *
 * @description コマンドのタイプを指定します。
 */
public enum EBouyomiPostType {
	
	//******************************//
	// define enums.
	//******************************//
	TALK	("talk",	(short)0x0001,	true),	//nameは使用していない。
	PAUSE	("pause",	(short)0x0010,	false),
	RESUME	("resume",	(short)0x0020,	false),
	SKIP	("skip",	(short)0x0030,	false),
	CLEAR	("clear",	(short)0x0040,	false),
	RELOAD	("reload",	(short)0,		false),	//commandは使用していない。
	EDU		("edu",		(short)0x0001,	true),
	FORGET	("forget",	(short)0x0001,	true),
	ON		("on",		(short)0,		false),	//commandは使用していない。
	OFF		("off",		(short)0,		false),	//commandは使用していない。
	HELP	("help",	(short)0,		false),	//commandは使用していない。
	EXEC	("exec",	(short)0,		false);	//commandは使用していない。
	
	
	//******************************//
	// define member variables.
	//******************************//
	private String name;
	private short command;
	private boolean sendMessage;
	
	
	//******************************//
	// define member methods.
	//******************************//
	private EBouyomiPostType(String name, short command, boolean sendFlag) {
		
		this.name = name;
		this.command = command;
		this.sendMessage = sendFlag;
		
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public short getCommand() {
		
		return this.command;
		
	}
	
	public boolean needMessage() {
		
		return this.sendMessage;
		
	}
	
	public static EBouyomiPostType getEnum(String name) {
		
		EBouyomiPostType[] types =  EBouyomiPostType.values();
		for(EBouyomiPostType each: types) {
			if(each.getName().equals(name)) {
				return each;
			}
		}
		return null;
		
	}

}
