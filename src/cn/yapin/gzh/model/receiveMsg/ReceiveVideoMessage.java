package cn.yapin.gzh.model.receiveMsg;

public class ReceiveVideoMessage extends ReceiveMessage {
	private String ThumbMediaId;
	private String MediaId;

	public ReceiveVideoMessage() {
		super();
	}

	public ReceiveVideoMessage(Long createTime, String toUserName, String fromUserName, String msgType, String msgId,
			String thumbMediaId, String mediaId) {
		super(createTime, toUserName, fromUserName, msgType, msgId);
		ThumbMediaId = thumbMediaId;
		MediaId = mediaId;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

}
