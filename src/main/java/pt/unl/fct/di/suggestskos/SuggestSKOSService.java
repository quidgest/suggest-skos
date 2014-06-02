package pt.unl.fct.di.suggestskos;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.util.Version;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import pt.unl.fct.di.suggestskos.resources.ExpansionsResource;
import pt.unl.fct.di.suggestskos.resources.SuggestResource;

import at.ac.univie.mminf.luceneSKOS.search.SKOSAutocompleter;
import at.ac.univie.mminf.luceneSKOS.skos.SKOSEngine;
import at.ac.univie.mminf.luceneSKOS.skos.impl.SKOSEngineImpl;

//import com.google.common.cache.CacheBuilderSpec;
//import io.dropwizard.assets.AssetsBundle;
//import com.yammer.dropwizard.Service;
//import com.yammer.dropwizard.bundles.AssetsBundle;
//import com.yammer.dropwizard.config.Environment;
//import com.yammer.dropwizard.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuggestSKOSService extends Application<SuggestSKOSConfiguration> {

	// only loads the data and terminates the Service	
	private boolean onlyLoads;

	public static void main(String[] args) throws Exception {
		boolean onlyLoads = false;
		if(args.length == 3)
			onlyLoads = args[2].equals("load");
		new SuggestSKOSService(onlyLoads).run(args);
	}

	private SuggestSKOSService(boolean onlyLoads) {
		super();
		this.onlyLoads = onlyLoads;
	}

	@Override
	public void initialize(Bootstrap<SuggestSKOSConfiguration> arg0) {
		// TODO Auto-generated method stub
		// By default a restart will be required to pick up any changes to assets.
		// Use the following spec to disable that behaviour, useful when developing.
		//CacheBuilderSpec cacheSpec = CacheBuilderSpec.disableCaching();

		// CacheBuilderSpec cacheSpec = AssetsBundle.DEFAULT_CACHE_SPEC;
		//addBundle(new ConfiguredAssetsBundle("/public", "/public"));
	}

	@Override
	public void run(SuggestSKOSConfiguration configuration, Environment environment) throws Exception {
		final Logger LOG = LoggerFactory.getLogger(SuggestSKOSService.class);

		final String fileName = configuration.getFileName();
		final String languages = configuration.getLanguages();

		File file = new File(fileName);
		if (!file.exists()) {
			IOException e = new IOException("File not found: " + fileName);
			LOG.error(e.getMessage());
			throw e;
		}

		final String[] langs = languages == null ? new String[0] : languages.split("\\s+");

		final SKOSEngine skosEngine = new SKOSEngineImpl(Version.LUCENE_48, fileName, langs);
		final SKOSAutocompleter skosAutocompleter = new SKOSAutocompleter(Version.LUCENE_48, fileName, langs);

		// cross-origin filter
		environment.servlets().addFilter("/*", CrossOriginFilter.class);

		environment.jersey().getResourceConfig().getSingletons().add(new ExpansionsResource(skosEngine));
		environment.jersey().getResourceConfig().getSingletons().add(new SuggestResource(skosAutocompleter));
		if(onlyLoads) {
			System.out.println("Ending index phase");
			System.exit(0);
		}

	}
}
