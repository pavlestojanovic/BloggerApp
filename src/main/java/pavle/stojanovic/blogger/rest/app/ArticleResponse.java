package pavle.stojanovic.blogger.rest.app;

import pavle.stojanovic.blogger.rest.dto.ArticleDTO;

public class ArticleResponse extends RESTResponse {
	
	private ArticleDTO article;

	public ArticleDTO getArticle() {
		return article;
	}

	public void setArticle(ArticleDTO article) {
		this.article = article;
	}

}
