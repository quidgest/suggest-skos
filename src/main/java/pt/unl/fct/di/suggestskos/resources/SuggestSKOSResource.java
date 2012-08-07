package pt.unl.fct.di.suggestskos.resources;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.lucene.util.Version;

import pt.unl.fct.di.suggestskos.core.Suggestions;

import at.ac.univie.mminf.luceneSKOS.search.SKOSAutocompleter;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

@Path("/suggest-skos")
@Produces(MediaType.APPLICATION_JSON)
public class SuggestSKOSResource {
  private final AtomicLong counter;
  private final SKOSAutocompleter skosAutocompleter;
  
  public SuggestSKOSResource() throws IOException {
    this.counter = new AtomicLong();
    skosAutocompleter = new SKOSAutocompleter(Version.LUCENE_40,
        "../../skos/eurovoc_skos.rdf.zip", "en");
  }
  
  @GET
  @Timed
  public String[] getSuggestions(@QueryParam("term") Optional<String> term)
      throws IOException {
    Suggestions sugs = new Suggestions(counter.incrementAndGet(),
        skosAutocompleter.suggestSimilar(term.or(""), 10));
    return sugs.getContent();
  }
}
