package cn.yapin.gzh.open;

import cn.yapin.gzh.model.sendMsg.*;
import cn.yapin.gzh.utils.WxUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public abstract class AbstractWxAdapter implements WxAdapter {

    private PrintWriter writer;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private ServletContext servletContext;

    // public WxServerAdapter(PrintWriter writer) {
    // this.writer = writer;
    //
    // }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void sendTextMessage(TextMessage msg) {
        String xml = WxUtils.message2Xml(msg);
        System.out.println("send to wx：" + xml);
        writer.write(xml);
        System.out.println("发送了一条textmessage");
    }

    public void sendNews(NewsMessage msg) {
        String xml = WxUtils.message2Xml(msg);
        System.out.println("send to wx：" + xml);
        writer.write(xml);
        System.out.println("发送了一条news");
    }

    public void sendImage(ImageMessage msg) {
        String xml = WxUtils.message2Xml(msg);
        System.out.println("send to wx：" + xml);
        writer.write(xml);
        System.out.println("发送了一条Image");
    }

    public void sendVoice(VoiceMessage msg) {
        String xml = WxUtils.message2Xml(msg);
        System.out.println("send to wx：" + xml);
        writer.write(xml);
        System.out.println("发送了一条Voice");
    }

    public void sendVideo(VideoMessage msg) {
        String xml = WxUtils.message2Xml(msg);
        writer.write(xml);
        System.out.println("send to wx：" + xml);
        System.out.println("发送了一条Video");
    }

    public void sendMusic(MusicMessage msg) {
        String xml = WxUtils.message2Xml(msg);
        writer.write(xml);
        System.out.println("send to wx：" + xml);
        System.out.println("发送了一条Music");
    }

    @Override
    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

}
