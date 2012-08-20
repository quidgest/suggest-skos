package pt.unl.fct.di.suggestskos.resources;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.lucene.util.Version;

import at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine;
import at.ac.univie.mminf.luceneSKOS.skos.impl.SKOSEngineImpl;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

@Path("/expansions")
@Produces(MediaType.APPLICATION_JSON)
public class ExpansionsSKOSResource {
  private final AtomicLong counter;
  private final SKOSEngine skosEngine;
  
  public ExpansionsSKOSResource() throws IOException {
    this.counter = new AtomicLong();
    skosEngine = new SKOSEngineImpl(Version.LUCENE_40,
        "../../skos/mesh_complete.nt.zip");
  }
  
  @GET
  @Timed
  public String[] getExpansions(
      @QueryParam("q") Optional<String> query,
      @QueryParam("limit") Optional<Integer> limit) throws IOException {
    String term = URLDecoder.decode(query.or("").toLowerCase(), "UTF-8");
    return skosEngine.getAltTerms(term);
  }
}
