package pt.unl.fct.di.suggestskos;

import pt.unl.fct.di.suggestskos.health.TemplateHealthCheck;
import pt.unl.fct.di.suggestskos.resources.SuggestSKOSResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

public class SuggestSKOSService extends Service<SuggestSKOSConfiguration> {
  
  public static void main(String[] args) throws Exception {
    new SuggestSKOSService().run(args);
  }
  
  private SuggestSKOSService() {
    super("suggest-skos");
  }
  
  @Override
  protected void initialize(SuggestSKOSConfiguration configuration,
      Environment environment) {
    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();
    environment.addResource(new SuggestSKOSResource(template, defaultName));
    environment.addHealthCheck(new TemplateHealthCheck(template));
  }
  
}
