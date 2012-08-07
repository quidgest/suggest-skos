package pt.unl.fct.di.suggestskos;

import java.io.IOException;

import pt.unl.fct.di.suggestskos.health.TemplateHealthCheck;
import pt.unl.fct.di.suggestskos.resources.SuggestSKOSResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;

public class SuggestSKOSService extends Service<SuggestSKOSConfiguration> {
  
  public static void main(String[] args) throws Exception {
    new SuggestSKOSService().run(args);
  }
  
  private SuggestSKOSService() {
    super("suggest-skos");
    
    addBundle(new AssetsBundle("/public", "/public"));
  }
  
  @Override
  protected void initialize(SuggestSKOSConfiguration configuration,
      Environment environment) throws IOException {
    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();
    
    
    environment.addResource(new SuggestSKOSResource());
    environment.addHealthCheck(new TemplateHealthCheck(template));
  }
  
}
