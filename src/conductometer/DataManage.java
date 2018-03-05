package conductometer;


public class DataManage {
	
	public static DataManage[] dataManages = new DataManage[30];
	
	public static final int DEVICE_CONNECT = 0;
	public static final int DEVICE_REGISTER = 1;
	public static final int DEVICE_DATA = 2;
	
	
	private int command;
	private int serialNumber;
	private String studentNumber;
	private int progress;
	private float settedTemprature;
	private float balanceTemprature;
	private float heatingTemprature;
	private MeasuredData[] measuredData = new MeasuredData[21];
	
	public class MeasuredData {
		public int time = 0;
		public float temprature = 0;
	}
	
	public DataManage() {
		for (int i = 0; i < measuredData.length; i++) {
			measuredData[i] = new MeasuredData();
		}
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public int getSerialNumber() {
		return this.serialNumber;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	
	public int getCommand() {
		return this.command;
	}
	
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getSutdentNumber() {
		return this.studentNumber;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	public int getProgress() {
		return this.progress;
	}

	public void setSettedTemprature(float settedTemprature) {
		this.settedTemprature = settedTemprature;
	}
	
	public float getSettedTemprature() {
		return this.settedTemprature;
	}
	
	public void setBalanceTemprature(float balanceTemprature) {
		this.balanceTemprature = balanceTemprature;
	}
	
	public float getBalanceTemprature() {
		return this.balanceTemprature;
	}

	public void setHeatingTemprature(float heatingTemprature) {
		this.heatingTemprature = heatingTemprature;
	}
	
	public float getHeatingTemprature() {
		return this.heatingTemprature;
	}

	public void setMeasuredData(MeasuredData[] measuredData) {
		this.measuredData = measuredData;
	}
	
	public MeasuredData[] getMeasuredData() {
		return this.measuredData;
	}

	public void setMeasuredTime(int i, int time) {
		this.measuredData[i].time = time;
	}

	public int getMeasuredTime(int i) {
		return this.measuredData[i].time;
	}
	
	public float getMeasuredTemprature(int i) {
		return this.measuredData[i].temprature;
	}
	
	public void setMeasuredTemprature(int i, float temprature) {
		this.measuredData[i].temprature = temprature;
	}
	
	
	public static void main(String[] args) {
		DataManage.dataManages[0] = new DataManage();
		System.out.println(String.valueOf(DataManage.dataManages[0].getMeasuredTime(0)));
	}
	
}

