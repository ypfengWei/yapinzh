package cn.yapin.gzh.utils.wxpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static cn.yapin.gzh.utils.WebUtils.doXMLParse;
import static cn.yapin.gzh.utils.WebUtils.readBuffer;

/**
 * 应答处理类
 * 应答处理类继承此类，重写isTenpaySign方法即可。
 *
 * @author miklchen
 */
public class ResponseHandler {

    /**
     * 密钥
     */
    private String key;

    /**
     * 应答的参数
     */
    private SortedMap parameters;

    /**
     * debug信息
     */
    private String debugInfo;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String uriEncoding;

    /**
     * 构造函数
     *
     * @param request
     * @param response
     */
    public ResponseHandler(HttpServletRequest request,
                           HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.parameters = doXMLParse(readBuffer(request));
    }

    /**
     * 获取密钥
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置密钥
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取参数值
     *
     * @param parameter 参数名称
     * @return String
     */
    public String getParameter(String parameter) {
        String s = (String) this.parameters.get(parameter);
        return (null == s) ? "" : s;
    }

    /**
     * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public boolean isTenpaySign() {
        StringBuffer sb = new StringBuffer();
        Set es = this.parameters.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }

        sb.append("key=").append(this.getKey());

        //算出摘要
        String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
        String sign = MD5.MD5Encode(sb.toString(), enc).toLowerCase();

        String tenpaySign = this.getParameter("sign").toLowerCase();

        //debug信息
        this.setDebugInfo(sb.toString() + " => sign:" + sign +
                " tenpaySign:" + tenpaySign);

        return tenpaySign.equals(sign);
    }

    /**
     * 返回处理结果给财付通服务器。
     *
     * @param msg: Success or fail。
     * @throws IOException
     */
    public void sendToCFT(String msg) throws IOException {
        PrintWriter out = this.getHttpServletResponse().getWriter();
        out.println(msg);
        out.flush();
        out.close();

    }


    /**
     * 设置debug信息
     */
    protected void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }


    protected HttpServletResponse getHttpServletResponse() {
        return this.response;
    }

}
