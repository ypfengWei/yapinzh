package cn.yapin.gzh.model.sendMsg;


import cn.yapin.gzh.model.container.ArticleDescriptionContainer;

import java.util.List;

public class NewsMessage extends BaseMessage {

	private List<ArticleDescriptionContainer> Articles;

	private int ArticleCount;

	public NewsMessage() {
		super();
	}

	public NewsMessage(String toUserName, String fromUserName, Long createTime, String msgType,
			List<ArticleDescriptionContainer> articles, int articleCount) {
		super(toUserName, fromUserName, createTime, msgType);
		Articles = articles;
		ArticleCount = articleCount;
	}

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<ArticleDescriptionContainer> getArticles() {
		return Articles;
	}

	public void setArticles(List<ArticleDescriptionContainer> articles) {
		Articles = articles;
	}

}
