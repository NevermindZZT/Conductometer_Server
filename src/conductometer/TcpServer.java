package conductometer;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


public class TcpServer extends Thread{
	public static final int SERVER_PORT = 8266;
	public static YeekuMap<Integer, PrintStream> clients = 
			new YeekuMap<Integer, PrintStream>();
	
	public static TcpServer mainTcpServer;						//静态服务实例
	
	public ServerSocket ss = null;
	
	public void run() {
		try {
			ss = new ServerSocket(SERVER_PORT);
			while (true) {
				Socket socket =ss.accept();
				new ServerThread(socket).start();
			}
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "端口被占用，服务已关闭");
		}
		finally{
			try {
				if (ss != null) {
					ss.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			System.exit(1);
		}

	}
}

