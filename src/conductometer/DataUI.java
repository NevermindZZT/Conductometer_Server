package conductometer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DataUI extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6214484802539800056L;

	private int serialNumber;
	private JLabel[] dataLabel = new JLabel[48];
	private JLabel[] data2Label = new JLabel[6];
	private JLabel studentLabel = new JLabel("", JLabel.CENTER);
	
	public DataUI(int serialNumber) {
		
		this.serialNumber = serialNumber;
		
		JFrame dataFrame = new JFrame("�����ţ�" + String.valueOf(serialNumber));
		JButton refreshButton = new JButton("ˢ������");
		JPanel logoPanel = new JPanel();
		JPanel dataPanel = new JPanel();
		JPanel data2Panel = new JPanel();
		JPanel data2Container = new JPanel();
		JLabel data2Header = new JLabel("�������ݣ�");
		
		studentLabel.setFont(new Font("΢���ź�", Font.PLAIN, 48));
		studentLabel.setPreferredSize(new Dimension(768, 120));
		
		for (int i = 0; i < dataLabel.length; i++) {
			dataLabel[i] = new JLabel();
			dataLabel[i].setBorder(BorderFactory.createLineBorder(Color.black));
			dataLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			dataLabel[i].setFont(new Font("΢���ź�", Font.PLAIN, 28));
			if (i % 16 == 0) {
				dataLabel[i].setText("ʱ��");
				dataLabel[i].setOpaque(true);
				dataLabel[i].setBackground(new Color(196, 215, 233));
			} else if (i % 8 == 0) {
				dataLabel[i].setText("�¶�");
				dataLabel[i].setOpaque(true);
				dataLabel[i].setBackground(new Color(196, 215, 233));
			}
		}
		
		for (int i = 0; i < data2Label.length; i++) {
			data2Label[i] = new JLabel();
			data2Label[i].setBorder(BorderFactory.createLineBorder(Color.black));
			data2Label[i].setHorizontalAlignment(SwingConstants.CENTER);
			data2Label[i].setFont(new Font("΢���ź�", Font.PLAIN, 24));
		}
		
		for (int i = 0; i < 3; i++) {
			data2Label[i].setOpaque(true);
			data2Label[i].setBackground(new Color(196, 215, 233));
		}
		data2Label[0].setText("���ü������¶�");
		data2Label[1].setText("�Ⱥ�̬ɢ�����¶�");
		data2Label[2].setText("���º�ɢ�����¶�");
		
		data2Header.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		
		data2Container.setLayout(new BorderLayout());
		data2Container.add(data2Header, BorderLayout.NORTH);
		data2Container.add(data2Panel, BorderLayout.CENTER);
		data2Container.add(new JLabel("���ϴ�ѧ���������ѧԺ", JLabel.RIGHT), BorderLayout.SOUTH);
		
		refreshButton.setPreferredSize(new Dimension(100, 40));
		refreshButton.setBackground(new Color(220, 220, 220));
		refreshButton.setBorderPainted(false);
		refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TcpServer.clients.get(serialNumber).print("rcv-" + String.valueOf(DataManage.DEVICE_DATA) + "-" + String.valueOf(serialNumber) + "\r\n");
				try {
					Thread.sleep(1000);
					setStudentNumber(DataManage.dataManages[serialNumber - 1].getSutdentNumber());
					setData();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "��������ʧ�ܣ������ԣ�");
				}
			}
		});
		
		logoPanel.setLayout(new BorderLayout());
		logoPanel.add(studentLabel, BorderLayout.NORTH);
		logoPanel.add(refreshButton, BorderLayout.EAST);
		
		dataPanel.setLayout(new GridLayout(8, 6));
		for (int i = 0; i < dataLabel.length; i++) {
			dataPanel.add(dataLabel[i / 6 + (8 * (i % 6))]);
		}
		
		data2Panel.setLayout(new GridLayout(2, 3));
		for (int i = 0; i < data2Label.length; i++) {
			data2Panel.add(data2Label[i]);
		}
		
		dataFrame.setIconImage(new ImageIcon("img/icon.png").getImage());
		dataFrame.setLayout(new BorderLayout());
		dataFrame.add(logoPanel, BorderLayout.NORTH);
		dataFrame.add(dataPanel, BorderLayout.CENTER);
		dataFrame.add(data2Container, BorderLayout.SOUTH);
		dataFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dataFrame.setSize(1366, 768);
		dataFrame.setLocationRelativeTo(null);
		dataFrame.setExtendedState(MAXIMIZED_BOTH);
		dataFrame.setVisible(true);
		
		this.setData();
		
	}
	
	
	public void setStudentNumber(String studentNumber) {
		studentLabel.setText("ѧ�ţ�" + studentNumber);
	}
	
	public void setData() {
		String time = new String();
		for (int i = 0; i < 7; i++) {																	//��ʾʱ��
			time = String.format("%d:%02d", DataManage.dataManages[serialNumber - 1].getMeasuredTime(i) / 60,
					DataManage.dataManages[serialNumber - 1].getMeasuredTime(i) % 60);
			dataLabel[i + 1].setText(time);
			dataLabel[i + 1].setOpaque(true);
			dataLabel[i + 1].setBackground(new Color(228, 228, 228));
			time = String.format("%d:%02d", DataManage.dataManages[serialNumber - 1].getMeasuredTime(i + 7) / 60,
					DataManage.dataManages[serialNumber - 1].getMeasuredTime(i + 7) % 60);
			dataLabel[i + 17].setText(time);
			dataLabel[i + 17].setOpaque(true);
			dataLabel[i + 17].setBackground(new Color(228, 228, 228));
			time = String.format("%d:%02d", DataManage.dataManages[serialNumber - 1].getMeasuredTime(i + 14) / 60,
					DataManage.dataManages[serialNumber - 1].getMeasuredTime(i + 14) % 60);
			dataLabel[i + 33].setText(time);
			dataLabel[i + 33].setOpaque(true);
			dataLabel[i + 33].setBackground(new Color(228, 228, 228));
			
																										//��ʾ�¶�
			dataLabel[i + 9].setText(String.valueOf(DataManage.dataManages[serialNumber - 1].getMeasuredTemprature(i)));
			dataLabel[i + 9].setOpaque(true);
			dataLabel[i + 25].setText(String.valueOf(DataManage.dataManages[serialNumber - 1].getMeasuredTemprature(i + 7)));
			dataLabel[i + 25].setOpaque(true);
			dataLabel[i + 41].setText(String.valueOf(DataManage.dataManages[serialNumber - 1].getMeasuredTemprature(i + 14)));
			dataLabel[i + 41].setOpaque(true);
		}
		data2Label[3].setText(String.valueOf(DataManage.dataManages[serialNumber - 1].getSettedTemprature()));
		data2Label[4].setText(String.valueOf(DataManage.dataManages[serialNumber - 1].getBalanceTemprature()));
		data2Label[5].setText(String.valueOf(DataManage.dataManages[serialNumber - 1].getHeatingTemprature()));
	}
	
	public static void main(String[] args) {
		new DataUI(1);
	}
	
}

