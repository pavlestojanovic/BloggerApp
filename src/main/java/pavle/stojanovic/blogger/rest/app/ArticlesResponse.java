package pavle.stojanovic.blogger.rest.app;

import java.util.List;

import pavle.stojanovic.blogger.domain.Article;

public class ArticlesResponse extends RESTResponse {
	
	private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
