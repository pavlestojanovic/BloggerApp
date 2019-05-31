package pavle.stojanovic.blogger.rest.app;

import java.util.List;

import pavle.stojanovic.blogger.domain.Tag;

public class TagsResponse extends RESTResponse {
	
	private List<Tag> tags;

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
