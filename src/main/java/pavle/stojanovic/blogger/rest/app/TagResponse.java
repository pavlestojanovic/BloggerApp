package pavle.stojanovic.blogger.rest.app;

import pavle.stojanovic.blogger.rest.dto.TagDTO;

public class TagResponse extends RESTResponse {
	
	private TagDTO tag;

	public TagDTO getTag() {
		return tag;
	}

	public void setTag(TagDTO tag) {
		this.tag = tag;
	}

}
