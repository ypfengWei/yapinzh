package cn.yapin.gzh.utils;

import cn.yapin.gzh.entity.AccessToken;
import cn.yapin.gzh.model.container.ArticleDescriptionContainer;
import cn.yapin.gzh.model.sendMsg.BaseMessage;
import cn.yapin.gzh.model.sendMsg.NewsMessage;
import com.thoughtworks.xstream.XStream;

public class WxUtils {

	private static AccessToken token = null;


	/**
	 * message转xml字符串
	 * 
	 *
	 */
	public static String message2Xml(BaseMessage message) {
		XStream xs = new XStream();
		xs.alias("xml", message.getClass());
		// xs.alias("item", Item.class);T
		return xs.toXML(message);

	}

	/**
	 * news转xml字符串
	 * 
	 */
	public static String news2Xml(NewsMessage news) {
		XStream xs = new XStream();
		xs.alias("xml", NewsMessage.class);
		xs.alias("item", ArticleDescriptionContainer.class);

		return xs.toXML(news);

	}

}
