package pt.unl.fct.di.suggestskos.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pt.unl.fct.di.suggestskos.core.Suggestions;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

@Path("/suggest-skos")
@Produces(MediaType.APPLICATION_JSON)
public class SuggestSKOSResource {
  private final AtomicLong counter;
  
  public SuggestSKOSResource() {
    this.counter = new AtomicLong();
  }
  
  @GET
  @Timed
  public String[] getSuggestions(@QueryParam("term") Optional<String> term) {
    Suggestions sugs = new Suggestions(counter.incrementAndGet(), new String[] {"Hello",
    "World"});
    return sugs.getContent();
  }
}
