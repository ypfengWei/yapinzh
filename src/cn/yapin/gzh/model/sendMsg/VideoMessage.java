package cn.yapin.gzh.model.sendMsg;


import cn.yapin.gzh.model.container.VideoDescriptionContainer;

public class VideoMessage extends BaseMessage {

    private VideoDescriptionContainer Video;

    public VideoMessage() {
        super();
    }

    public VideoMessage(String toUserName, String fromUserName, Long createTime, String msgType,
                        VideoDescriptionContainer video) {
        super(toUserName, fromUserName, createTime, msgType);
        Video = video;
    }

    public VideoDescriptionContainer getVideo() {
        return Video;
    }

    public void setVideo(VideoDescriptionContainer video) {
        Video = video;
    }

}
