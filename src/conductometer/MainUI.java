package conductometer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6860427745279247440L;
	
	public static MainUI mainUI;
	
	private String serverIp;
	private JLabel ipLabel = new JLabel();
	public InfoPanel[] infoPanel = new InfoPanel[30];

	public MainUI() {
		JFrame mainFrame = new JFrame("�����ȵ��嵼��ϵ����������ϵͳ");
		JLabel logoLabel = new JLabel(new ImageIcon("img/logo.png"), JLabel.CENTER);
		JLabel logo2Label = new JLabel("���ϴ�ѧ���������ѧԺ    ", JLabel.RIGHT);
		JButton signButton = new JButton("�ֶ�ǩ��");
		JPanel logoPanel = new JPanel();
		JPanel studentPanel = new JPanel();
		
		logo2Label.setFont(new Font("����", Font.PLAIN, 18));
		logo2Label.setPreferredSize(new Dimension(500, 30));
		
		signButton.setPreferredSize(new Dimension(100, 40));
		signButton.setBackground(new Color(220, 220, 220));
		signButton.setBorderPainted(false);
		signButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					for (PrintStream clientPs : TcpServer.clients.valueSet()) {
						clientPs.print("rcv-" + String.valueOf(DataManage.DEVICE_REGISTER) + "-" + 
								String.valueOf(TcpServer.clients.getKeyByValue(clientPs)) + "\r\n");
						
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "ǩ��ʧ��");
				}
			}
		});
		
		logoPanel.setLayout(new BorderLayout());
		logoPanel.add(ipLabel, BorderLayout.WEST);
		logoPanel.add(logoLabel, BorderLayout.NORTH);
		logoPanel.add(signButton, BorderLayout.EAST);
		
		studentPanel.setLayout(new GridLayout(5, 6));
		for (int i = 0; i < infoPanel.length; i++) {
			infoPanel[i] = new InfoPanel(i + 1);
			studentPanel.add(infoPanel[i]);
		}

		mainFrame.setIconImage(new ImageIcon("img/icon.png").getImage());
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(logoPanel, BorderLayout.NORTH);
		mainFrame.add(studentPanel, BorderLayout.CENTER);
		mainFrame.add(logo2Label, BorderLayout.SOUTH);
		mainFrame.setSize(1366, 768);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(mainFrame, "ȷ���˳����Ͽ��������ӡ�", "ȷ���˳� ��", 
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					if (LoginUI.autoWifi) {
						try {
							Runtime.getRuntime().exec("netsh wlan stop hostednetwork");
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "�ȵ�ر�ʧ�ܣ����ֶ��رգ�");
						}
					}
					System.exit(0);
				}
			}
		});
		
	}
	
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
		ipLabel.setText("<html>������ip��" + this.serverIp + "<br>"
				+ "�������˿ڣ�" + String.valueOf(TcpServer.SERVER_PORT) + "</html>");
	}
	
	public String getServerIp() {
		return serverIp;
	}
	
	public static void main(String[] args) {
		new MainUI();
	}
	
}
