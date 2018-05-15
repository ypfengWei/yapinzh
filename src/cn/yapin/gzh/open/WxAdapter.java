package cn.yapin.gzh.open;

import cn.yapin.gzh.model.receiveEvent.*;
import cn.yapin.gzh.model.receiveMsg.*;
import cn.yapin.gzh.model.sendMsg.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;


public interface WxAdapter {

    void onReceiveText(ReceiveTextMessage message);

    void onReceiveImage(ReceiveImageMessage message);

    void onReceiveShortvideo(ReceiveShortvideoMessage message);

    void onReceiveVideo(ReceiveVideoMessage message);

    void onReceiveLocation(ReceiveLocationMessage message);

    void onReceiveVoice(ReceiveVoiceMessage message);

    void onReceiveLink(ReceiveLinkMessage message);

    void onSubscriptionEvent(ReceiveEvent event);

    void onUnSubscriptionEvent(ReceiveEvent event);

    void onScanWithSubscribeEvent(ReceiveScanEvent event);

    void onScanWithSubscribedEvent(ReceiveScanEvent event);

    void onClickMenuEvent(ReceiveClickMenuEvent event);

    void onLocationSelectEvent(ReceiveLocationSelectEvent event);

    void onPicWeixinEvent(ReceivePicEvent event);

    void onPicPhotoOrAlbumnEvent(ReceivePicEvent event);

    void onPicSysPhotoEvent(ReceivePicEvent event);

    void onScanCodeWaitMsgEvent(ReceiveScanCodeEvent event);

    void onScanCodePushEvent(ReceiveScanCodeEvent event);

    void onViewMenuEvent(ReceiveViewMenuEvent event);

    void onLocationEvent(ReceiveLocationEvent event);

    void onMassSendJobFinishEvent(Map<String, String> map);

    void setWriter(PrintWriter writer);

    void setRequest(HttpServletRequest request);

    void setResponse(HttpServletResponse response);

    void setServletContext(ServletContext servletContext);

    void sendTextMessage(TextMessage msg);

    void sendNews(NewsMessage msg);

    void sendImage(ImageMessage msg);

    void sendVoice(VoiceMessage msg);

    void sendVideo(VideoMessage msg);

    void sendMusic(MusicMessage msg);

}
