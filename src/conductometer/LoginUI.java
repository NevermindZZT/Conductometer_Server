package conductometer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7633784214143870620L;
	
	public static final boolean autoWifi = false;
	private static final String ssid = "BMZL";
	private static final String key = "csuwlsyzxbmzl";

	public LoginUI() {
		JFrame loginFrame = new JFrame("登录");
		JLabel logoLabel = new JLabel(new ImageIcon("img/login.png"), JLabel.CENTER);
		JLabel nameLabel = new JLabel("用户：");
		JLabel passwordLabel = new JLabel("密码：");
		JTextField nameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
		JButton loginButton = new JButton("登录");
		JButton resetButton = new JButton("重置");
		
		loginFrame.setIconImage(new ImageIcon("img/icon.png").getImage());
		
		loginFrame.setLayout(new FlowLayout());
		loginFrame.setSize(320, 260);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setVisible(true);
		loginFrame.setResizable(false);
		
		loginFrame.add(logoLabel);
		loginFrame.add(nameLabel);
		loginFrame.add(nameField);
		loginFrame.add(passwordLabel);
		loginFrame.add(passwordField);
		loginFrame.add(loginButton);
		loginFrame.add(resetButton);
		
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if (autoWifi) {
					try {
						Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=allow ssid=" + LoginUI.ssid +
																	" key=" + LoginUI.key);
						Runtime.getRuntime().exec("netsh wlan start hostednetwork");
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "热点建立失败，请手动建立热点\nssid:" + LoginUI.ssid + " key:" +
													LoginUI.key);
					}
				}
				
				InetAddress ip = null;
				try {
					ip = InetAddress.getLocalHost();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				TcpServer.mainTcpServer = new TcpServer();
				TcpServer.mainTcpServer.start();
				MainUI.mainUI = new MainUI();
				MainUI.mainUI.setServerIp(ip.getHostAddress().toString());
				
				loginFrame.dispose();
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nameField.setText("");
				passwordField.setText("");
			}
		});
		
	}
	
	public static void main(String[] args) {
		new LoginUI();
	}
	
}
