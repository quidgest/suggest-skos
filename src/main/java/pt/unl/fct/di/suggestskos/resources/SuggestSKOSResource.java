package pt.unl.fct.di.suggestskos.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public List<Map<String,String>> getSuggestions(
      @QueryParam("q") Optional<String> query,
      @QueryParam("limit") Optional<Integer> limit) throws IOException {
    Suggestions sugs = new Suggestions(counter.incrementAndGet(),
        skosAutocompleter.suggestSimilar(query.or(""), limit.or(10)));
    List<Map<String,String>> items = new ArrayList<Map<String,String>>();
    for (String s : sugs.getContent()) {
      Map<String,String> map = new HashMap<String,String>();
      map.put("value", s);
      map.put("name", s);
      items.add(map);
    }
    return items;
  }
}
