package noki.bouyomichat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;


/**********
 * @class BouyomiGate
 *
 * @description 棒読みちゃんへデータを送信するクラスです。
 */
public class BouyomiGate {
	
	//******************************//
	// define member variables.
	//******************************//
	public static BouyomiGate instance = new BouyomiGate();
	
	private BouyomiThread thread;
	private LinkedList<BouyomiData> waitings = new LinkedList<BouyomiData>();

	
	//******************************//
	// define member methods.
	//******************************//
	public void stackQueue(EBouyomiPostType type) {
		
		this.waitings.offer(new BouyomiData(type));
		this.ignateThread();
		
	}
	
	public void stackQueue(EBouyomiPostType type, String message) {
		
		this.waitings.offer(new BouyomiData(type, message));
		this.ignateThread();
				
	}
	
	private void ignateThread() {
		
		if(this.thread == null || this.thread.getState() == Thread.State.TERMINATED) {
			this.thread = new BouyomiThread();
		}
		if(thread.getState() == Thread.State.NEW) {
			this.thread.start();
		}

	}
	
	
	//******************************//
	// define inner class.
	//******************************//
	private class BouyomiThread extends Thread {
		
		@Override
		public void run() {
			
			try {
				while(waitings.size() > 0) {
					
					BouyomiData data = waitings.poll();
					data.post();
					
					Thread.sleep(200);
				}
			}
			catch(InterruptedException exception) {
				BouyomiChatCore.log("The thread was illegally stopped.");
			}
			
		}
 	
	}//end of inner class.
	

	//******************************//
	// define inner class.
	//******************************//
	private class BouyomiData {
		
		private EBouyomiPostType type;
		private String message;
		
		public BouyomiData(EBouyomiPostType type) {
			
			this.type = type;
			this.message = null;
			
		}
		
		public BouyomiData(EBouyomiPostType type, String message) {
			
			this.type = type;
			this.message = message;
			
		}
		
		public void post() {
			
			try {
				if(this.type.needMessage() == true) {
					this.postReal(this.type, this.message);
				}
				else {
					this.postReal(this.type);
				}
			}
			catch(IOException exception) {
				BouyomiChatCore.log("Can't send to BouyomiChan.");
				BouyomiChatCore.log(exception.toString());
				BouyomiChatCore.log(this.type.getName());
				if(this.message != null) {
					BouyomiChatCore.log(this.message);
				}
			}
			
		}
		
		private void postReal(EBouyomiPostType type) throws IOException {
			
			Socket socket = null;
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress(BouyomiConf.host, BouyomiConf.port));
				
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				output.writeShort(Short.reverseBytes(type.getCommand()));
				output.flush();
				output.close();
			}
			finally {
				if(socket != null) socket.close();
			}
			
		}
		
		private void postReal(EBouyomiPostType type, String message) throws IOException {
			
			Socket socket = null;
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress(BouyomiConf.host, BouyomiConf.port));
	
				byte[] b;
				b = message.getBytes(BouyomiConf.charsetName);
			
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				output.writeShort(Short.reverseBytes(type.getCommand()));
				output.writeShort(Short.reverseBytes(BouyomiConf.speed));
				output.writeShort(Short.reverseBytes(BouyomiConf.tone));
				output.writeShort(Short.reverseBytes(BouyomiConf.volume));
				output.writeShort(Short.reverseBytes(BouyomiConf.voice));
				output.write(0);
				output.writeInt(Integer.reverseBytes(b.length));
				output.write(b, 0, b.length);
				output.flush();
				output.close();
			}
			finally {
				if(socket != null) socket.close();				
			}
			
		}
		
	}//end of inner class.
	
}
