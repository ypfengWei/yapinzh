package cn.yapin.gzh.model.sendMsg;


import cn.yapin.gzh.model.container.MusicDescriptionContainer;

public class MusicMessage extends BaseMessage {

    private MusicDescriptionContainer Music;

    public MusicMessage() {
        super();
    }

    public MusicMessage(String toUserName, String fromUserName, Long createTime, String msgType,
                        MusicDescriptionContainer music) {
        super(toUserName, fromUserName, createTime, msgType);
        Music = music;
    }

    public MusicDescriptionContainer getMusic() {
        return Music;
    }

    public void setMusic(MusicDescriptionContainer music) {
        Music = music;
    }
}
