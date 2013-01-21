package pt.unl.fct.di.suggestskos;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.util.Version;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import pt.unl.fct.di.suggestskos.resources.ExpansionsResource;
import pt.unl.fct.di.suggestskos.resources.SuggestResource;
import at.ac.univie.mminf.luceneSKOS.search.SKOSAutocompleter;
import at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine;
import at.ac.univie.mminf.luceneSKOS.skos.impl.SKOSEngineImpl;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.logging.Log;

public class SuggestSKOSInitializer extends SuggestSKOSConfiguration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	public static void init(SuggestSKOSConfiguration configuration,
	      Environment environment) throws IOException {
	    final Log LOG = Log.forClass(SuggestSKOSService.class);
	    
	    final String fileName = configuration.getFileName();
	    final String languages = configuration.getLanguages();
	    
	    File file = new File(fileName);
	    if (!file.exists()) {
	      IOException e = new IOException("File not found: " + fileName);
	      LOG.error(e.getMessage());
	      throw e;
	    }
	    
	    final String[] langs = languages == null ? new String[0] : languages.split("\\s+");
	    
	    final SKOSEngine skosEngine = new SKOSEngineImpl(Version.LUCENE_40,
	        fileName, langs);
	    final SKOSAutocompleter skosAutocompleter = new SKOSAutocompleter(
	        Version.LUCENE_40, fileName, langs);
	    
	    // cross-origin filter
	    environment.addFilter(CrossOriginFilter.class, "/*");
	    
	    environment.addResource(new ExpansionsResource(skosEngine));
	    environment.addResource(new SuggestResource(skosAutocompleter));
	  }
}
