package cn.yapin.gzh.model.sendMsg;


import cn.yapin.gzh.model.container.MediaIdContainer;

public class VoiceMessage extends BaseMessage {

    private MediaIdContainer Voice;

    public VoiceMessage() {
        super();
    }

    public VoiceMessage(String toUserName, String fromUserName, Long createTime, String msgType,
                        MediaIdContainer voice) {
        super(toUserName, fromUserName, createTime, msgType);
        Voice = voice;
    }

    public MediaIdContainer getVoice() {
        return Voice;
    }

    public void setVoice(MediaIdContainer voice) {
        Voice = voice;
    }

}
