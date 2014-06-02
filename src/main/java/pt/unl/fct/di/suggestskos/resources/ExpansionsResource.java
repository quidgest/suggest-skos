package pt.unl.fct.di.suggestskos.resources;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pt.unl.fct.di.suggestskos.core.Expansions;

import at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/expansions")
@Produces(MediaType.APPLICATION_JSON)
public class ExpansionsResource {
  //private static final Log LOG = Log.forClass(ExpansionsResource.class);
  private static final Logger LOG = LoggerFactory.getLogger(ExpansionsResource.class);
  
  private final AtomicLong counter;
  private final SKOSEngine skosEngine;
  
  public ExpansionsResource(SKOSEngine skosEngine) throws IOException {
    this.counter = new AtomicLong();
    this.skosEngine = skosEngine;
  }
  
  @GET
  @Timed
  public Expansions getExpansions(@QueryParam("q") Optional<String> query, @QueryParam("limit") Optional<Integer> limit) throws IOException {
    String term = URLDecoder.decode(query.or("").toLowerCase(), "UTF-8");
    
    // to remove duplicates
    LinkedHashSet<String> items = new LinkedHashSet<String>();
    
    try {
      String[] conceptURIs = skosEngine.getConcepts(term);
      
      for (String conceptURI : conceptURIs) {
        String[] prefLabels = skosEngine.getPrefLabels(conceptURI);
        items.addAll(Arrays.asList(prefLabels));
        String[] altLabels = skosEngine.getAltLabels(conceptURI);
        items.addAll(Arrays.asList(altLabels));
      }
    } catch (Exception e) {
      LOG.error("Error when accessing SKOS Engine.\n" + e.getMessage());
    }
    items.remove(term);
    
    Expansions exps = new Expansions(counter.incrementAndGet(),
        Arrays.asList(items.toArray(new String[items.size()])));
    return exps;
  }
}
