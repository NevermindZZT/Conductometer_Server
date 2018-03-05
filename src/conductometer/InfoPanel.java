package conductometer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class InfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8921808549301716988L;

	private String stuNumber;
//	private JPanel centerlPanel = new JPanel();
	private JLabel stuLabel = new JLabel();
	private JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
	
	public InfoPanel(int serialNumber) {
		super();
		
		JLabel serialLabel = new JLabel(String.valueOf(serialNumber), JLabel.CENTER);
		
		serialLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		stuLabel.setHorizontalAlignment(JLabel.CENTER);
		stuLabel.setVerticalAlignment(JLabel.CENTER);
		stuLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		progressBar.setMinimum(0);
		progressBar.setMaximum(43);
		progressBar.setBorderPainted(false);
		progressBar.setIndeterminate(false);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		
		setStuNumber("未签到");
		
//		centerlPanel.setLayout(new BorderLayout());
//		centerlPanel.add(stuLabel, BorderLayout.CENTER);
//		centerlPanel.add(progressBar, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.add(serialLabel, BorderLayout.NORTH);
//		this.add(centerlPanel, BorderLayout.CENTER);
		this.add(stuLabel, BorderLayout.CENTER);
		this.add(progressBar, BorderLayout.SOUTH);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (MainUI.mainUI.infoPanel[serialNumber - 1].getStuNumber().equals("未签到")) {
					JOptionPane.showMessageDialog(null, "该机器未签到！");
				} else if (TcpServer.clients.get(serialNumber) == null) {
					JOptionPane.showMessageDialog(null, "该机器已断开连接！");
				} else {
					DataUI dataUI = new DataUI(serialNumber);
					dataUI.setStudentNumber(DataManage.dataManages[serialNumber - 1].getSutdentNumber());
//					dataUI.setData(DataManage.dataManages[serialNumber - 1].getEmpiricalData());
				}
			}
		});
	}
	
	public void setStuNumber(String stuNumber) {
		this.stuNumber = stuNumber;
		stuLabel.setText(stuNumber);
	}
	
	public String getStuNumber() {
		return stuNumber;
	}
	
	public void setProgressBar(int value) {
		this.progressBar.setValue(value);
	}
	
}
