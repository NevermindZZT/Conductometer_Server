package conductometer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerThread extends Thread {
	
	private Socket socket;
	private BufferedReader br = null;
	private PrintStream ps = null;
	
	public ServerThread (Socket socket) throws IOException{
		this.socket = socket;
	}
	
	public void run() {
		try {
			String line = null;
			DataManage dataManage = null;
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//获取对应输入流
			ps = new PrintStream(socket.getOutputStream());								//获取对应输出流

			ps.print("rcv-0-0\r\n");
			
			while ((line = br.readLine()) != null) {
				if (line.startsWith("send-")) {
					dataManage = getRealMsg(line);
					
					switch (dataManage.getCommand()) {
					case DataManage.DEVICE_CONNECT:
						if (TcpServer.clients.containsKey(dataManage.getSerialNumber()) == false) {
							TcpServer.clients.put(dataManage.getSerialNumber(), ps);
						} else {
							TcpServer.clients.remove(dataManage.getSerialNumber());
							TcpServer.clients.put(dataManage.getSerialNumber(), ps);
						}
						break;
						
					case DataManage.DEVICE_REGISTER:
						if (DataManage.dataManages[dataManage.getSerialNumber() - 1] == null) {
							DataManage.dataManages[dataManage.getSerialNumber() - 1] = new DataManage();
						}
						DataManage.dataManages[dataManage.getSerialNumber() - 1].setStudentNumber(dataManage.getSutdentNumber());
						MainUI.mainUI.infoPanel[dataManage.getSerialNumber() - 1].setStuNumber(dataManage.getSutdentNumber());
						break;
						
					case DataManage.DEVICE_DATA:
						if (DataManage.dataManages[dataManage.getSerialNumber() - 1] == null) {
							DataManage.dataManages[dataManage.getSerialNumber() - 1] = new DataManage();
							DataManage.dataManages[dataManage.getSerialNumber() - 1].setSerialNumber(dataManage.getSerialNumber());
						}
						DataManage.dataManages[dataManage.getSerialNumber() - 1].setProgress(dataManage.getProgress());
						MainUI.mainUI.infoPanel[dataManage.getSerialNumber() - 1].setProgressBar(dataManage.getProgress());
						if (dataManage.getProgress() >= 1) {
							DataManage.dataManages[dataManage.getSerialNumber() - 1].setSettedTemprature(dataManage.getSettedTemprature());
							if (dataManage.getProgress() >= 2) {
								DataManage.dataManages[dataManage.getSerialNumber() - 1].setBalanceTemprature(dataManage.getBalanceTemprature());
								if (dataManage.getProgress() >= 3) {
									DataManage.dataManages[dataManage.getSerialNumber() - 1].setHeatingTemprature(dataManage.getHeatingTemprature());
									if (dataManage.getProgress() >= 4) {
										DataManage.dataManages[dataManage.getSerialNumber() - 1].setMeasuredData(dataManage.getMeasuredData());
									}
								}
							}
						}
						break;
					}
				} else if (line.startsWith("AT")) {
					ps.print("OK\r\n");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, String.valueOf(TcpServer.clients.getKeyByValue(ps))
					+ "号机器发生未知错误，如果多次尝试无法解决，请放弃操作！");
			MainUI.mainUI.infoPanel[TcpServer.clients.getKeyByValue(ps) - 1].setStuNumber("未签到");
			MainUI.mainUI.infoPanel[TcpServer.clients.getKeyByValue(ps) - 1].setProgressBar(0);
			TcpServer.clients.removeByValue(ps);
			try {
				if (br != null) {
					br.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, "尝试断开连接失败，手动再见!");
			}
		}
		
	}
	
	public DataManage getRealMsg(String line) {
		DataManage dataManage = new DataManage();
		line = line.substring(5, line.length());
		
		String[] nums = line.split("-");
		
		dataManage.setCommand(Integer.parseInt(nums[0]));
	
		switch (dataManage.getCommand()) {
		case DataManage.DEVICE_CONNECT:
			dataManage.setSerialNumber(Integer.parseInt(nums[1]));
			break;
			
		case DataManage.DEVICE_REGISTER:
			dataManage.setSerialNumber(Integer.parseInt(nums[1]));
			dataManage.setStudentNumber(nums[2]);
			break;
			
		case DataManage.DEVICE_DATA:
			dataManage.setSerialNumber(Integer.parseInt(nums[1]));
			dataManage.setProgress(nums.length - 2);
			if (nums.length >= 3) {
				dataManage.setSettedTemprature(Float.parseFloat(nums[2]));
				if (nums.length >= 4) {
					dataManage.setBalanceTemprature(Float.parseFloat(nums[3]));
					if (nums.length >= 5) {
						dataManage.setHeatingTemprature(Float.parseFloat(nums[4]));
						if (nums.length >= 6) {
							for (int i = 0; i < (nums.length - 5) / 2; i++) {
								dataManage.setMeasuredTime(i, Integer.parseInt(nums[i * 2 + 5]));
								dataManage.setMeasuredTemprature(i, Float.parseFloat(nums[i * 2 + 6]));
							}
						}
					}
				}
			}
			break;
			
		}
		
		return dataManage;
	}

	
}
