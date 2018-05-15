package cn.yapin.gzh.message;

import cn.yapin.gzh.model.receiveEvent.*;
import cn.yapin.gzh.model.receiveMsg.*;
import cn.yapin.gzh.model.sendMsg.TextMessage;
import cn.yapin.gzh.open.AbstractWxAdapter;
import cn.yapin.gzh.utils.WxUtils;

import java.util.Map;

public class WxAdapterImpl extends AbstractWxAdapter {

    @Override
    public void onReceiveText(ReceiveTextMessage message) {

        System.out.println("onReceiveTextMessage");
        TextMessage text = new TextMessage();
        text.setContent("哈咯，这是一条文本消息");
        text.setCreateTime(System.currentTimeMillis());
        text.setFromUserName(message.getToUserName());
        text.setToUserName(message.getFromUserName());
        text.setMsgType(MessageType.TEXT);
        sendTextMessage(text);

        // //测试发送image 正常
        // ImageMessage img = new ImageMessage();
        // img.setCreateTime(System.currentTimeMillis());
        // img.setFromUserName(message.getToUserName());
        // img.setToUserName(message.getFromUserName());
        // img.setMsgType(MessageType.IMAGE);
        // Image i = new Image();
        // i.setMediaId("7fpHF8Shpc1twvKp7OAfFPByeottLSsyzTK5u_By5Uc");
        // img.setImage(i);
        // // 7fpHF8Shpc1twvKp7OAfFPByeottLSsyzTK5u_By5Uc
        // // oxajh0ogRHaVtzDNCiHQ4oe_CEoI
        // sendImage(img);

        // 测试发送text消息 正常


        // 测试添加imagematerial代码 正常
        // String imagePath =
        // getServletContext().getRealPath("/res/img/i1.jpg");
        // try {
        //
        // String mediaId = WxUtil.addPerpetualOtherMaterial(MaterialType.IMAGE,
        // imagePath);
        // System.out.println(mediaId);
        // } catch (IllegalFileNameException e) {
        // e.printStackTrace();
        // }

        // 测试添加Videomaterial代码 正常
        // String videoPath =
        // getServletContext().getRealPath("/res/video/v1.mp4");
        // try {
        //
        // String mediaId = WxUtil.addPerpetualVideoMaterial(videoPath,new
        // VideoDescription("好看的视频", "很好看很好看"));
        // System.out.println(mediaId);
        // } catch (IllegalFileNameException e) {
        // e.printStackTrace();
        // }

    }

    @Override
    public void onReceiveVoice(ReceiveVoiceMessage message) {
        System.out.println("onReceiveVoice");
        // 测试发送voice，正常
        // VoiceMessage voice = new VoiceMessage();
        // voice.setCreateTime(System.currentTimeMillis());
        // voice.setFromUserName(message.getToUserName());
        // voice.setToUserName(message.getFromUserName());
        // voice.setMsgType(MessageType.VOICE);
        // Voice v = new Voice();
        // v.setMediaId(message.getMediaId());
        // voice.setVoice(v);
        // sendVoice(voice);

    }

    @Override
    public void onReceiveImage(ReceiveImageMessage message) {
        System.out.println("onReceiveImage");

    }

    @Override
    public void onReceiveShortvideo(ReceiveShortvideoMessage message) {
        System.out.println("onReceiveShortvideo");

    }

    @Override
    public void onReceiveVideo(ReceiveVideoMessage message) {
        System.out.println("onReceiveVideo");

        // 测试发送video 正常
        // VideoMessage video = new VideoMessage();
        // video.setCreateTime(System.currentTimeMillis());
        // video.setFromUserName(message.getToUserName());
        // video.setToUserName(message.getFromUserName());
        // video.setMsgType(MessageType.VIDEO);
        // Video v = new Video();
        // v.setDescription("很好看的视频，人人都喜欢看");
        // v.setTitle("精彩视频");
        // v.setMediaId("7fpHF8Shpc1twvKp7OAfFIOV1ztRBP_zjTQXeS7lO04");
        // video.setVideo(v);
        // sendVideo(video);

    }

    @Override
    public void onReceiveLocation(ReceiveLocationMessage message) {

        System.out.println("onReceiveLocation");

    }

    @Override
    public void onReceiveLink(ReceiveLinkMessage message) {
        System.out.println("onReceiveLink");
    }

    @Override
    public void onSubscriptionEvent(ReceiveEvent event) {
        System.out.println("onSubscriptionEvent");
    }

    @Override
    public void onUnSubscriptionEvent(ReceiveEvent event) {
        System.out.println("onUnSubscriptionEvent");

    }

    @Override
    public void onScanWithSubscribeEvent(ReceiveScanEvent event) {
        System.out.println("onScanWithSubscribeEvent");

    }

    @Override
    public void onScanWithSubscribedEvent(ReceiveScanEvent event) {
        System.out.println(WxUtils.message2Xml(event));
        System.out.println("onScanWithSubscribedEvent");

    }

    @Override
    public void onClickMenuEvent(ReceiveClickMenuEvent event) {
        System.out.println("onClickMenuEvent");

    }

    @Override
    public void onLocationEvent(ReceiveLocationEvent event) {
        System.out.println("onLocationEvent");

    }


    @Override
    public void onLocationSelectEvent(ReceiveLocationSelectEvent event) {
        System.out.println("onLocationSelectEvent");
    }

    @Override
    public void onPicWeixinEvent(ReceivePicEvent event) {
        System.out.println("onPicWeixinEvent");
    }

    @Override
    public void onPicPhotoOrAlbumnEvent(ReceivePicEvent event) {
        System.out.println("onPicPhotoOrAlbumnEvent");
    }

    @Override
    public void onPicSysPhotoEvent(ReceivePicEvent event) {
        System.out.println("onPicSysPhotoEvent");
    }

    @Override
    public void onScanCodeWaitMsgEvent(ReceiveScanCodeEvent event) {
        System.out.println(WxUtils.message2Xml(event));
        System.out.println("onScanCodeWaitMsgEvent");
    }

    @Override
    public void onScanCodePushEvent(ReceiveScanCodeEvent event) {
        System.out.println("onScanCodePushEvent");
    }

    @Override
    public void onViewMenuEvent(ReceiveViewMenuEvent event) {
        System.out.println("onViewMenuEvent");
    }

    @Override
    public void onMassSendJobFinishEvent(Map<String, String> map) {
        System.out.println("onMassSendJobFinishEvent");
    }

}
