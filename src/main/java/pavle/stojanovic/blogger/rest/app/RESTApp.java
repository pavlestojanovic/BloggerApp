package pavle.stojanovic.blogger.rest.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import pavle.stojanovic.blogger.rest.resources.ArticleResource;
import pavle.stojanovic.blogger.rest.resources.CommentResource;
import pavle.stojanovic.blogger.rest.resources.RatingResource;
import pavle.stojanovic.blogger.rest.resources.TagResource;
import pavle.stojanovic.blogger.rest.resources.UserResource;

@ApplicationPath("rest")
public class RESTApp extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> resources = new HashSet<>();
		
		resources.add(UserResource.class);
		resources.add(ArticleResource.class);
		resources.add(TagResource.class);
		resources.add(CommentResource.class);
		resources.add(RatingResource.class);
		
		return resources;
	}
	
}
