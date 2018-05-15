package cn.yapin.gzh.model.receiveEvent;

public class ReceiveScanCodeEvent extends ReceiveEvent {
	private String EventKey;
	private String MenuID;

	private String ScanCodeInfo;// 扫描信息
	private String ScanType; // 扫描类型，一般是qrcode
	private String ScanResult; // 扫描结果，即二维码对应的字符串信息

	public ReceiveScanCodeEvent() {
		super();
	}

	public ReceiveScanCodeEvent(String toUserName, String fromUserName, Long createTime, String msgType, String event,
			String eventKey, String menuID, String scanCodeInfo, String scanType, String scanResult) {
		super(toUserName, fromUserName, createTime, msgType, event);
		EventKey = eventKey;
		MenuID = menuID;
		ScanCodeInfo = scanCodeInfo;
		ScanType = scanType;
		ScanResult = scanResult;
	}

	public String getScanCodeInfo() {
		return ScanCodeInfo;
	}

	public void setScanCodeInfo(String scanCodeInfo) {
		ScanCodeInfo = scanCodeInfo;
	}

	public String getScanType() {
		return ScanType;
	}

	public void setScanType(String scanType) {
		ScanType = scanType;
	}

	public String getScanResult() {
		return ScanResult;
	}

	public void setScanResult(String scanResult) {
		ScanResult = scanResult;
	}

	public String getMenuID() {
		return MenuID;
	}

	public void setMenuID(String menuID) {
		MenuID = menuID;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
